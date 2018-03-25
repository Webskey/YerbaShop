package org.yerbashop.mailMessages;

import java.util.Set;

import org.springframework.mail.SimpleMailMessage;
import org.yerbashop.model.Products;
import org.yerbashop.model.Users;

public class OrderToUser implements MessageCreator {

	SimpleMailMessage message;

	public OrderToUser(Users user, Set<Products> order) {

		String products = "";
		for(Products prod:order) {
			products+=prod.getName()+", ";
		}

		message = new SimpleMailMessage(); 
		message.setTo(user.getEmail()); 
		message.setSubject("You've made a new order"); 
		message.setText("Hello "+user.getUsername()+" You ordered those items: \n"+products+".");
	}

	@Override
	public SimpleMailMessage getMessage() {
		return message;
	}

}
