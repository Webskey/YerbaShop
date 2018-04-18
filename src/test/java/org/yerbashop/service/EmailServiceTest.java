package org.yerbashop.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
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
import org.yerbashop.Credentials;
import org.yerbashop.EmailConfig;
import org.yerbashop.dummybuilders.ProductsBuilder;
import org.yerbashop.dummybuilders.UsersModelBuilder;
import org.yerbashop.mailMessages.OrderToAdmin;
import org.yerbashop.mailMessages.OrderToUser;
import org.yerbashop.mailMessages.WelcomeMessage;
import org.yerbashop.model.Products;
import org.yerbashop.model.UsersDTO;
import org.yerbashop.model.UsersValidate;

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

	private UsersValidate userValidate;
	private UsersDTO usersDTO;
	private Set<Products> products;

	private GreenMail testSmtp;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		testSmtp = new GreenMail(ServerSetupTest.SMTP);
		testSmtp.start();

		mailSender.setPort(3025);
		mailSender.setHost("localhost");

		UsersModelBuilder usersModelBuilder = new UsersModelBuilder(UsersValidate.class);
		userValidate = (UsersValidate) usersModelBuilder.getObject();
		UsersModelBuilder usersModelBuilder2 = new UsersModelBuilder(UsersDTO.class);
		usersDTO = (UsersDTO) usersModelBuilder2.getObject();
		ProductsBuilder productsBuilder = new ProductsBuilder();
		products = productsBuilder.getProductsSet();
	}

	@After
	public void cleanup(){
		testSmtp.stop();
	}	

	@Test
	public void shouldReturnWelcomeMessageDetails_whenMethodSendEmailInvokedProperly() throws MessagingException {
		WelcomeMessage welcomeMessage = new WelcomeMessage(userValidate);
		emailService.sendEmail(welcomeMessage);

		Message[] messages = testSmtp.getReceivedMessages();
		assertEquals(1, messages.length);
		assertEquals(welcomeMessage.getMessage().getSubject(), messages[0].getSubject());
		String body = GreenMailUtil.getBody(messages[0]).replaceAll("[\\r\\n]+", "");
		assertEquals(welcomeMessage.getMessage().getText().replaceAll("[\\r\\n]+", ""), body);
		assertEquals(userValidate.getEmail(), messages[0].getAllRecipients()[0].toString());
	}

	@Test
	public void shouldReturnOrderToAdmin_whenMethodSendEmailInvokedProperly() throws MessagingException, IOException {
		OrderToAdmin orderToAdmin = new OrderToAdmin(usersDTO, products);
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
		OrderToUser orderToUser = new OrderToUser(usersDTO, products);
		emailService.sendEmail(orderToUser);

		Message[] messages = testSmtp.getReceivedMessages();
		assertEquals(1, messages.length);
		assertEquals(orderToUser.getMessage().getSubject(), messages[0].getSubject());
		String body = GreenMailUtil.getBody(messages[0]).replaceAll("[\\r\\n]+", "");
		assertEquals(orderToUser.getMessage().getText().replaceAll("[\\r\\n]+", ""), body);
		assertEquals(usersDTO.getEmail(), messages[0].getAllRecipients()[0].toString());
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
		userValidate.setEmail(null);
		emailService.sendEmail(new WelcomeMessage(userValidate));
	}
}