package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAO implements Dao<Order> {

	public static final Logger LOGGER = LogManager.getLogger();

	public List<Long> modelItemsFromResultSet(ResultSet resultSet, Long id) throws SQLException {
		List<Long> item_ids = new ArrayList<>();
		while (resultSet.next()) {
			if (id == resultSet.getLong("order_id")) {
				item_ids.add(resultSet.getLong("item_id"));
			}
		}
		return item_ids;
	}

	public Order modelFromResultSet(ResultSet resultSet, ResultSet itemsOrderedResultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		Long customer_id = resultSet.getLong("customer_id");
		List<Long> item_ids = modelItemsFromResultSet(itemsOrderedResultSet, id);
		return new Order(id, customer_id, item_ids);
	}

	/**
	 * Reads all customers from the database
	 * 
	 * @return A list of customers
	 */
	@Override
	public List<Order> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement ordersStatement = connection.createStatement();
				ResultSet ordersResultSet = ordersStatement.executeQuery("SELECT * FROM orders;");) {
			List<Order> orders = new ArrayList<>();

			while (ordersResultSet.next()) {
				try (Statement itemsOrderedStatement = connection.createStatement();
						ResultSet itemsOrderedResultSet = itemsOrderedStatement
								.executeQuery("SELECT * FROM items_ordered;");) {

					itemsOrderedResultSet.next();
					orders.add(modelFromResultSet(ordersResultSet, itemsOrderedResultSet));
					itemsOrderedResultSet.close();

				} catch (SQLException e) {
					LOGGER.debug(e);
					LOGGER.error(e.getMessage());
				}
			}
			return orders;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	public Order readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet ordersResultSet = statement.executeQuery("SELECT * FROM orders ORDER BY id DESC LIMIT 1;");
				PreparedStatement itemsOrderedStatement = connection
						.prepareStatement("SELECT * FROM items_ordered WHERE order_id = ?");) {
			ordersResultSet.next();
			itemsOrderedStatement.setLong(1, ordersResultSet.getLong("id"));
			ResultSet itemsOrderedResultSet = itemsOrderedStatement.executeQuery();
			itemsOrderedResultSet.next();
			return modelFromResultSet(ordersResultSet, itemsOrderedResultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Creates a customer in the database
	 * 
	 * @param customer - takes in a customer object. id will be ignored
	 */
	@Override
	public Order create(Order order) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement ordersStatement = connection
						.prepareStatement("INSERT INTO orders(customer_id) VALUES (?);");
				PreparedStatement itemsOrderedStatement = connection
						.prepareStatement("INSERT INTO items_ordered(order_id, item_id) VALUES (?, ?);");
				PreparedStatement idStatement = connection
						.prepareStatement("SELECT id FROM orders ORDER BY id DESC LIMIT 1;");) {

			ordersStatement.setLong(1, order.getCustomerId());
			ordersStatement.executeUpdate();

			ResultSet idResult = idStatement.executeQuery();
			idResult.next();
			Long order_id = idResult.getLong("id");

			int count = 0;

			for (Long item_id : order.getItemIds()) {
				itemsOrderedStatement.setLong(1, order_id);
				itemsOrderedStatement.setLong(2, item_id);
				itemsOrderedStatement.addBatch();
				count++;

				if (count % 100 == 0 || count == order.getItemIds().size()) {
					itemsOrderedStatement.executeBatch();
				}
			}
			return readLatest();
		} catch (Exception e) {
//			LOGGER.
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Order read(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement ordersStatement = connection.prepareStatement("SELECT * FROM orders WHERE id = ?;");
				PreparedStatement itemsOrderedStatement = connection
						.prepareStatement("SELECT * FROM items_ordered WHERE order_id = ?;");) {

			ordersStatement.setLong(1, id);
			itemsOrderedStatement.setLong(1, id);
			try (ResultSet resultSet = ordersStatement.executeQuery();
					ResultSet itemsOrderedResultSet = itemsOrderedStatement.executeQuery()) {
				resultSet.next();
				itemsOrderedResultSet.next();
				return modelFromResultSet(resultSet, itemsOrderedResultSet);
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Updates a customer in the database
	 * 
	 * @param customer - takes in a customer object, the id field will be used to
	 *                 update that customer in the database
	 * @return
	 */
	@Override
	public Order update(Order order) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement ordersStatement = connection
						.prepareStatement("UPDATE orders SET id = ?, customer_id = ? WHERE id = ?;");
				PreparedStatement itemsOrderedStatement = connection
						.prepareStatement("INSERT INTO items_ordered(order_id, item_id) VALUES (?, ?)");) {
			ordersStatement.setLong(1, order.getId());
			ordersStatement.setLong(2, order.getCustomerId());
			ordersStatement.setLong(3, order.getId());
			ordersStatement.executeUpdate();
			if (order.getItemIds().get(0) > 0) {
				itemsOrderedStatement.setLong(1, order.getId());
				itemsOrderedStatement.setLong(2, order.getItemIds().get(0));
				itemsOrderedStatement.executeUpdate();
			}
			return read(order.getId());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public Order removeItem(Order order) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement ordersStatement = connection
						.prepareStatement("UPDATE orders SET id = ?, customer_id = ? WHERE id = ?;");
				PreparedStatement itemsOrderedStatement = connection
						.prepareStatement("DELETE FROM items_ordered WHERE order_id = ? AND item_id = ? LIMIT 1");) {
			ordersStatement.setLong(1, order.getId());
			ordersStatement.setLong(2, order.getCustomerId());
			ordersStatement.setLong(3, order.getId());
			ordersStatement.executeUpdate();

			itemsOrderedStatement.setLong(1, order.getId());
			itemsOrderedStatement.setLong(2, order.getItemIds().get(0));
			itemsOrderedStatement.executeUpdate();

			return read(order.getId());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Deletes a customer in the database
	 * 
	 * @param id - id of the customer
	 */
	@Override
	public int delete(long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement ordersStatement = connection.prepareStatement("DELETE FROM orders WHERE id = ?");
				PreparedStatement itemsOrderedStatement = connection
						.prepareStatement("DELETE FROM items_ordered WHERE order_id = ?");) {

			itemsOrderedStatement.setLong(1, id);
			ordersStatement.setLong(1, id);
			itemsOrderedStatement.executeUpdate();
			return ordersStatement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

	@Override
	public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
