package org.yerbashop;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

	@Autowired
	Credentials credentials;

	@Bean
	public JavaMailSenderImpl getMailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername(credentials.getEmailUsername());
		mailSender.setPassword(credentials.getEmailPassword());

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.transport.protocol", "smtp");
		//javaMailProperties.put("mail.debug", "true");//Prints out everything on screen

		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}
}
