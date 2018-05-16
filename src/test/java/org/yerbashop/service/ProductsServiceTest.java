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
import org.yerbashop.dao.GetAllDao;
import org.yerbashop.dummybuilders.ProductsBuilder;
import org.yerbashop.model.Products;

@RunWith(MockitoJUnitRunner.class)
public class ProductsServiceTest {

	@Mock
	private GetAllDao<Products> productsDao;

	@InjectMocks
	private ProductsService productsService;

	private List<Products> productsList;

	@Before
	public void setUp() {
		ProductsBuilder productsBuilder = new ProductsBuilder();
		productsList = productsBuilder.getProductsList();
	}	

	@Test
	public void shouldReturnProdcutsList_whenMethodCalledCorrectly(){
		//given
		when(productsDao.getAll()).thenReturn(productsList);
		//when
		List<Products> serviceProductsList = productsService.getProductList();
		//then
		assertEquals(serviceProductsList, productsList);
		assertEquals(serviceProductsList.get(1).getPrice(), productsList.get(1).getPrice());
		assertEquals(productsList.get(0).getName(), "Yerba Mate");
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNullPointerException_whenListWasNotInitialized() {
		//given
		List<Products> productsLists = null;
		when(productsDao.getAll()).thenReturn(productsLists);
		//when
		List<Products> serviceProductsList = productsService.getProductList();
		//then
		assertNull(serviceProductsList.get(3));
	}
}