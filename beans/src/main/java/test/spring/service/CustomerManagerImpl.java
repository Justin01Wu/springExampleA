package test.spring.service;

import org.springframework.transaction.annotation.Transactional;

import test.spring.beans.Customer;

public class CustomerManagerImpl implements CustomerManager {
 
    private CustomerDAO customerDAO;
 
    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
 
    // @Transactional is inside spring-tx:jar
    @Override
    @Transactional
    public void createCustomer(Customer cust) {
        customerDAO.create(cust);
    }
 
}