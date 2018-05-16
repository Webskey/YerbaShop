package org.yerbashop.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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

	private static UsersValidate userValidate;
	private static UsersDTO usersDTO;
	private static Set<Products> products;
	private static UsersModelBuilder usersModelBuilder;
	private GreenMail testSmtp;

	@BeforeClass
	public static void beforeClass() {			
		usersModelBuilder = new UsersModelBuilder(UsersValidate.class);		
		UsersModelBuilder usersModelBuilder2 = new UsersModelBuilder(UsersDTO.class);
		usersDTO = (UsersDTO) usersModelBuilder2.getObject();
		ProductsBuilder productsBuilder = new ProductsBuilder();
		products = productsBuilder.getProductsSet();
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		testSmtp = new GreenMail(ServerSetupTest.SMTP);
		testSmtp.start();

		mailSender.setPort(3025);
		mailSender.setHost("localhost");		
	}

	@After
	public void cleanup(){
		testSmtp.stop();
	}	

	@Test
	public void shouldReturnWelcomeMessageDetails_whenWelcomeMessageSent() throws MessagingException {
		//given
		userValidate = (UsersValidate) usersModelBuilder.getObject();
		WelcomeMessage welcomeMessage = new WelcomeMessage(userValidate);
		//when
		emailService.sendEmail(welcomeMessage);
		//then
		Message[] messages = testSmtp.getReceivedMessages();
		assertEquals(1, messages.length);
		assertEquals(welcomeMessage.getMessage().getSubject(), messages[0].getSubject());
		String body = GreenMailUtil.getBody(messages[0]).replaceAll("[\\r\\n]+", "");
		assertEquals(welcomeMessage.getMessage().getText().replaceAll("[\\r\\n]+", ""), body);
		assertEquals(userValidate.getEmail(), messages[0].getAllRecipients()[0].toString());
	}

	@Test
	public void shouldReturnOrderToAdminMessageDetails_whenOrderToAdminMessageSent() throws MessagingException, IOException {
		//given
		OrderToAdmin orderToAdmin = new OrderToAdmin(usersDTO, products);
		//when
		emailService.sendEmail(orderToAdmin);
		//then
		Message[] messages = testSmtp.getReceivedMessages();
		assertEquals(1, messages.length);
		assertEquals(orderToAdmin.getMessage().getSubject(), messages[0].getSubject());
		String body = GreenMailUtil.getBody(messages[0]).replaceAll("[\\r\\n]+", "");
		assertEquals(orderToAdmin.getMessage().getText().replaceAll("[\\r\\n]+", ""), body);
		assertEquals("YerbaShop.project@gmail.com", messages[0].getAllRecipients()[0].toString());
	}

	@Test
	public void shouldReturnOrderToUserMessageDetails_whenOrderToUserMessageSent() throws MessagingException, IOException {
		//given
		OrderToUser orderToUser = new OrderToUser(usersDTO, products);
		//when
		emailService.sendEmail(orderToUser);
		//then
		Message[] messages = testSmtp.getReceivedMessages();
		assertEquals(1, messages.length);
		assertEquals(orderToUser.getMessage().getSubject(), messages[0].getSubject());
		String body = GreenMailUtil.getBody(messages[0]).replaceAll("[\\r\\n]+", "");
		assertEquals(orderToUser.getMessage().getText().replaceAll("[\\r\\n]+", ""), body);
		assertEquals(usersDTO.getEmail(), messages[0].getAllRecipients()[0].toString());
	}

	@Test
	public void shouldReturnCustomMessageDetails_whenCustomMessageSentThroughMailSender() throws MessagingException {
		//given
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("email@email.com");
		message.setSubject("subject");
		message.setText("text");
		//when
		mailSender.send(message);
		//then
		Message[] messages = testSmtp.getReceivedMessages();
		assertEquals(1, messages.length);
		assertEquals("subject", messages[0].getSubject());
		String body = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
		assertEquals("text", body);
		assertEquals("email@email.com", messages[0].getAllRecipients()[0].toString());
	}

	@Test(expected=java.lang.NullPointerException.class)
	public void shouldThrowNullPointerException_whenEmailServiceInvokedWithNullArgument() throws MessagingException {
		//when
		emailService.sendEmail(null);
		//then NullPointerException thrown
	}

	@Test(expected=java.lang.NullPointerException.class)
	public void shouldThrowNullPointerException_whenEmailFieldIsNull() throws MessagingException {
		//given
		userValidate.setEmail(null);
		//when
		emailService.sendEmail(new WelcomeMessage(userValidate));
		//then NullPointerException thrown
	}
}