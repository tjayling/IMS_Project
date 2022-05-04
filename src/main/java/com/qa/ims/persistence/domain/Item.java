package com.qa.ims.persistence.domain;

import java.util.Objects;

public class Item {
	private Long id;
	private String name;
	private Double value;
	
	public Item(String name, Double value) {
		this.setName(name);
		this.setValue(value);
	}
	
	public Item(Long id, String name, Double value) {
		this.setId(id);
		this.setName(name);
		this.setValue(value);
	}

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "id: " + id + " name: " + name + " value: " + value;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, name, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(value, other.value);
	}
}
