package com.qa.ims.persistence.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {
	
	private Long id;
	private Long customerId;
	private List<Long> itemId = new ArrayList<>();
	private List<Integer> quantity = new ArrayList<>();
	private Double orderTotal;
	
	
	public Order(Long customerId, List<Long> itemId, List<Integer> quantity) {
		super();
		this.customerId = customerId;
		this.itemId = itemId;
		this.quantity = quantity;
	}


	public Order(Long id, Long customerId, List<Long> itemId, List<Integer> quantity) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.itemId = itemId;
		this.quantity = quantity;
	}


	public Long getId() {
		return id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public List<Long> getItemId() {
		return itemId;
	}


	public void addItemId(Long itemId) {
		this.itemId.add(itemId);
	}

	public List<Integer> getQuantity() {
		return quantity;
	}

	public void addQuantity(Integer quantity) {
		this.quantity.add(quantity);
	}
	
	public Double getOrderTotal() {
		return orderTotal;
	}


	public void setOrderTotal(Double orderTotal) {
		this.orderTotal = orderTotal;
	}


	@Override
	public String toString() {
		String output = "Order: id=" + id + ", customerId=" + customerId + ": \n";
		for(int i=0;i<itemId.size();i++) {
			output+= " 		itemId=" + itemId.get(i) + ", quantity=" + quantity.get(i) + "\n";
		}
		output+="	Order Total = £"+getOrderTotal();
		return output;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result + ((orderTotal == null) ? 0 : orderTotal.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		} else if (!itemId.equals(other.itemId))
			return false;
		if (orderTotal == null) {
			if (other.orderTotal != null)
				return false;
		} else if (!orderTotal.equals(other.orderTotal))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}


	
	
	
	
	

}
