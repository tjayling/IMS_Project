package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class ItemTest {
	
	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(Item.class).verify();
	}
	
	@Test
	public void testToString() {
		Item item = new Item(1L, "lamp", 10d);
		String expected = "id: 1 name: lamp value: 10.0";
        assertEquals(expected, item.toString());
	}
}