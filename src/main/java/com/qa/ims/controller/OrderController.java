package com.qa.ims.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Order> {

	public static final Logger LOGGER = LogManager.getLogger();

	private OrderDAO orderDAO;
	private Utils utils;

	public OrderController(OrderDAO orderDAO, Utils utils) {
		super();
		this.orderDAO = orderDAO;
		this.utils = utils;
	}

	/**
	 * Reads all customers to the logger
	 */
	@Override
	public List<Order> readAll() {
		List<Order> orders = orderDAO.readAll();
		for (Order order : orders) {
			LOGGER.info(order.toString());
		}
		return orders;
	}

	/**
	 * Creates a customer by taking in user input
	 */
	@Override
	public Order create() {
		String again = "";
		boolean addAgain = true;
		List<Long> itemId = new ArrayList<>();
		List<Integer> quantity = new ArrayList<>();
		LOGGER.info("Please enter the customer ID");
		Long customerId = utils.getLong();
		while(addAgain) {
			LOGGER.info("Please enter the Item ID");
			itemId.add(utils.getLong());
			LOGGER.info("Please enter a quantity");
			quantity.add(utils.getInt());
			do {
			LOGGER.info("Do you want to add another item? [yes or no]");
			again = utils.getString();
			switch(again) {
				case "yes": break;
				case "no": addAgain = false; break;	
				default: System.out.println("incorrect input");
			}
			}while(again!="yes" && again!= "no");
		}
		Order order = orderDAO.create(new Order(customerId, itemId,quantity));
		LOGGER.info("Order created");
		return order;
	}

	/**
	 * Updates an existing customer by taking in user input
	 */
	@Override
	public Order update() {
		List<Long> itemId = new ArrayList<>();
		List<Integer> quantity = new ArrayList<>();
		boolean correctInput = false;
		Long customerId = null;
		LOGGER.info("Please enter the id of the order you would like to update");
		Long id = utils.getLong();
		LOGGER.info("Do you want to change CUSTOMER id, ADD an item, DELETE an item or change an item QUANTITY?");
		while(!correctInput) {
			String updateChoice = utils.getString();
			switch(updateChoice) {
				
			case "customer":
				LOGGER.info("Please enter the new customer id");
				customerId = utils.getLong(); break;
			case "add":
				customerId =  -9090L;
				LOGGER.info("Please enter the item id you would like to add");
				itemId.add(utils.getLong());
				LOGGER.info("Please enter a quantity");
				quantity.add(utils.getInt());break;
			case "delete":
				customerId =  -9091L;
				LOGGER.info("Please enter the item id you would like to delete");
				itemId.add(utils.getLong());break;
			case "quantity":
				customerId =  -9092L;
				LOGGER.info("Please enter the item id you would like to change the quantity of");
				itemId.add(utils.getLong());
				LOGGER.info("Please enter a new quantity");
				quantity.add(utils.getInt());break;
			default: LOGGER.info("Please enter CUSTOMER, ADD, DELETE or QUANTITY.");
			}
		}
		
		Order order = orderDAO.update(new Order(id,customerId, itemId,quantity));
		LOGGER.info("Order Updated");
		return order;
	}

	/**
	 * Deletes an existing customer by the id of the customer
	 * 
	 * @return
	 */
	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the order you would like to delete");
		Long id = utils.getLong();
		return orderDAO.delete(id);
	}

}
