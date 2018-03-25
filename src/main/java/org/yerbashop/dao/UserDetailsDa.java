package org.yerbashop.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yerbashop.model.Users;

@Repository
public class UserDetailsDa  {

	@Autowired
	private SessionFactory sessionFactory;

	public Users findUserByUsername(String username) {

		return sessionFactory.getCurrentSession().get(Users.class, username);
	}
}