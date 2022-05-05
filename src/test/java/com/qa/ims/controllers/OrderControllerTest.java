package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.OrderController;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

	@Mock
	private Utils utils;

	@Mock
	private OrderDAO dao;

	@InjectMocks
	private OrderController controller;

	@Test
	public void testCreate() {
		final Long CUSTOMER_ID = 1l;
		final List<Long> ITEM_IDS = List.of(1l, 2l, 3l, 4l);
		final Order created = new Order(CUSTOMER_ID, ITEM_IDS);

		Mockito.when(utils.getLong()).thenReturn(CUSTOMER_ID, 1l, 2l, 3l, 4l, 0l);
		Mockito.when(dao.create(created)).thenReturn(created);

		assertEquals(created, controller.create());

		Mockito.verify(utils, Mockito.times(6)).getLong();
		Mockito.verify(dao, Mockito.times(1)).create(created);
	}
	
	@Test
	public void testReadAll() {
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(1l, 2l, List.of(1l, 2l)));
		
		Mockito.when(utils.getString()).thenReturn("red", "read"); //Testing incorrect input
		Mockito.when(dao.readAll()).thenReturn(orders);
		
		assertEquals(orders, controller.readAll());

		Mockito.verify(utils, Mockito.times(2)).getString();
		Mockito.verify(dao, Mockito.times(1)).readAll();	
	}
	
	@Test
	public void testUpdateAdd() {
		Order updated = new Order(1l, 2l, 3l);
		
		Mockito.when(utils.getLong()).thenReturn(updated.getId(), updated.getCustomerId(), 3l);
		Mockito.when(utils.getString()).thenReturn("add");
		Mockito.when(dao.update(updated)).thenReturn(updated);
		
		assertEquals(updated, controller.update());
		
		Mockito.verify(utils, Mockito.times(3)).getLong();
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(dao, Mockito.times(1)).update(updated);	
	}
	
	@Test
	public void testUpdateRemove() {
		Order updated = new Order(1l, 2l, List.of(2l));
		
		Mockito.when(utils.getLong()).thenReturn(updated.getId(), updated.getCustomerId(), 2l);
		Mockito.when(utils.getString()).thenReturn("delete");
		Mockito.when(dao.removeItem(updated)).thenReturn(updated);
		
		assertEquals(updated, controller.update());
		
		Mockito.verify(utils, Mockito.times(3)).getLong();
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(dao, Mockito.times(1)).removeItem(updated);	
	}
	
	@Test
	public void testDelete() {
		final Long ID = 1l;
		
		Mockito.when(utils.getLong()).thenReturn(ID);
		Mockito.when(dao.delete(ID)).thenReturn(1);
		
		assertEquals(1L, this.controller.delete());

		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(dao, Mockito.times(1)).delete(ID);
	}
}
