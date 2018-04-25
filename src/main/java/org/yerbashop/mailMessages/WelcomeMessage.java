package org.yerbashop.mailMessages;

import org.springframework.mail.SimpleMailMessage;
import org.yerbashop.model.UsersValidate;

public class WelcomeMessage implements MessageCreator {

	private SimpleMailMessage message;

	public WelcomeMessage(UsersValidate user) {

		message = new SimpleMailMessage(); 
		message.setTo(user.getEmail()); 
		message.setSubject("Welcome in YerbaShop.org"); 
		message.setText("Hello, "+user.getFirstname()+","
		+ "\n\nThank you for registering in our store."
		+ "\n\nYour login details:"
		+ "\nLogin: "+user.getUsername()
		+ "\nPassword: "+user.getPassword()
		+ "\n\n\tYerbaShop.org! cheers mate - yerba mate");
	}

	@Override
	public SimpleMailMessage getMessage() {
		return message;
	}
}
