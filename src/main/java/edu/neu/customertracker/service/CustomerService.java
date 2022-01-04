package edu.neu.customertracker.service;

import edu.neu.customertracker.entity.Customer;
import java.util.List;

public interface CustomerService {

  List<Customer> getCustomers();

  void saveCustomer(Customer customer);

  Customer getCustomer(int id);

  void deleteCustomer(int id);
}
