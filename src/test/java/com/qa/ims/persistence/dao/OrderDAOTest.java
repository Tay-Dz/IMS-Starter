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
		Order created = new Order(2L, 1l, itemId,quantity);

		assertEquals(created, DAO.create(created));
	}

	@Test
	public void testReadAll() {
		List<Long> itemId = new ArrayList<>();
		List<Integer> quantity = new ArrayList<>();
		itemId.add(1l);
		quantity.add(2);
		List<Order> expected = new ArrayList<>();
		Order check = new Order(1L, 1l, itemId,quantity);
		check.setOrderTotal(13.98);
		expected.add(check);
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
	public void testReadCustomer() {
		final long ID = 1L;
		List<Long> itemId = new ArrayList<>();
		List<Integer> quantity = new ArrayList<>();
		assertEquals(new Order(ID, 1l, itemId,quantity), DAO.readOrderCustomer(ID));
	}

	@Test
	public void testUpdate() {
		List<Long> itemId = new ArrayList<>();
		List<Integer> quantity = new ArrayList<>();
		final Order updated = new Order(1l, 1l, itemId,quantity);
		assertEquals(updated, DAO.update(updated));

	}
	@Test
	public void testUpdateAdd() {
		List<Long> itemId = new ArrayList<>();
		List<Integer> quantity = new ArrayList<>();
		Order  OGup = DAO.updateAdd(1l,1l,5);
		itemId.add(1l);
		itemId.add(1l);
		quantity.add(2);
		quantity.add(5);
		Order  updated2 = new Order(1l, 1l, itemId,quantity);
		assertEquals(updated2, OGup);

	}
	@Test
	public void testUpdateDelete() {
		List<Long> itemId = new ArrayList<>();
		List<Integer> quantity = new ArrayList<>();
		final Order updated = new Order(1l, 1l, itemId,quantity);
		assertEquals(updated, DAO.updateDelete(1l,1l));

	}@Test
	public void testUpdateQuantity() {
		List<Long> itemId = new ArrayList<>();
		List<Integer> quantity = new ArrayList<>();
		itemId.add(1l);
		quantity.add(7);
		final Order updated = new Order(1l, 1l, itemId,quantity);
		assertEquals(updated, DAO.updateQuantity(1l,1l,7));

	}

	@Test
	public void testDelete() {
		
		assertEquals(1, DAO.delete(1));
	}
}
