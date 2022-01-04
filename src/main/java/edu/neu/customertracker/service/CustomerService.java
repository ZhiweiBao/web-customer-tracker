package edu.neu.customertracker.service;

import edu.neu.customertracker.entity.Customer;
import edu.neu.customertracker.util.CustomerFields;
import java.util.List;

public interface CustomerService {

  List<Customer> getCustomers(CustomerFields sortField, String searchName);

  void saveCustomer(Customer customer);

  Customer getCustomer(int id);

  void deleteCustomer(int id);
}
