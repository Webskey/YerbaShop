package org.yerbashop.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yerbashop.dao.SaveDao;
import org.yerbashop.model.Orders;
import org.yerbashop.model.Products;
import org.yerbashop.model.Users;

@Service
public class SaveOrdersService {

	@Autowired
	private SaveDao saveDao;

	private Orders order;

	@Transactional
	public void saveOrders(Users user, Set<Products> productsList) {

		this.order = new Orders();
		order.setUsers(user);
		order.setProductsList(productsList);

		saveDao.save(order);
	}

	public Orders getOrder(){
		return order;
	}
}
