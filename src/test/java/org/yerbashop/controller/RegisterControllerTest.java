package org.yerbashop.controller;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.concurrent.ExecutorService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.yerbashop.AppConfig;
import org.yerbashop.dummybuilders.UsersModelBuilder;
import org.yerbashop.model.UsersValidate;
import org.yerbashop.service.EmailService;
import org.yerbashop.service.UserRegisterService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class RegisterControllerTest {

	private MockMvc mockMvc;
	private UsersValidate user;

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
		
		UsersModelBuilder usersModelBuilder = new UsersModelBuilder(UsersValidate.class);
		user = (UsersValidate) usersModelBuilder.getObject();
	}

	@Test
	public void shouldReturnInstanceOfUsersValidateClassAttribute_whenRegisterFormCalled() throws Exception{
		//when
		this.mockMvc.perform(get("/register"))
		//then
		.andExpect(status().isOk())
		.andExpect(view().name("register"))
		.andExpect(model().attribute("user", instanceOf(UsersValidate.class)));
	}

	@Test
	public void shouldFailValidation_whenUsernameIncorrect() throws Exception{
		//when
		this.mockMvc.perform(post("/reg").param("username", "F").flashAttr("user", user))
		//then
		.andExpect(model().attributeHasFieldErrors("user", "username"))
		.andExpect(model().attribute("user", hasProperty("password", is("password"))))
		.andExpect(view().name("register"))
		.andExpect(status().isOk());
	}

	@Test
	public void shouldFailValidation_whenPasswordIncorrect() throws Exception{
		//when
		this.mockMvc.perform(post("/reg").param("password", "F").flashAttr("user", user))
		//then
		.andExpect(model().attributeHasFieldErrors("user", "password"))
		.andExpect(model().attribute("user", hasProperty("username", is("username"))))
		.andExpect(view().name("register"))
		.andExpect(status().isOk());
	}

	@Test
	public void shouldFailValidation_whenEmailIncorrect() throws Exception{
		//when
		this.mockMvc.perform(post("/reg").param("email", "F").flashAttr("user", user))
		//then
		.andExpect(model().attributeHasFieldErrors("user", "email"))
		.andExpect(model().attribute("user", hasProperty("password", is("password"))))
		.andExpect(view().name("register"))
		.andExpect(status().isOk());
	}

	@Test
	public void shouldPassValidation_whenUserValidated() throws Exception{
		//given
		doAnswer((invocation)->{
			((Runnable) invocation.getArguments()[0]).run();
			return null;
		}).when(executor).execute(any(Runnable.class)); 

		doNothing().when(emailService).sendEmail(any());
		doNothing().when(userRegisterService).register(any());
		//when
		this.mockMvc.perform(post("/reg").flashAttr("user", user))
		//then
		.andExpect(model().attribute("user", hasProperty("password", is("password"))))
		.andExpect(model().attributeHasNoErrors("user"))
		.andExpect(view().name("reg"))
		.andExpect(status().isOk());

		verify(emailService, times(1)).sendEmail(any());
		verify(userRegisterService, times(1)).register(user);
	}

	@Test
	public void shouldRejectValidationAndFailRegistration_whenUsernameAlreadyExists()throws Exception{
		//given
		doThrow(org.hibernate.exception.ConstraintViolationException.class).when(userRegisterService).register(any());
		//when
		this.mockMvc.perform(post("/reg").flashAttr("user", user))
		//then
		.andExpect(model().attributeHasFieldErrors("user", "username"))
		.andExpect(model().attribute("user", hasProperty("password", is("password"))))
		.andExpect(view().name("register"))
		.andExpect(status().isOk());
	}
}