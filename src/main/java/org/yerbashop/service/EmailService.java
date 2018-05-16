package org.yerbashop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.yerbashop.mailMessages.MessageCreator; 

/**
 * <h1>EmailService class.</h1>
 *
 * @author  <a href="https://github.com/Webskey">Webskey</a>
 * @since   2018-03-25
 */

@Service
public class EmailService{

	@Autowired
	private JavaMailSender mailSender;

	public void sendEmail(MessageCreator message) {
		mailSender.send(message.getMessage());
	}
}