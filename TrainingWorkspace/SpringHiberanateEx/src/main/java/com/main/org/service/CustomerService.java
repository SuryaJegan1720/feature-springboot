package com.main.org.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.org.dao.CustomerDao;
import com.main.org.model.Customer;

@Service
public class CustomerService {
	@Autowired
	CustomerDao custoDao;
	@Transactional
	public List<Customer> getAllCustomers() {
		return custoDao.getAllCustomers();
	}
	
	@Transactional
	public Customer getCustomer(int id) {
		return custoDao.getCustomer(id);
	}

	@Transactional
	public void addCustomer(Customer customer) {
		custoDao.addCustomer(customer);
	}

	@Transactional
	public void updateCustomer(Customer customer) {
		custoDao.updateCustomer(customer);

	}

	@Transactional
	public void deleteCustomer(int id) {
		custoDao.deleteCustomer(id);
	}

}
