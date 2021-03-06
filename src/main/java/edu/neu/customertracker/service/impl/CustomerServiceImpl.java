package edu.neu.customertracker.service.impl;

import edu.neu.customertracker.dao.CustomerDAO;
import edu.neu.customertracker.entity.Customer;
import edu.neu.customertracker.service.CustomerService;
import edu.neu.customertracker.util.CustomerFields;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerDAO customerDAO;

  @Override
  @Transactional
  public List<Customer> getCustomers(CustomerFields sortField, String searchName) {
    return customerDAO.getCustomers(sortField, searchName);
  }

  @Override
  @Transactional
  public void saveCustomer(Customer customer) {
    customerDAO.saveCustomer(customer);
  }

  @Override
  @Transactional
  public Customer getCustomer(int id) {
    return customerDAO.getCustomer(id);
  }

  @Override
  @Transactional
  public void deleteCustomer(int id) {
    customerDAO.deleteCustomer(id);
  }
}
