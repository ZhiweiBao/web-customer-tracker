package edu.neu.customertracker.dao.impl;

import edu.neu.customertracker.dao.CustomerDAO;
import edu.neu.customertracker.entity.Customer;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public List<Customer> getCustomers() {
    // get the current hibernate session
    Session currentSession = sessionFactory.getCurrentSession();

    // create a query, sort by lastName
    Query<Customer> query = currentSession.createQuery("from Customer order by lastName",
        Customer.class);

    // get a list of result from the query
    return query.getResultList();
  }

  @Override
  public void saveCustomer(Customer customer) {
    // get current hibernate session
    Session currentSession = sessionFactory.getCurrentSession();

    // save/update the customer
    currentSession.saveOrUpdate(customer);
  }

  @Override
  public Customer getCustomer(int id) {
    // get current hibernate session
    Session currentSession = sessionFactory.getCurrentSession();

    // read from db using the primary key;
    return currentSession.get(Customer.class, id);
  }
}
