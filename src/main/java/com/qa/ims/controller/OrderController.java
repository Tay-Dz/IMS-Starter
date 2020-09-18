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
	 * Reads all orders to the logger
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
	 * Creates an order by taking in user input
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
			}while(!again.equals("yes") && !again.equals("no"));
		}
		Order order = orderDAO.create(new Order(customerId, itemId,quantity));
		LOGGER.info("Order created");
		return order;
	}

	/**
	 * Updates an existing order by taking in user input
	 */
	@Override
	public Order update() {
		List<Long> itemId = new ArrayList<>();
		List<Integer> quantity = new ArrayList<>();
		boolean correctInput = false;
		Long customerId = null;
		Long itemIdLong;
		int quantityInt;
		Order order = new Order(customerId, itemId,quantity);
		LOGGER.info("Please enter the id of the order you would like to update");
		Long id = utils.getLong();
		LOGGER.info("How do you want to update the order: \n"
				+ "CUSTOMER: To change the customer id \nADD: To add an item to the order, \n"
				+ "DELETE: To remove an item from the order \nQUANTITY: To change the quantity of an item");
		while(!correctInput) {
			String updateChoice = utils.getString();
			switch(updateChoice) {
				
			case "customer":
				LOGGER.info("Please enter the new customer id");
				customerId = utils.getLong(); 
				order = orderDAO.update(new Order(id,customerId, itemId,quantity));
				correctInput  =true;
				break;
			case "add":
				LOGGER.info("Please enter the item id you would like to add");
				itemIdLong = utils.getLong();
				LOGGER.info("Please enter a quantity");
				quantityInt = utils.getInt();
				order = orderDAO.updateAdd(id,itemIdLong,quantityInt);
				correctInput  =true;
				break;
			case "delete":
				LOGGER.info("Please enter the item id you would like to delete");
				itemIdLong = utils.getLong();
				order = orderDAO.updateDelete(id, itemIdLong);
				correctInput  =true;
				break;
			case "quantity":
				LOGGER.info("Please enter the item id you would like to change the quantity of");
				itemIdLong = utils.getLong();
				LOGGER.info("Please enter a new quantity");
				quantityInt = utils.getInt();
				order = orderDAO.updateQuantity(id,itemIdLong,quantityInt);
				correctInput  =true;
				break;
			default: LOGGER.info("Please enter CUSTOMER, ADD, DELETE or QUANTITY.");
			}
		}
		LOGGER.info("Order Updated");
		return order;
	}

	/**
	 * Deletes an existing order by the id of the order
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
