package org.yerbashop.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.yerbashop.dummybuilders.ProductsBuilder;
import org.yerbashop.dummybuilders.UsersBuilder;
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

	private Users user;
	private Set<Products> products;

	@Before
	public void before() {
		UsersBuilder usersBuilder = new UsersBuilder();
		user = usersBuilder.getUser();

		ProductsBuilder productsBuilder = new ProductsBuilder();
		products = productsBuilder.getProductsSet();
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