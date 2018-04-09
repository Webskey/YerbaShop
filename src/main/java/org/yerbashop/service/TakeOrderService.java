package org.yerbashop.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yerbashop.mailMessages.OrderToAdmin;
import org.yerbashop.mailMessages.OrderToUser;
import org.yerbashop.model.Products;
import org.yerbashop.model.Users;

@Service
public class TakeOrderService implements Runnable{

	@Autowired
	private EmailService emailService;

	@Autowired
	private SaveOrdersService saveOrdersService;

	@Autowired
	private UserProfileService userProfileService;

	private Users user;
	private String username;
	private Set<Products> orderList;
	private Thread thread;

	public void setOrder(String username, Set<Products> order) {
		this.username = username;
		orderList = new HashSet<Products>();
		orderList.addAll(order);
	}
	
	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		user = userProfileService.getUser(username);
		emailService.sendEmail(new OrderToAdmin(user,orderList));
		emailService.sendEmail(new OrderToUser(user,orderList));
		saveOrdersService.saveOrders(user, orderList);			
	}
}