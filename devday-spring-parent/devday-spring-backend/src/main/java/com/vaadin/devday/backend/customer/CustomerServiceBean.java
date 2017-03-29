package com.vaadin.devday.backend.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.vaadin.devday.service.customer.Customer;
import com.vaadin.devday.service.customer.CustomerService;

@Component
public class CustomerServiceBean implements CustomerService {
	private List<Customer> customers = new ArrayList<Customer>(){
		{
			add(new Customer("bubu","gogu"));
		}
	};

	private static final Object LOCK = new Object();

	public CustomerServiceBean() {
		customers = new ArrayList<>();
	}

	@Override
	public void store(Customer customer) {
		synchronized (LOCK) {
			if (customers.contains(customer)) {
				Customer existingCustomer = customers.get(customers.indexOf(customer));
				existingCustomer.setDataFrom(customer);
			} else {
				customers.add(customer);
			}
		}
	}

	@Override
	public void delete(Customer customer) {
		synchronized (LOCK) {
			customers.remove(customer);
		}
	}

	@Override
	public List<Customer> getCustomers() {
		synchronized (LOCK) {
			return customers.stream().map(c -> new Customer(c)).collect(Collectors.toList());
		}
	}
}
