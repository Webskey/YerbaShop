package org.yerbashop.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.yerbashop.AppConfig;
import org.yerbashop.model.Products;
import org.yerbashop.service.ProductsService;
import org.yerbashop.service.TakeOrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class ProductsControllerTest {

	private MockMvc mockMvc;

	@Mock
	private ProductsService productsService;

	@Mock
	private Principal principal;

	@Mock
	private TakeOrderService takeOrderService;

	@Spy
	private Set<Products> orderList = new HashSet<Products>();

	@InjectMocks
	private ProductsController productsController;


	private List<Products> products;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		products = productsList();
		when(productsService.getProductList()).thenReturn(products);
		when(principal.getName()).thenReturn("username");

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/view/");
		viewResolver.setSuffix(".jsp");

		this.mockMvc = MockMvcBuilders.standaloneSetup(productsController)
				.setViewResolvers(viewResolver)
				.build();
	}

	private List<Products> productsList(){
		products = new ArrayList<Products>();

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
	public void shouldPassAllProductsAsModelAttribute_whenCalledProductsWithoutParametr() throws Exception{

		this.mockMvc.perform(get("/products"))
		.andExpect(status().isOk())
		.andExpect(view().name("products"))
		.andExpect(model().attribute("products", is(products)))
		.andExpect(model().attribute("products", hasItem(hasProperty("name", is("Yerba Mate")))))
		.andExpect(model().attribute("products", hasItem(hasProperty("name", is("Green Mate")))))
		.andExpect(model().attribute("products", hasItem(hasProperty("name", is("Metal Gourd")))))
		.andExpect(forwardedUrl("/WEB-INF/jsp/view/products.jsp"));
	}

	@Test
	public void shouldPassOnlyYerbasProductsAsModelAttribute_whenCalledProductsWithYerbaParametr() throws Exception{

		this.mockMvc.perform(get("/products?cat=Yerba"))
		.andExpect(status().isOk())
		.andExpect(view().name("products"))
		.andExpect(model().attribute("products", hasSize(2)))
		.andExpect(model().attribute("products", hasItem(hasProperty("name", is("Yerba Mate")))))
		.andExpect(model().attribute("products", hasItem(hasProperty("name", is("Green Mate")))))
		.andExpect(forwardedUrl("/WEB-INF/jsp/view/products.jsp"));
	}

	@Test
	public void shouldPassOnlyGourdsProductsAsModelAttribute_whenCalledProductsWithGourdsParametr() throws Exception{

		this.mockMvc.perform(get("/products?cat=gourds"))
		.andExpect(status().isOk())
		.andExpect(view().name("products"))
		.andExpect(model().attribute("products", hasSize(1)))
		.andExpect(model().attribute("products", hasItem(hasProperty("name", is("Metal Gourd")))))
		.andExpect(forwardedUrl("/WEB-INF/jsp/view/products.jsp"));
	}

	@Test
	public void shouldAddProductToBasket_whenAddToBasketPostMethodCalled() throws Exception{

		this.mockMvc.perform(post("/add-to-basket").flashAttr("Products", products.get(0)).flashAttr("orderList", orderList))
		.andExpect(status().isOk())
		.andExpect(view().name("add-to-basket"))
		.andExpect(model().attribute("productAdded",products.get(0)))
		.andExpect(forwardedUrl("/WEB-INF/jsp/view/add-to-basket.jsp"));

		verify(orderList, times(1)).add(products.get(0));
		assertEquals(orderList.size(),1);
	}

	@Test
	public void shouldRemoveProductFromBasket_whenRemoveFromBasketPostMethodCalled() throws Exception{
		orderList.addAll(productsList());

		this.mockMvc.perform(post("/remove-from-basket").flashAttr("Products", products.get(0)).flashAttr("orderList", orderList))
		.andExpect(redirectedUrl("basket"))
		.andExpect(view().name("redirect:basket"));

		verify(orderList, times(1)).remove(products.get(0));
		assertEquals(orderList.size(),2);
	}

	@Test
	public void shouldReturnOrderListAsModelAttr_whenBasketMethodCalled() throws Exception{
		orderList.addAll(productsList());

		this.mockMvc.perform(get("/basket").flashAttr("orderList", orderList))
		.andExpect(status().isOk())
		.andExpect(view().name("basket"))
		.andExpect(model().attribute("orderList", hasSize(3)))
		.andExpect(model().attribute("orderList", hasItem(hasProperty("name", is("Yerba Mate")))))
		.andExpect(model().attribute("priceSum", 45))
		.andExpect(forwardedUrl("/WEB-INF/jsp/view/basket.jsp"));
	}

	@Test
	public void shouldReturnNothing_whenBasketMethodCalledWithEmptyList() throws Exception{

		this.mockMvc.perform(get("/basket"))
		.andExpect(status().isOk())
		.andExpect(view().name("basket"))
		.andExpect(model().attribute("orderList", hasSize(0)))
		.andExpect(model().attribute("priceSum", 0))
		.andExpect(forwardedUrl("/WEB-INF/jsp/view/basket.jsp"));
	}

	@Test
	public void shouldMakeOrderThings_whenOrdercalled() throws Exception {
		orderList.addAll(productsList());

		this.mockMvc.perform(post("/order").principal(principal).flashAttr("orderList", orderList))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk())
		.andExpect(view().name("order"))
		.andExpect(forwardedUrl("/WEB-INF/jsp/view/order.jsp"));

		verify(takeOrderService).setOrder("username", orderList);
		assertEquals(orderList.size(),0);
	}
} 