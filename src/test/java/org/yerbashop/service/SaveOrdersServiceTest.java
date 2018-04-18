package org.yerbashop.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.yerbashop.dao.LoadByIdDao;
import org.yerbashop.dao.SaveDao;
import org.yerbashop.dummybuilders.ProductsBuilder;
import org.yerbashop.dummybuilders.UsersModelBuilder;
import org.yerbashop.model.Orders;
import org.yerbashop.model.Products;
import org.yerbashop.model.Users;
import org.yerbashop.model.UsersDTO;

@RunWith(MockitoJUnitRunner.class)
public class SaveOrdersServiceTest {

	@Mock
	private SaveDao saveDao;
	
	@Mock
	private LoadByIdDao<Users> loadByIdDao;

	@InjectMocks
	private SaveOrdersService saveOrdersService;

	private UsersDTO userDTO;
	private Users user;
	
	private Set<Products> products;

	@Before
	public void setUp() {		
		UsersModelBuilder usersBuilder = new UsersModelBuilder(UsersDTO.class);
		userDTO = (UsersDTO) usersBuilder.getObject();
		UsersModelBuilder usersModelBuilder = new UsersModelBuilder(Users.class);
		user = (Users) usersModelBuilder.getObject();
		
		ProductsBuilder productsBuilder = new ProductsBuilder();
		products = productsBuilder.getProductsSet();
	}

	@Test
	public void shouldSaveOrderList_whenAllGood() {
		doNothing().when(saveDao).save(any());
		when(loadByIdDao.findUserById("username")).thenReturn(user);

		Orders orders = new Orders();
		orders.setProductsList(products);
		orders.setUsers(user);

		saveOrdersService.saveOrders(userDTO, products);
		
		verify(saveDao).save(orders);
	}
}
