package org.yerbashop.mailMessages;

import org.springframework.mail.SimpleMailMessage;
import org.yerbashop.model.UsersDTO;

public class WelcomeMessage implements MessageCreator {
	
	SimpleMailMessage message;
	 
    public WelcomeMessage(UsersDTO user) {
    	
    	message = new SimpleMailMessage(); 
    	message.setTo(user.getEmail()); 
        message.setSubject("Welcome in YerbaShop.org"); 
        message.setText("Hello, "+user.getFirstname()+" Your login details: Login: "+user.getUsername()+" Password: "+user.getPassword()+"YerbaShop.org cheers mate, yerba mate");
    }

	@Override
	public SimpleMailMessage getMessage() {
		return message;
	}

}
