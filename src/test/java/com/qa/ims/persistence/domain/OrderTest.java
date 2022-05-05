package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class OrderTest {
	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(Order.class).verify();
	}

	@Test
	public void testToString() {
		Order order = new Order(1L, 1L, List.of(1L));
		String expected = "id:1, customer_id:1, item_ids:[1]";
		assertEquals(expected, order.toString());
	}

}
