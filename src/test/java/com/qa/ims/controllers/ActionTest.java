package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.Action;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class ActionTest {

	@Test
	public void testGetDescription() {
		final String actionDescription = "CREATE: To save a new entity into the database";
		assertEquals(actionDescription, Action.CREATE.getDescription());
	}

	@Mock
	private Utils utils;

	@Test
	public void testGetAction() {
		final Action expectedAction = Action.CREATE;

		Mockito.when(utils.getString()).thenReturn("c", "create");

		assertEquals(expectedAction, Action.getAction(utils));

		Mockito.verify(utils, Mockito.times(2)).getString();
	}
}
