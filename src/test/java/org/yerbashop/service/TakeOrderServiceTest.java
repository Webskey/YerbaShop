package org.yerbashop.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.yerbashop.model.Products;
import org.yerbashop.model.Users;

@RunWith(MockitoJUnitRunner.class)
public class TakeOrderServiceTest {

	@Mock
	private EmailService emailService;

	@Mock
	private SaveOrdersService saveOrdersService;

	@Mock
	private UserProfileService userProfileService;

	@InjectMocks
	private TakeOrderService takeOrderService;

	private Users user = new Users();
	private Set<Products> products;

	@Before
	public void before() {
		user = new Users();
		products = productsList();
	}

	private Set<Products> productsList(){
		products = new HashSet<Products>();

		Products p1 = new Products();
		Products p2 = new Products();
		Products p3 = new Products();

		p1.setName("Yerba Mate");
		p1.setCategory("classicYerba");
		p1.setPrice(20);

		p2.setName("Green Mate");
		p2.setCategory("flavouredYerba");
		p2.setPrice(10);

		p3.setName("Metal Gourd");
		p3.setCategory("gourdsAccesories");
		p3.setPrice(15);

		products.add(p1);
		products.add(p2);
		products.add(p3);

		return products;
	}

	@Test
	public void shouldCallOtherServices_whenMethodRunInvoked() {
		when(userProfileService.getUser("username")).thenReturn(user);

		takeOrderService.setOrder("username", products);
		takeOrderService.run();

		verify(emailService,times(2)).sendEmail(any());
		verify(userProfileService).getUser("username");
		verify(saveOrdersService).saveOrders(user, products);
	}
}