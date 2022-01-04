package edu.neu.customertracker.dao;

import edu.neu.customertracker.entity.Customer;
import java.util.List;

public interface CustomerDAO {

  List<Customer> getCustomers();

  void saveCustomer(Customer customer);
}
