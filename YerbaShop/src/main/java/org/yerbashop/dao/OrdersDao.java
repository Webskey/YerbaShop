package org.yerbashop.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yerbashop.model.Orders;

@Repository
public class OrdersDao  {

  @Autowired
  private SessionFactory sessionFactory;

  @SuppressWarnings("unchecked")
  public List<Orders> getAllProducts() {
	  
    return sessionFactory.getCurrentSession().createQuery("from Orders").getResultList();
  }
}