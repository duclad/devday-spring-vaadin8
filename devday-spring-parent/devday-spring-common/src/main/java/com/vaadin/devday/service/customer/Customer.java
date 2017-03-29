package com.vaadin.devday.service.customer;

import java.util.UUID;

public class Customer {
	private String id;
	private String firstName;
	private String lastName;

	public Customer() {
		id = UUID.randomUUID().toString();
		// EJB serialization
	}

	public Customer(String firstName, String lastName) {
		id = UUID.randomUUID().toString();
		this.setFirstName(firstName);
		this.setLastName(lastName);
	}

	public Customer(Customer c) {
		this.id = c.id;
		this.firstName = c.firstName;
		this.lastName = c.lastName;
	}

	public void setDataFrom(Customer customer) {
		this.setFirstName(customer.getFirstName());
		this.setLastName(customer.getLastName());
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastThenFirstName(){
		return getLastName()+" "+getFirstName();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof Customer) {
			return this.id.equals(((Customer) obj).id);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
