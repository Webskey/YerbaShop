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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.yerbashop.AppConfig;
import org.yerbashop.dummybuilders.ProductsBuilder;
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

		ProductsBuilder productsBuilder = new ProductsBuilder();
		products = productsBuilder.getProductsList();

		when(productsService.getProductList()).thenReturn(products);
		when(principal.getName()).thenReturn("username");

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/view/");
		viewResolver.setSuffix(".jsp");

		this.mockMvc = MockMvcBuilders.standaloneSetup(productsController)
				.setViewResolvers(viewResolver)
				.build();
	}

	@Test
	public void shouldPassAllProductsAsModelAttribute_whenCalledProductsWithoutParametr() throws Exception{
		//when
		this.mockMvc.perform(get("/products"))
		//then
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
		//when
		this.mockMvc.perform(get("/products?cat=Yerba"))
		//then
		.andExpect(status().isOk())
		.andExpect(view().name("products"))
		.andExpect(model().attribute("products", hasSize(2)))
		.andExpect(model().attribute("products", hasItem(hasProperty("name", is("Yerba Mate")))))
		.andExpect(model().attribute("products", hasItem(hasProperty("name", is("Green Mate")))))
		.andExpect(forwardedUrl("/WEB-INF/jsp/view/products.jsp"));
	}

	@Test
	public void shouldPassOnlyGourdsProductsAsModelAttribute_whenCalledProductsWithGourdsParametr() throws Exception{
		//when
		this.mockMvc.perform(get("/products?cat=gourds"))
		//then
		.andExpect(status().isOk())
		.andExpect(view().name("products"))
		.andExpect(model().attribute("products", hasSize(1)))
		.andExpect(model().attribute("products", hasItem(hasProperty("name", is("Metal Gourd")))))
		.andExpect(forwardedUrl("/WEB-INF/jsp/view/products.jsp"));
	}

	@Test
	public void shouldAddProductToBasket_whenAddToBasketPostMethodCalled() throws Exception{
		//when
		this.mockMvc.perform(post("/add-to-basket").flashAttr("Products", products.get(0)).flashAttr("orderList", orderList))
		//then
		.andExpect(status().isOk())
		.andExpect(view().name("add-to-basket"))
		.andExpect(model().attribute("productAdded", products.get(0)))
		.andExpect(forwardedUrl("/WEB-INF/jsp/view/add-to-basket.jsp"));

		verify(orderList, times(1)).add(products.get(0));
		assertEquals(orderList.size(),1);
	}

	@Test
	public void shouldRemoveProductFromBasket_whenRemoveFromBasketPostMethodCalled() throws Exception{
		//given
		orderList.addAll(products);
		//when
		this.mockMvc.perform(post("/remove-from-basket").flashAttr("Products", products.get(0)).flashAttr("orderList", orderList))
		//then
		.andExpect(redirectedUrl("basket"))
		.andExpect(view().name("redirect:basket"));

		verify(orderList, times(1)).remove(products.get(0));
		assertEquals(orderList.size(), 2);
	}

	@Test
	public void shouldReturnOrderListAsModelAttr_whenBasketMethodCalled() throws Exception{
		//given
		orderList.addAll(products);
		//when
		this.mockMvc.perform(get("/basket").flashAttr("orderList", orderList))
		//then
		.andExpect(status().isOk())
		.andExpect(view().name("basket"))
		.andExpect(model().attribute("orderList", hasSize(3)))
		.andExpect(model().attribute("orderList", hasItem(hasProperty("name", is("Yerba Mate")))))
		.andExpect(model().attribute("priceSum", 45))
		.andExpect(forwardedUrl("/WEB-INF/jsp/view/basket.jsp"));
	}

	@Test
	public void shouldReturnEmptyList_whenBasketMethodCalledWithEmptyProductsList() throws Exception{
		//when
		this.mockMvc.perform(get("/basket"))
		//then
		.andExpect(status().isOk())
		.andExpect(view().name("basket"))
		.andExpect(model().attribute("orderList", hasSize(0)))
		.andExpect(model().attribute("priceSum", 0))
		.andExpect(forwardedUrl("/WEB-INF/jsp/view/basket.jsp"));
	}

	@Test
	public void shouldCallOrderServiceAndClearBasketList_whenOrderMade() throws Exception {
		//given
		orderList.addAll(products);
		//when
		this.mockMvc.perform(post("/order").principal(principal).flashAttr("orderList", orderList))
		//then
		.andExpect(status().isOk())
		.andExpect(view().name("order"))
		.andExpect(forwardedUrl("/WEB-INF/jsp/view/order.jsp"));

		verify(takeOrderService).setOrder("username", orderList);
		verify(takeOrderService).start();
		assertEquals(orderList.size(), 0);
	}
} 