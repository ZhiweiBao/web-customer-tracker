package edu.neu.customertracker.controller;

import edu.neu.customertracker.dao.CustomerDAO;
import edu.neu.customertracker.entity.Customer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {

  @Autowired
  private CustomerDAO customerDAO;

  @RequestMapping("/list")
  public String listCustomers(Model model) {
    // get customers from the dao
    List<Customer> customers = customerDAO.getCustomers();

    // add the customers to the model
    model.addAttribute("customers", customers);
    return "list-customers";
  }

}


