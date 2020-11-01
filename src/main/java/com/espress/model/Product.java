package com.espress.model;

import java.util.UUID;

public class Product {

	private String id;
	private String name;
	private Type type;
	private String description;

	public Product() {

	}

	public Product(String name, Type type, String description) {
		this.name = name;
		this.type = type;
		this.description = description;
		this.id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", type=" + type + ", description=" + description + "]";
	}

}
