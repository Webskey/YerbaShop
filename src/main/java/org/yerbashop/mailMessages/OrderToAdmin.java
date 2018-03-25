package org.yerbashop.mailMessages;

import java.util.Set;

import org.springframework.mail.SimpleMailMessage;
import org.yerbashop.model.Products;
import org.yerbashop.model.Users;

public class OrderToAdmin implements MessageCreator {

	SimpleMailMessage message;

	public OrderToAdmin(Users user, Set<Products> order) {

		String products = "";
		for(Products prod:order) {
			products+=prod.getName()+", ";
		}

		message = new SimpleMailMessage(); 
		message.setTo("YerbaShop.project@gmail.com"); 
		message.setSubject("New order has been placed"); 
		message.setText(user.getUsername()+" has ordered those items:\n "+products+".");
	}

	@Override
	public SimpleMailMessage getMessage() {
		return message;
	}

}
