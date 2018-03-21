package org.yerbashop.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.yerbashop.dao.ProductsDao;
import org.yerbashop.model.Products;

@RunWith(MockitoJUnitRunner.class)
public class ProductsServiceTest {

	@Mock
    private ProductsDao productsDao;

    @InjectMocks
    private ProductsService productsService;
    
    List<Products> products;
    
    @Before
    public void setUp() {
    	
    }
    
    private List<Products> productsList(){
    	products = new ArrayList<Products>();
    	
        Products p1 = new Products();
        Products p2 = new Products();
        Products p3 = new Products();
    	
		p1.setName("Yerba Mate");
		p1.setCategory("classicYerba");
		
		p2.setName("Green Mate");
		p2.setCategory("flavouredYerba");

		p3.setName("Metal Gourd");
		p3.setCategory("gourdsAccesories");
		
		products.add(p1);
		products.add(p2);
		products.add(p3);
		
    	return products;
    }
    
    @Test
    public void shouldGiveList_whenMethodCalledCorrectly(){
    	when(productsDao.getAllProducts()).thenReturn(productsList());
        assertEquals(productsService.getProductList(), products);
        assertEquals(products.get(0).getName(),"Yerba Mate");
    }
    
   @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerException_whenListWasNotInitialized() {
	   	when(productsDao.getAllProducts()).thenReturn(products);
	   	Products product = productsService.getProductList().get(0);
    	assertNull(product);
    }

}
