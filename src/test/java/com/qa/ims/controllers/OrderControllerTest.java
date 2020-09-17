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
		final Long  C_ID = 1L;
		List<Long> ITEM_ID = new ArrayList<>();
		List<Integer> QUANTITY = new ArrayList<>();
		ITEM_ID.add(1l);
		QUANTITY.add(2);
		final Order created = new Order(C_ID,ITEM_ID,QUANTITY);
		final String STOP_ORDER = "no";

		Mockito.when(utils.getLong()).thenReturn(C_ID,1l);
		Mockito.when(utils.getInt()).thenReturn(2);
		Mockito.when(utils.getString()).thenReturn(STOP_ORDER);
		
		Mockito.when(dao.create(created)).thenReturn(created);

		assertEquals(created, controller.create());

		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(utils, Mockito.times(2)).getLong();
		Mockito.verify(utils, Mockito.times(1)).getInt();
		Mockito.verify(dao, Mockito.times(1)).create(created);
	}

	@Test
	public void testReadAll() {
		List<Long> itemId = new ArrayList<>();
		List<Integer> quantity = new ArrayList<>();
		itemId.add(1l);
		quantity.add(2);
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(1L, 1L, itemId,quantity));

		Mockito.when(dao.readAll()).thenReturn(orders);

		assertEquals(orders, controller.readAll());

		Mockito.verify(dao, Mockito.times(1)).readAll();
	}

	/*
	 * @Test public void testUpdate() { List<Long> itemId = new ArrayList<>();
	 * List<Integer> quantity = new ArrayList<>(); Long ORDER_ID = 1L; Long
	 * CUSTOMER_ID = 2L; Order updated = new Order(ORDER_ID, CUSTOMER_ID,
	 * itemId,quantity); final String UPDATE_CUSTOMER = "customer";
	 * 
	 * Mockito.when(this.utils.getLong()).thenReturn(ORDER_ID);
	 * Mockito.when(this.utils.getString()).thenReturn(UPDATE_CUSTOMER);
	 * Mockito.when(this.utils.getLong()).thenReturn(CUSTOMER_ID);
	 * Mockito.when(this.dao.update(updated)).thenReturn(updated);
	 * 
	 * assertEquals(updated, this.controller.update());
	 * 
	 * Mockito.verify(this.utils, Mockito.times(2)).getLong();
	 * Mockito.verify(this.utils, Mockito.times(1)).getString();
	 * Mockito.verify(this.dao, Mockito.times(1)).update(updated);
	 * 
	 * }
	 */

	@Test
	public void testDelete() {
		final long ID = 1L;

		Mockito.when(utils.getLong()).thenReturn(ID);
		Mockito.when(dao.delete(ID)).thenReturn(1);

		assertEquals(1L, this.controller.delete());

		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(dao, Mockito.times(1)).delete(ID);
	}

}
