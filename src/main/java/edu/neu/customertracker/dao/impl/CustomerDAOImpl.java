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

    // read from db using the primary key
    return currentSession.get(Customer.class, id);
  }

  @Override
  public void deleteCustomer(int id) {
    // get current hibernate session
    Session currentSession = sessionFactory.getCurrentSession();

    // delete the customer using the primary key
    currentSession.createQuery("delete from Customer where id=:customerId")
        .setParameter("customerId", id)
        .executeUpdate();
  }

  @Override
  public List<Customer> searchCustomers(String searchName) {
    // get the current hibernate session
    Session currentSession = sessionFactory.getCurrentSession();

    Query<Customer> query;

    //
    // only search by name if theSearchName is not empty
    //
    if (searchName != null && searchName.trim().length() > 0) {
      // search for firstName or lastName ... case insensitive
      query = currentSession.createQuery(
          "from Customer where lower(firstName) like :theName or lower(lastName) like :theName order by lastName",
          Customer.class);
      query.setParameter("theName", "%" + searchName.toLowerCase() + "%");
    } else {
      // theSearchName is empty ... so just get all customers
      query = currentSession.createQuery("from Customer order by lastName", Customer.class);
    }

    // execute query and get result list
    return query.getResultList();
  }
}
