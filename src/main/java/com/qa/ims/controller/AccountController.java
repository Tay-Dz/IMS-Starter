package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.AccountDAO;
import com.qa.ims.persistence.domain.Account;
import com.qa.ims.utils.Utils;

/**
 * Takes in Account details for CRUD functionality
 *
 */
public class AccountController implements CrudController<Account> {

	public static final Logger LOGGER = LogManager.getLogger();

	private AccountDAO accountDAO;
	private Utils utils;

	public AccountController(AccountDAO accountDAO, Utils utils) {
		super();
		this.accountDAO = accountDAO;
		this.utils = utils;
	}
	
	public Boolean logIn() {
		Boolean isAdmin = null;
		while(isAdmin==null) {
			LOGGER.info("Please enter your user name");
			String userName = utils.getString();
			LOGGER.info("Please enter your password");
			String password = utils.getString();
			isAdmin = accountDAO.logIn(userName,password);
			if (isAdmin == null) {
				System.out.println("Incorrect login. Try again.");
			}
		}
		return isAdmin;
	}

	/**
	 * Reads all Accounts to the logger
	 */
	@Override
	public List<Account> readAll() {
		List<Account> accounts = accountDAO.readAll();
		for (Account account : accounts) {
			LOGGER.info(account.toString());
		}
		return accounts;
	}

	/**
	 * Creates a Account by taking in user input
	 */
	@Override
	public Account create() {
		LOGGER.info("Please enter a user name");
		String userName = utils.getString();
		LOGGER.info("Please enter a password");
		String password = utils.getString();
		LOGGER.info("Do you want this person to be an admin? [TRUE OR FALSE]");
		Boolean isAdmin = utils.getBoolean();
		Account account = accountDAO.create(new Account(userName, password,isAdmin));
		LOGGER.info("Account created");
		return account;
	}

	/**
	 * Updates an existing Account by taking in user input
	 */
	@Override
	public Account update() {
		LOGGER.info("Please enter the id of the Account you would like to update");
		Long id = utils.getLong();
		LOGGER.info("Please enter a user name");
		String userName = utils.getString();
		LOGGER.info("Please enter a password");
		String password = utils.getString();
		LOGGER.info("Do you want this person to be an admin? [TRUE OR FALSE]");
		Boolean isAdmin = utils.getBoolean();
		Account account = accountDAO.update(new Account(id, userName, password,isAdmin));
		LOGGER.info("Account Updated");
		return account;
	}

	/**
	 * Deletes an existing Account by the id of the Account
	 * 
	 * @return
	 */
	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the Account you would like to delete");
		Long id = utils.getLong();
		return accountDAO.delete(id);
	}

}
