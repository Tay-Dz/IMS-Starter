package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAO implements Dao<Order>{
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		Long customerId = resultSet.getLong("customer_id");
		List<Long> itemId = new ArrayList<>();
		List<Integer> quantity = new ArrayList<>();
		itemId.add(resultSet.getLong("item_id"));
		quantity.add(resultSet.getInt("quantity"));
		return new Order(id, customerId,itemId,quantity);
	}
	
	public Order modelFromResultSetNullList(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		Long customerId = resultSet.getLong("customer_id");
		List<Long> itemId = new ArrayList<>();
		List<Integer> quantity = new ArrayList<>();
		return new Order(id, customerId,itemId,quantity);
	}
	
	private Long idFromResultSet(ResultSet resultSet) throws SQLException{
		return resultSet.getLong("id");
	}
	private Double totalPriceFromResultSet(ResultSet resultSet) throws SQLException{
		return resultSet.getDouble("total");
	}

	/**
	 * Reads all orders from the database
	 * 
	 * @return A list of orders
	 */
	@Override
	public List<Order> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("select * from order_customer c join order_products p  on c.id=p.id");) {
			List<Order> orders = new ArrayList<>();
			while (resultSet.next()) { 
				boolean addedOrder =false;
				Order newOrder = modelFromResultSet(resultSet);
				for(Order orderInList:orders) {
					if(newOrder.getId() == orderInList.getId()) {
						orderInList.addItemId(newOrder.getItemId().get(0));
						orderInList.addQuantity(newOrder.getQuantity().get(0));
						addedOrder = true;
					}
				}
				if(!addedOrder) {
					orders.add(newOrder);
				}
			}
			for(Order orderInList:orders) {
				ResultSet totalPrice = statement.executeQuery("SELECT SUM(op.quantity* p.price) as total FROM items p JOIN order_products op ON p.id=op.item_id WHERE op.id ="+	orderInList.getId());
				totalPrice.next();
				orderInList.setOrderTotal(totalPriceFromResultSet(totalPrice));
			}
			return orders;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	public Order readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM order_customer ORDER BY id DESC LIMIT 1");) {
			resultSet.next();
			Order latest =  modelFromResultSetNullList(resultSet);
			ResultSet resultSet2 = statement.executeQuery("select * from order_customer c join order_products p  on c.id=p.id WHERE c.id ="+latest.getId());
			while (resultSet2.next()) { 
				Order newOrder = modelFromResultSet(resultSet2);
				latest.addItemId(newOrder.getItemId().get(0));
				latest.addQuantity(newOrder.getQuantity().get(0));
			}
			return latest;
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Creates a order in the database
	 * 
	 * @param order - takes in a order object. id will be ignored
	 */
	@Override
	public Order create(Order order) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("INSERT INTO order_customer(customer_id) values('" + order.getCustomerId()+ "')");
			ResultSet resultSet = statement.executeQuery("SELECT id FROM order_customer WHERE customer_id ="+order.getCustomerId()+" ORDER BY id DESC LIMIT 1");
			resultSet.next();
			Long orderId = idFromResultSet(resultSet);
			for(int i=0;i<order.getItemId().size();i++) {
				statement.executeUpdate("INSERT INTO order_products(id,item_id,quantity) values('" + orderId + "','" 
						+ order.getItemId().get(i) + "','" + order.getQuantity().get(i) + "')");
			}
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public Order readOrder(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM order_customer where id = " + id);) {
			resultSet.next();
			Order orderToRead =  modelFromResultSetNullList(resultSet);
			ResultSet resultSet2 = statement.executeQuery("SELECT * FROM order_customer JOIN order_products ON order_customer.id=order_products.id WHERE order_customer.id ="+id);
			while (resultSet2.next()) { 
				Order newOrder = modelFromResultSet(resultSet2);
				orderToRead.addItemId(newOrder.getItemId().get(0));
				orderToRead.addQuantity(newOrder.getQuantity().get(0));
			}
			return orderToRead;
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	public Order readOrderCustomer(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM order_customer where id = " + id);) {
			resultSet.next();
			Order orderToRead =  modelFromResultSetNullList(resultSet);
			return orderToRead;
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Updates an order in the database by changing the customer id
	 * 
	 * @param order - takes in a order object, the id field will be used to
	 *                 update that order in the database
	 * @return
	 */
	@Override
	public Order update(Order order) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("update order_customer set customer_id ='" + order.getCustomerId() +"' where id =" + order.getId());
			return readOrderCustomer(order.getId());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	/**
	 * Updates an order in the database by adding a new item
	 * 
	 * @param Long , Long, int - takes in an order id, item id and quantity, used to insert
	 * 					into a specific order
	 * @return
	 */
	public Order updateAdd(Long id, Long itemId, int quantity) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("INSERT INTO order_products(id,item_id,quantity) values('" + id + "','" 
					+ itemId + "','" + quantity + "')");
			return readOrder(id);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	/**
	 * Updates an order in the database by deleting an item
	 * 
	 * @param Long , Long - takes in an order id and item id, used to delete a specific item from
	 * 					a specific order
	 * @return
	 */
	public Order updateDelete(Long id, Long itemId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("delete from order_products where id = " + id+" and item_id ="+itemId);
			return readOrder(id);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	/**
	 * Updates a order in the database by changing the quantity of an item
	 * 
	 * @param Long , Long, int - takes in an order id, item id and quantity, used to change
	 * 					the quantity of an item within an order
	 * @return
	 */
	public Order updateQuantity(Long id, Long itemId, int quantity) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("update order_products set quantity = '"+quantity+"' where id = " + id+" and item_id ="+itemId);
			return readOrder(id);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Deletes an order in the database
	 * 
	 * @param id - id of the order
	 */
	@Override
	public int delete(long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("delete from order_products where id = " + id);
			return statement.executeUpdate("delete from order_customer where id = " + id);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}


}
