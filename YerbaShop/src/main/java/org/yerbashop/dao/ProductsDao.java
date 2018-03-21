package org.yerbashop.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yerbashop.model.Products;

@Repository
public class ProductsDao  {

  @Autowired
  private SessionFactory sessionFactory;

  @SuppressWarnings("unchecked")
  public List<Products> getAllProducts() {
	  
    return sessionFactory.getCurrentSession().createQuery("from Products").getResultList();
  }
}