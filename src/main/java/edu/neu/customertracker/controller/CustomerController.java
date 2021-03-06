package edu.neu.customertracker.controller;

import edu.neu.customertracker.entity.Customer;
import edu.neu.customertracker.service.CustomerService;
import edu.neu.customertracker.util.CustomerFields;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/customer")
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  @GetMapping("/list")
  public String listCustomers(
      @RequestParam(required = false) String sort,
      @RequestParam(value = "searchName", required = false) String searchName,
      Model model) {
    // get customers from the service
    List<Customer> customers;

    // check for sort field
    if (sort != null) {
      CustomerFields sortField = Enum.valueOf(CustomerFields.class, sort);
      customers = customerService.getCustomers(sortField, searchName);
    } else {
      // no sort field provided ... default to sorting by last name
      customers = customerService.getCustomers(CustomerFields.LAST_NAME, searchName);
    }

    // add searchName to the model
    model.addAttribute("searchName", searchName);

    // add the customers to the model
    model.addAttribute("customers", customers);
    return "list-customers";
  }

  @GetMapping("/showFormForAdd")
  public String showFormForAdd(Model model) {
    // create model attribute to bind form data
    Customer customer = new Customer();
    model.addAttribute("customer", customer);
    return "customer-form";
  }

  @PostMapping("/saveCustomer")
  public String saveCustomer(@ModelAttribute("customer") Customer customer) {
    // save the customer using our service
    customerService.saveCustomer(customer);
    return "redirect:/customer/list";
  }

  @GetMapping("/showFormForUpdate")
  public String showFormForUpdate(@RequestParam("customerId") int id, Model model) {
    // get the customer from the service
    Customer customer = customerService.getCustomer(id);
    // set customer as a model attribute to pre-populate the form
    model.addAttribute("customer", customer);
    // send over to the form
    return "customer-form";
  }

  @GetMapping("/delete")
  public String deleteCustomer(@RequestParam("customerId") int id) {
    // delete the customer from the service
    customerService.deleteCustomer(id);
    return "redirect:/customer/list";
  }
}


