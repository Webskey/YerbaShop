package org.yerbashop.mailMessages;

import java.util.Set;

import org.springframework.mail.SimpleMailMessage;
import org.yerbashop.model.Products;
import org.yerbashop.model.UsersDTO;

public class OrderToAdmin implements MessageCreator {

	private SimpleMailMessage message;

	public OrderToAdmin(UsersDTO user, Set<Products> order) {

		String products = "";
		int totalPrice = 0;
		for(Products prod:order) {
			products += prod.getName() + ", price - "+ prod.getPrice() + "$\n";
			totalPrice += prod.getPrice();
		}
		
		message = new SimpleMailMessage(); 
		message.setTo("YerbaShop.project@gmail.com"); 
		message.setSubject("New order has been placed"); 
		message.setText("User: "+user.getUsername()
				+ "\nFirstname: " + user.getFirstname()
				+ "\nLastname: " + user.getLastname()
				+ "\nEmail: " + user.getEmail()
				+ "\nPhone: " + user.getPhoneNr()
				+ "\nAdress: " + user.getAdress()
				+ "\n\nHas ordered those items:"
				+ "\n"+products
				+ "\nWith total price of: "+totalPrice+"$");
	}

	@Override
	public SimpleMailMessage getMessage() {
		return message;
	}
}