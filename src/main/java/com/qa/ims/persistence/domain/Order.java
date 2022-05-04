package com.qa.ims.persistence.domain;

import java.util.List;
import java.util.Objects;

public class Order {

	private Long id;
	private Long customer_id;
	private List<Long> item_ids;
	
	public Order(Long customer_id, List<Long> item_ids) {
		this.setCustomerId(customer_id);
		this.setItemIds(item_ids);
	}
	
	public Order(Long id, Long customer_id, List<Long> item_ids) {
		this.setId(id);
		this.setCustomerId(customer_id);
		this.setItemIds(item_ids);
	}
	
	public Order(Long id, Long customer_id, Long item_id) {
		this.setId(id);
		this.setCustomerId(customer_id);
		this.setItemIds(List.of(item_id));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customer_id;
	}

	public void setCustomerId(Long customer_id) {
		this.customer_id = customer_id;
	}

	public List<Long> getItemIds() {
		return item_ids;
	}

	public void setItemIds(List<Long> item_ids) {
		this.item_ids = item_ids;
	}

	@Override
	public String toString() {
		return "id:" + id + ", customer_id:" + customer_id + ", item_ids:" + item_ids;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customer_id, id, item_ids);
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
		return Objects.equals(customer_id, other.customer_id) && Objects.equals(id, other.id)
				&& Objects.equals(item_ids, other.item_ids);
	}
	
	
	
}
