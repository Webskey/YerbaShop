package org.yerbashop.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.yerbashop.dao.ProductsDao;
import org.yerbashop.dummybuilders.ProductsBuilder;
import org.yerbashop.model.Products;

@RunWith(MockitoJUnitRunner.class)
public class ProductsServiceTest {

	@Mock
	private ProductsDao productsDao;

	@InjectMocks
	private ProductsService productsService;

	private List<Products> productsList;

	@Before
	public void setUp() {
		ProductsBuilder productsBuilder = new ProductsBuilder();
		productsList = productsBuilder.getProductsList();
	}	

	@Test
	public void shouldGiveList_whenMethodCalledCorrectly(){
		when(productsDao.getAllProducts()).thenReturn(productsList);
		assertEquals(productsService.getProductList(), productsList);
		assertEquals(productsList.get(0).getName(),"Yerba Mate");
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNullPointerException_whenListWasNotInitialized() {
		List<Products> productsLists = null;
		when(productsDao.getAllProducts()).thenReturn(productsLists);
		assertNull(productsService.getProductList().get(0));
	}
}