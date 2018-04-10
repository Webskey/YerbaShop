package org.yerbashop.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.yerbashop.dao.SaveDao;
import org.yerbashop.dummybuilders.ProductsBuilder;
import org.yerbashop.dummybuilders.UsersBuilder;
import org.yerbashop.model.Orders;
import org.yerbashop.model.Products;
import org.yerbashop.model.Users;

@RunWith(MockitoJUnitRunner.class)
public class SaveOrdersServiceTest {

	@Mock
	private SaveDao saveDao;

	@InjectMocks
	private SaveOrdersService saveOrdersService;

	private Users user;

	private Set<Products> products;

	@Before
	public void setUp() {
		
		UsersBuilder usersBuilder = new UsersBuilder();
		user = usersBuilder.getUser();
		
		ProductsBuilder productsBuilder = new ProductsBuilder();
		products = productsBuilder.getProductsSet();
	}

	@Test
	public void shouldSaveOrderList_whenAllGood() {
		doNothing().when(saveDao).save(any());

		Orders orders = new Orders();
		orders.setUsers(user);
		orders.setProductsList(products);

		saveOrdersService.saveOrders(user, products);
		
		verify(saveDao).save(orders);
	}
}
