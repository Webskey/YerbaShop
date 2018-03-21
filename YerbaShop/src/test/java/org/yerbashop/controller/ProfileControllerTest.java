package org.yerbashop.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;

import org.yerbashop.AppConfig;
import org.yerbashop.model.Orders;
import org.yerbashop.model.Users;
import org.yerbashop.service.UserProfileService;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class ProfileControllerTest {
    
    private MockMvc mockMvc;

    @Autowired
	private WebApplicationContext wac;
    
    @Autowired
    private Filter springSecurityFilterChain;
    
    @Mock
    private UserProfileService userProfileService;
      
    @Mock
    private Principal principal;
    
    @InjectMocks
    private ProfileController profileController;
       
    @Before
    public void setup() {
    	MockitoAnnotations.initMocks(this);
    }
    
    private Users userTest() {
    	Users user = new Users();
        user.setUsername("username");
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setEmail("email");
        user.setPhoneNr("phoneNr");
        
        Orders order = new Orders();
        order.setId(12);
        order.setUsers(user);
        
        Set<Orders> orders = new HashSet<Orders>();
        orders.add(order);
        
        user.setOrders(orders);
        return user;
    }
    
    @Test
    public void shouldReturnProfileDetails_whenUserProvided() throws Exception {

    	InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
        this.mockMvc = MockMvcBuilders.standaloneSetup(profileController)
        		.setViewResolvers(viewResolver)
        		.build();
    	
    	String username = "username";
    	when(principal.getName()).thenReturn(username);
    	when(userProfileService.getUser(username)).thenReturn(userTest());
    	
        this.mockMvc.perform(get("/profile").principal(principal))
        		.andExpect(status().isOk())
        		.andExpect(model().attribute("username", "username"))
        		.andExpect(model().attribute("firstname", "firstname"))
        		.andExpect(model().attribute("lastname", "lastname"))
        		.andExpect(model().attribute("email", "email"))
        		.andExpect(model().attribute("phoneNr", "phoneNr"))
        		.andExpect(model().attribute("orderList", hasItem(instanceOf(Orders.class))))
        		.andExpect(view().name("profile"));
        		
        assertEquals(userTest().getUsername(),username);
     }
    
    @Test
    public void shouldReturnStatus403_whenUserNotLoggedIn() throws Exception {
    	
    	DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac)
      		  .apply(SecurityMockMvcConfigurers.springSecurity())
      	      .addFilters(springSecurityFilterChain);
        this.mockMvc = builder.build();
    	
        this.mockMvc.perform(get("/profile"))
        		.andExpect(status().isFound())
        		.andExpect(redirectedUrl("http://localhost/login"));
     }
}