package org.yerbashop;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * <h1>Email configuration class.</h1>
 * Sets properties of JavaMailSender.
 *
 * @author  <a href="https://github.com/Webskey">Webskey</a>
 * @since   2018-03-25
 */

@Configuration
public class EmailConfig {

	@Autowired
	Credentials credentials;
	/**
	 * @return JavaMailSenderImpl set to connect gmail account.
	 */
	@Bean
	public JavaMailSenderImpl getMailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername(String.valueOf(credentials.getEmailUsername()));
		mailSender.setPassword(String.valueOf(credentials.getEmailPassword()));

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.transport.protocol", "smtp");
		//javaMailProperties.put("mail.debug", "true");//Prints out everything on screen

		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}
}
