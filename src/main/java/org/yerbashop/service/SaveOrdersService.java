package org.yerbashop.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yerbashop.dao.LoadByIdDao;
import org.yerbashop.dao.SaveDao;
import org.yerbashop.model.Orders;
import org.yerbashop.model.Products;
import org.yerbashop.model.Users;
import org.yerbashop.model.UsersDTO;

@Service
public class SaveOrdersService {

	@Autowired
	private SaveDao saveDao;
	
	@Autowired
	private LoadByIdDao<Users> loadByIdDao;

	@Transactional
	public void saveOrders(UsersDTO user, Set<Products> productsList) {

		Orders order = new Orders();
		order.setUsers(loadByIdDao.findUserById(user.getUsername()));
		order.setProductsList(productsList);

		saveDao.save(order);
	}
}
