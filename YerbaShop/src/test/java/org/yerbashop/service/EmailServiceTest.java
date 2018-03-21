package org.yerbashop.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yerbashop.EmailConfig;
import org.yerbashop.Credentials;
import org.yerbashop.mailMessages.OrderToAdmin;
import org.yerbashop.mailMessages.OrderToUser;
import org.yerbashop.mailMessages.WelcomeMessage;
import org.yerbashop.model.UsersDTO;
import org.yerbashop.model.Products;
import org.yerbashop.model.Users;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmailConfig.class, Credentials.class})
public class EmailServiceTest {
	
	@Spy
	private JavaMailSenderImpl mailSender;
	
	@InjectMocks
	private EmailService emailService;

    private UsersDTO userDTO;

    private GreenMail testSmtp;
	
	@Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);
    	
        testSmtp = new GreenMail(ServerSetupTest.SMTP);
        testSmtp.start();
 
        mailSender.setPort(3025);
        mailSender.setHost("localhost");
        
    	userDTO = new UsersDTO();
    	userDTO.setUsername("username");
    	userDTO.setPassword("password");
    	userDTO.setFirstname("firstname");
    	userDTO.setLastname("lastname");
    	userDTO.setEmail("email@email.com");
    	userDTO.setPhoneNr("phoneNr");
    }
	
	@After
	public void cleanup(){
	    testSmtp.stop();
	}
	
	private Set<Products> list(){
		
		Set<Products> list = new HashSet<>();
		
		Products p1 = new Products();
		p1.setName("Yerba Mate");
		
		Products p2 = new Products();
		p2.setName("Green Mate");
		
		list.add(p1);
		list.add(p2);
		
		return list;
	}
	
	private Users user() {
		Users user = new Users();
		user.setUsername("username");
		user.setFirstname("firstname");
		user.setLastname("lastname");
		user.setEmail("email@email.com");
		user.setPhoneNr("phoneNr");
		return user;
	}
	
	@Test
	public void shouldReturnWelcomeMessageDetails_whenMethodSendEmailInvokedProperly() throws MessagingException {
		WelcomeMessage welcomeMessage = new WelcomeMessage(userDTO);
		emailService.sendEmail(welcomeMessage);
	
		Message[] messages = testSmtp.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals(welcomeMessage.getMessage().getSubject(), messages[0].getSubject());
        String body = GreenMailUtil.getBody(messages[0]);
        assertEquals(welcomeMessage.getMessage().getText(), body);
        assertEquals(userDTO.getEmail(), messages[0].getAllRecipients()[0].toString());
	}
	
	@Test
	public void shouldReturnOrderToAdmin_whenMethodSendEmailInvokedProperly() throws MessagingException, IOException {
		OrderToAdmin orderToAdmin = new OrderToAdmin(user(), list());
		emailService.sendEmail(orderToAdmin);
	
		Message[] messages = testSmtp.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals(orderToAdmin.getMessage().getSubject(), messages[0].getSubject());
        String body = GreenMailUtil.getBody(messages[0]).replaceAll("[\\r\\n]+", "");
        assertEquals(orderToAdmin.getMessage().getText().replaceAll("[\\r\\n]+", ""), body);
        assertEquals("YerbaShop.project@gmail.com", messages[0].getAllRecipients()[0].toString());
	}
	
	@Test
	public void shouldReturnOrderToUser_whenMethodSendEmailInvokedProperly() throws MessagingException, IOException {
		OrderToUser orderToUser = new OrderToUser(user(), list());
		emailService.sendEmail(orderToUser);
	
		Message[] messages = testSmtp.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals(orderToUser.getMessage().getSubject(), messages[0].getSubject());
        String body = GreenMailUtil.getBody(messages[0]).replaceAll("[\\r\\n]+", "");
        assertEquals(orderToUser.getMessage().getText().replaceAll("[\\r\\n]+", ""), body);
        assertEquals(user().getEmail(), messages[0].getAllRecipients()[0].toString());
	}
	
	@Test
	public void shouldReturnCustomMessageDetails_whenCustomMessageSentThroughMailSender() throws MessagingException {
		
		SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("email@email.com");
        message.setSubject("subject");
        message.setText("text");
        mailSender.send(message);
        
        Message[] messages = testSmtp.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("subject", messages[0].getSubject());
        String body = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
        assertEquals("text", body);
        assertEquals("email@email.com", messages[0].getAllRecipients()[0].toString());
	}
	
	@Test(expected=java.lang.NullPointerException.class)
	public void shouldThrowNullPointerException_whenEmailServiceInvokedWithNullArgument() throws MessagingException {
		emailService.sendEmail(null);
	}
	
	@Test(expected=java.lang.NullPointerException.class)
	public void shouldThrowNullPointerException_whenEmailFieldIsNull() throws MessagingException {
		userDTO.setEmail(null);
		emailService.sendEmail(new WelcomeMessage(userDTO));
	}

}
