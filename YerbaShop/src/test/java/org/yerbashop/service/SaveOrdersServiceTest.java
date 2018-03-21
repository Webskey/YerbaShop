package org.yerbashop.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.yerbashop.dao.SaveDao;
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
    	user = new Users();
    	user.setUsername("username");
    	user.setPassword("password");
    	user.setFirstname("firstname");
    	user.setLastname("lastname");
    	user.setEmail("email@email.com");
    	user.setAdress("adress");
    	user.setPhoneNr("phoneNr");
    	
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
    }
    
	@Test
	public void shouldSaveOrderList_whenAllGood() {
		doNothing().when(saveDao).save(any());
		
		Orders orders = new Orders();
		orders.setUsers(user);
		orders.setProductsList(products);
		
		saveOrdersService.saveOrders(user, products);
		assertEquals(orders.getUsers().getFirstname(),saveOrdersService.getOrder().getUsers().getFirstname());
		verify(saveDao).save(saveOrdersService.getOrder());
	}
}
