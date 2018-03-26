package org.yerbashop.mailMessages;

import java.util.Set;

import org.springframework.mail.SimpleMailMessage;
import org.yerbashop.model.Products;
import org.yerbashop.model.Users;

public class OrderToUser implements MessageCreator {

	private SimpleMailMessage message;

	public OrderToUser(Users user, Set<Products> order) {

		String products = "";
		int totalPrice = 0;
		for(Products prod:order) {
			products += prod.getName() + ", price - "+ prod.getPrice() + "$\n";
			totalPrice += prod.getPrice();
		}

		message = new SimpleMailMessage(); 
		message.setTo(user.getEmail()); 
		message.setSubject("You've made a new order"); 
		message.setText("Hello "+user.getFirstname()+",\n"
				+ "You ordered those items:"
				+ "\n"+products
				+ "\nWith total price of: "+totalPrice+"$");
	}

	@Override
	public SimpleMailMessage getMessage() {
		return message;
	}
}