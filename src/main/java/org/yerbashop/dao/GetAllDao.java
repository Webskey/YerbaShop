package org.yerbashop.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GetAllDao<T> {

	private String clazz;

	@Autowired
	private SessionFactory sessionFactory;

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return sessionFactory.getCurrentSession().createQuery( "from " + clazz).getResultList();
	}
}
