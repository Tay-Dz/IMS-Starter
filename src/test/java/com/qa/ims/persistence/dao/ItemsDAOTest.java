package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Items;
import com.qa.ims.utils.DBUtils;

public class ItemsDAOTest {
	
	private final ItemsDAO DAO = new ItemsDAO();

	@Before
	public void setup() {
		DBUtils.connect("src/test/resources/db.properties");
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}

	@Test
	public void testCreate() {
		final Items created = new Items(4L, "PC", 549.99);
		assertEquals(created, DAO.create(created));
	}

	@Test
	public void testReadAll() {
		List<Items> expected = new ArrayList<>();
		expected.add(new Items(1L, "book", 6.99));
		expected.add(new Items(2L, "tv", 199.99));
		expected.add(new Items(3L, "ball", 0.99));
		assertEquals(expected, DAO.readAll());
	}

	@Test
	public void testReadLatest() {
		assertEquals(new Items(3L, "ball", 0.99), DAO.readLatest());
	}

	@Test
	public void testRead() {
		final long ID = 1L;
		assertEquals(new Items(ID, "book", 6.99), DAO.readItems(ID));
	}

	@Test
	public void testUpdate() {
		final Items updated = new Items(1L, "tv", 449.99);
		assertEquals(updated, DAO.update(updated));

	}

	@Test
	public void testDelete() {
		assertEquals(1, DAO.delete(3));
	}
}
