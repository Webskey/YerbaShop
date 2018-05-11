package org.yerbashop.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.yerbashop.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class LoginControllerTest {

	@Autowired
	private Filter springSecurityFilterChain;

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.addFilters(springSecurityFilterChain);
		this.mockMvc = builder.build();
	}

	@Test
	public void shouldAccesAdminPage_whenHaveAdminRole() throws Exception {
		//given
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/admin").with(user("admin").password("pass").roles("USER","ADMIN"));
		//when
		this.mockMvc.perform(builder)
		//then
		.andExpect(status().isOk())
		.andExpect(authenticated().withRoles("USER","ADMIN").withUsername("admin"))
		.andExpect(MockMvcResultMatchers.model().attribute("siemka","admin"));
	}

	@Test
	public void shouldDenyAccesOnAdminPage_whenHaveNotAdminRole() throws Exception {
		//given
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/admin").with(user("user").password("pass").roles("USER"));
		//when
		this.mockMvc.perform(builder)
		//then
		.andExpect(status().isForbidden())
		.andExpect(authenticated().withRoles("USER").withUsername("user"))
		.andExpect(forwardedUrl("/403"));
	}

	@Test
	public void shouldLoginWithAdminRole_whenLoggedAsAdmin() throws Exception {
		//given
		String username = "admin";
		String password = "boss";
		//when
		this.mockMvc
		.perform(formLogin().user(username).password(password))
		//then
		.andExpect(status().isFound())
		.andExpect(authenticated().withRoles("USER","ADMIN").withUsername(username));
	}

	@Test
	public void shouldLoginWithUserRole_whenLoggedAsUser() throws Exception {
		//given
		String username = "username";
		String password = "password";
		//when
		this.mockMvc
		//then
		.perform(formLogin().user(username).password(password))
		.andExpect(status().isFound())
		.andExpect(authenticated().withRoles("USER").withUsername(username));
	}

	@Test
	public void shouldFailLogin_whenWrongPassword() throws Exception {
		//given
		String username = "admin";
		String password = "bosss";
		//when
		this.mockMvc
		.perform(formLogin().user(username).password(password))
		//then
		.andExpect(unauthenticated());
	}

	@Test
	public void shouldFailLogin_whenWrongUsername() throws Exception {
		//given
		String username = "addmin";
		String password = "boss";
		//when
		this.mockMvc
		.perform(formLogin().user(username).password(password))
		//then
		.andExpect(unauthenticated());
	}

	@Test
	public void shouldRedirectToLogoutPage_whenLoggedOut() throws Exception {
		//given
		String username = "username";
		String password = "password";
		//when
		this.mockMvc
		.perform(formLogin().user(username).password(password));
		this.mockMvc
		.perform(logout())
		//then
		.andExpect(redirectedUrl("/login?logout"))
		.andExpect(status().isFound());
	}
}
