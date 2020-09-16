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

import com.qa.ims.controller.AccountController;
import com.qa.ims.persistence.dao.AccountDAO;
import com.qa.ims.persistence.domain.Account;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

	@Mock
	private Utils utils;

	@Mock
	private AccountDAO dao;

	@InjectMocks
	private AccountController controller;

	@Test
	public void testCreate() {
		final String U_NAME = "barry", PASSWORD = "scott";
		final Boolean IS_ADMIN = true;
		final Account created = new Account(U_NAME, PASSWORD,IS_ADMIN);

		Mockito.when(utils.getString()).thenReturn(U_NAME, PASSWORD);
		Mockito.when(utils.getBoolean()).thenReturn(IS_ADMIN);
		Mockito.when(dao.create(created)).thenReturn(created);

		assertEquals(created, controller.create());

		Mockito.verify(utils, Mockito.times(2)).getString();
		Mockito.verify(utils, Mockito.times(1)).getBoolean();
		Mockito.verify(dao, Mockito.times(1)).create(created);
	}

	@Test
	public void testReadAll() {
		List<Account> account = new ArrayList<>();
		account.add(new Account(1l,"root", "root",true));

		Mockito.when(dao.readAll()).thenReturn(account);

		assertEquals(account, controller.readAll());

		Mockito.verify(dao, Mockito.times(1)).readAll();
	}

	@Test
	public void testUpdate() {
		Account updated = new Account(1l,"root", "root",true);

		Mockito.when(this.utils.getLong()).thenReturn(1L);
		Mockito.when(this.utils.getString()).thenReturn(updated.getUserName(), updated.getPassword());
		Mockito.when(utils.getBoolean()).thenReturn(updated.getIsAdmin());
		Mockito.when(this.dao.update(updated)).thenReturn(updated);

		assertEquals(updated, this.controller.update());

		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.utils, Mockito.times(2)).getString();
		Mockito.verify(utils, Mockito.times(1)).getBoolean();
		Mockito.verify(this.dao, Mockito.times(1)).update(updated);
	}

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
