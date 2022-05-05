package com.qa.ims.utils;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UtilsTest {
	
	@Mock
	private Utils utils;

	@Test
	public void testGetLong() {
		final Long expectedLong = 10l;

		Mockito.when(utils.getLong()).thenReturn(expectedLong);

		assertEquals(expectedLong, utils.getLong());
	}
	
	@Test
	public void testGetDouble() {
		final Double expectedLong = 1.2d;
		
		Mockito.when(utils.getDouble()).thenReturn(expectedLong);
		
		assertEquals(expectedLong, utils.getDouble());
	}
	
	@Test
	public void testGetString() {
		final String expectedLong = "Hello";
		
		Mockito.when(utils.getString()).thenReturn(expectedLong);
		
		assertEquals(expectedLong, utils.getString());
	}
}
