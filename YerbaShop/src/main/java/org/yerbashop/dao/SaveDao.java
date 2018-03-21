package org.yerbashop.dao;

import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SaveDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void save(Object object)throws ConstraintViolationException {
		sessionFactory.getCurrentSession().save(object);
	}
}
