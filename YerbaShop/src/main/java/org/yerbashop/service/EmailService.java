package org.yerbashop.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.yerbashop.mailMessages.MessageCreator; 
 
@Service
public class EmailService{
 
    @Autowired
    JavaMailSender mailSender;
    
    public void sendEmail(MessageCreator message) {
    	
        mailSender.send(message.getMessage());
        System.out.println("Message Send...Hurrey");
    }
}