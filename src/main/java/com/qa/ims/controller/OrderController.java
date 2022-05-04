package com.qa.ims.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;

/**
 * Takes in order details for CRUD functionality
 *
 */
public class OrderController implements CrudController<Order> {

	public static final Logger LOGGER = LogManager.getLogger();

	private OrderDAO orderDAO;
	private Utils utils;

	public OrderController(OrderDAO orderDAO, Utils utils) {
		super();
		this.orderDAO = orderDAO;
		this.utils = utils;
	}

	/**
	 * Reads all orders to the logger
	 */
	@Override
	public List<Order> readAll() {
		LOGGER.info(
				"Would you like to\nREAD: Read all orders in the system\nCALCULATE: Calculate the total price of an order in the system");
		String readOrCalc = utils.getString();

		while (!readOrCalc.equalsIgnoreCase("read") && !readOrCalc.equalsIgnoreCase("calculate")) {
			LOGGER.info("Invalid selection please try again");
			readOrCalc = utils.getString();
		}

		Boolean ROC = readOrCalc.equalsIgnoreCase("read") ? true : false;
		if (ROC) {
			List<Order> orders = orderDAO.readAll();
			for (Order order : orders) {
				LOGGER.info(order);
			}
			return orders;
		} else if (!ROC) {
			calculateOrder();
		}
		return null;
	}

	public void calculateOrder() {
		LOGGER.info("Enter the id of the order you would like to calculate");
		Long id = utils.getLong();
		LOGGER.info(String.format("The total value of order %s is £%s", id, orderDAO.calculateOrder(id)));
	}

	/**
	 * Creates an order by taking in user input
	 */
	@Override
	public Order create() {
		LOGGER.info("Please enter a customer id");
		Long customer_id = utils.getLong();
		LOGGER.info("Please enter your item ids. Enter 0 when all items have been added");
		List<Long> item_ids = new ArrayList<>();
		do {
			LOGGER.info("Please enter an item id");
			item_ids.add(utils.getLong());
		} while (item_ids.get(item_ids.size() - 1) != 0);
		item_ids.remove(item_ids.size() - 1);
		Order order = orderDAO.create(new Order(customer_id, item_ids));
		LOGGER.info("Order created");
		return order;
	}

	/**
	 * Updates an existing order by taking in user input
	 */
	@Override
	public Order update() {
		LOGGER.info("Please enter the id of the order you would like to update");
		Long id = utils.getLong();
		LOGGER.info("Please enter a customer id");
		Long customer_id = utils.getLong();
		LOGGER.info(
				"Would you like to\nADD: add an item to an existing order\nDELETE: delete an item from an existing order");
		String addOrRemove = utils.getString();
		while (!addOrRemove.equalsIgnoreCase("add") && !addOrRemove.equalsIgnoreCase("delete")) {
			LOGGER.info("Invalid selection please try again");
			addOrRemove = utils.getString();
		}

		Boolean AOR = addOrRemove.equalsIgnoreCase("add") ? true : false;
		Order order = null;
		if (AOR) {
			LOGGER.info("Please enter the id of the item you would like to add to your order");
			Long item_id = utils.getLong();
			order = orderDAO.update(new Order(id, customer_id, item_id));
			LOGGER.info("Order updated");
		} else if (!AOR) {
			LOGGER.info("Please enter the id of the item you would like to delete from your order");
			Long item_id = utils.getLong();
			order = orderDAO.removeItem(new Order(id, customer_id, item_id));
			LOGGER.info("Order updated");
		}

		return order;

	}

	/**
	 * Deletes an existing order by the id of the order
	 * 
	 * @return
	 */
	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the order you would like to delete");
		Long id = utils.getLong();
		return orderDAO.delete(id);
	}

}
