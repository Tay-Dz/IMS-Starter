package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Account;
import com.qa.ims.utils.DBUtils;

public class AccountDAOTest {

	private final AccountDAO DAO = new AccountDAO();

	@Before
	public void setup() {
		DBUtils.connect("src/test/resources/db.properties");
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}
	
	@Test 
	public void testLogin() {
		assertEquals(true,DAO.logIn("root", "root"));
	}

	@Test
	public void testCreate() {
		final Account created = new Account(2L, "root2", "root2",false);
		assertEquals(created, DAO.create(created));
	}

	@Test
	public void testReadAll() {
		List<Account> expected = new ArrayList<>();
		expected.add(new Account(1L, "root", "root",true));
		assertEquals(expected, DAO.readAll());
	}

	@Test
	public void testReadLatest() {
		assertEquals(new Account(1L, "root", "root",true), DAO.readLatest());
	}

	@Test
	public void testRead() {
		final long ID = 1L;
		assertEquals(new Account(ID, "root", "root",true), DAO.readAccount(ID));
	}

	@Test
	public void testUpdate() {
		final Account updated = new Account(1L, "root2", "root2",false);
		assertEquals(updated, DAO.update(updated));

	}

	@Test
	public void testDelete() {
		assertEquals(1, DAO.delete(1));
	}
}
