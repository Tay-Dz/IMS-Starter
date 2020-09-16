package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAOTest {

	private final OrderDAO DAO = new OrderDAO();

	@Before
	public void setup() {
		DBUtils.connect("src/test/resources/db.properties");
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}

	@Test
	public void testCreate() {
		List<Long> itemId = new ArrayList<>();
		List<Integer> quantity = new ArrayList<>();
		itemId.add(1l);
		quantity.add(2);
		final Order created = new Order(2L, 1l, itemId,quantity);
		assertEquals(created, DAO.create(created));
	}

	@Test
	public void testReadAll() {
		List<Long> itemId = new ArrayList<>();
		List<Integer> quantity = new ArrayList<>();
		itemId.add(1l);
		quantity.add(2);
		List<Order> expected = new ArrayList<>();
		expected.add(new Order(1L, 1l, itemId,quantity));
		assertEquals(expected, DAO.readAll());
	}

	@Test
	public void testReadLatest() {
		List<Long> itemId = new ArrayList<>();
		List<Integer> quantity = new ArrayList<>();
		itemId.add(1l);
		quantity.add(2);
		assertEquals(new Order(1L, 1l, itemId,quantity), DAO.readLatest());
	}

	@Test
	public void testRead() {
		final long ID = 1L;
		List<Long> itemId = new ArrayList<>();
		List<Integer> quantity = new ArrayList<>();
		itemId.add(1l);
		quantity.add(2);
		assertEquals(new Order(ID, 1l, itemId,quantity), DAO.readOrder(ID));
	}

	@Test
	public void testUpdate() {
		List<Long> itemId = new ArrayList<>();
		List<Integer> quantity = new ArrayList<>();
		itemId.add(1l);
		quantity.add(5);
		final Order updated = new Order(2l, 1l, itemId,quantity);
		assertEquals(updated, DAO.update(updated));

	}

	@Test
	public void testDelete() {
		
		assertEquals(1, DAO.delete(1));
	}
}
