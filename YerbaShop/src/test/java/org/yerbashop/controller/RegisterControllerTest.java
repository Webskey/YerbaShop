package org.yerbashop.controller;

import java.util.concurrent.ExecutorService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.yerbashop.AppConfig;
import org.yerbashop.model.UsersDTO;
import org.yerbashop.service.EmailService;
import org.yerbashop.service.UserRegisterService;

import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;



@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class RegisterControllerTest {
    
    private MockMvc mockMvc;
    private UsersDTO user;
    
    @Mock
    private ExecutorService executor;
    
    @Mock
    private UserRegisterService userRegisterService;
    
    @Mock
    private EmailService emailService;
  
    @InjectMocks
    private RegisterController registerController;
       
    @Before
    public void setup() {
    	
    	MockitoAnnotations.initMocks(this);
    	
    	InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
        
        this.mockMvc = MockMvcBuilders.standaloneSetup(registerController)
        		.setViewResolvers(viewResolver)
        		.build();
        user = new UsersDTO();
    }
    
    private UsersDTO userTest() {
        user.setUsername("usernames");
        user.setPassword("password");
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setEmail("jolo_92@wp.pl");
        user.setPhoneNr("phoneNr");
        return user;
    }
    
    @Test
    public void shouldBeInstanceOfUserDTO_whenCalledModelInRegister() throws Exception{
    	
    	this.mockMvc.perform(get("/register"))
    			  //.andDo(MockMvcResultHandlers.print())
    				.andExpect(status().isOk())
    				.andExpect(view().name("register"))
    				.andExpect(model().attribute("user", instanceOf(UsersDTO.class)));
    }
    
    @Test
    public void shouldFailValidation_whenUsernameWrong() throws Exception{
    	
    	this.mockMvc.perform(post("/reg").param("username","F").flashAttr("user", userTest()))
    			  //.andDo(MockMvcResultHandlers.print())
    				.andExpect(model().attributeHasFieldErrors("user", "username"))
    				.andExpect(model().attribute("user", hasProperty("password", is("password"))))
    				.andExpect(view().name("register"))
    				.andExpect(status().isOk());
    }
    
    @Test
    public void shouldFailValidation_whenPasswordeWrong() throws Exception{
    	
    	this.mockMvc.perform(post("/reg").param("password","F").flashAttr("user", userTest()))
    			  //.andDo(MockMvcResultHandlers.print())
    				.andExpect(model().attributeHasFieldErrors("user", "password"))
    				.andExpect(model().attribute("user", hasProperty("username", is("usernames"))))
    				.andExpect(view().name("register"))
    				.andExpect(status().isOk());
    }
    
    @Test
    public void shouldFailValidation_whenEmailWrong() throws Exception{
    	
    	this.mockMvc.perform(post("/reg").param("email","F").flashAttr("user", userTest()))
    			  //.andDo(MockMvcResultHandlers.print())
    				.andExpect(model().attributeHasFieldErrors("user", "email"))
    				.andExpect(model().attribute("user", hasProperty("password", is("password"))))
    				.andExpect(view().name("register"))
    				.andExpect(status().isOk());
    }
    
    @Test
    public void shouldPassValidation_whenAllGood() throws Exception{
    	doAnswer((invocation)->{
            ((Runnable) invocation.getArguments()[0]).run();
            return null;
    	}).when(executor).execute(any(Runnable.class)); 
    	
    	doNothing().when(emailService).sendEmail(any());
    	doNothing().when(userRegisterService).register(any());
    	
    	this.mockMvc.perform(post("/reg").flashAttr("user", userTest()))
    				.andExpect(model().attribute("user", hasProperty("password", is("password"))))
    				.andExpect(model().attributeHasNoErrors("user"))
    				.andExpect(view().name("reg"))
    				.andExpect(status().isOk());
    	
    	verify(emailService, times(1)).sendEmail(any());
    	verify(userRegisterService, times(1)).register(userTest());
    }
    
    @Test
    public void shouldThrowUnsupportedOperationException_whenUserAlreadyExists()throws Exception{
    	doThrow(org.hibernate.exception.ConstraintViolationException.class).when(userRegisterService).register(any());
    	
    	this.mockMvc.perform(post("/reg").flashAttr("user", userTest()))
    			  //.andDo(MockMvcResultHandlers.print())
    				.andExpect(model().attributeHasFieldErrors("user", "username"))
    				.andExpect(model().attribute("user", hasProperty("password", is("password"))))
    				.andExpect(view().name("register"))
    				.andExpect(status().isOk());
    }
}