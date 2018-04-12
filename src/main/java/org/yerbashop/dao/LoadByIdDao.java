package org.yerbashop.dao;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoadByIdDao<T>{

	private String clazz;

	@Autowired
	private SessionFactory sessionFactory;

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	public T findUserById(Serializable id) {
		try {
			return (T) sessionFactory.getCurrentSession().load(Class.forName(clazz), id);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}