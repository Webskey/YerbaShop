package org.yerbashop.controller;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;

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
  public void shouldAccesAdminPage_whenHasAdminRole() throws Exception {
      MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/admin").with(user("admin").password("pass").roles("USER","ADMIN"));
      this.mockMvc.perform(builder)
      		.andExpect(status().isOk())
      		.andExpect(authenticated().withRoles("USER","ADMIN").withUsername("admin"))
      		.andExpect(MockMvcResultMatchers.model().attribute("siemka","admin"));
   }
  
  @Test
  public void shouldDenyAccesOnAdmisPage_whenWithoutAdminRole() throws Exception {
      MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/admin").with(user("user").password("pass").roles("USER"));
      this.mockMvc.perform(builder)
      		.andExpect(status().isForbidden())
      		.andExpect(authenticated().withRoles("USER").withUsername("user"))
      		.andExpect(forwardedUrl("/403"));
   }

  @Test
  public void shouldLoginWithAdminRole_whenLoggedAsAdmin() throws Exception {
      this.mockMvc
      	.perform(formLogin().user("admin").password("boss"))
      	.andExpect(status().isFound())
      	.andDo(print())
      	.andExpect(authenticated().withRoles("USER","ADMIN").withUsername("admin"));
   }
  
  @Test
  public void shouldLoginWithUserRole_whenLoggedAsUser() throws Exception {
      this.mockMvc
      	.perform(formLogin().user("username").password("password"))
      	.andExpect(status().isFound())
      	.andExpect(authenticated().withRoles("USER").withUsername("username"));
   }
  
  @Test
  public void shouldFailLogin_whenWrongPassword() throws Exception {
      this.mockMvc
      	.perform(formLogin().user("admin").password("bosss"))
      	.andExpect(unauthenticated());
   }
  @Test
  public void shouldFailLogin_whenWrongUsername() throws Exception {
      this.mockMvc
      	.perform(formLogin().user("adminW").password("boss"))
      	.andExpect(unauthenticated());
   }
  @Test
  public void shouldRedirectToLogout_whenPerformingLogout() throws Exception {
	  this.mockMvc
    	.perform(formLogin().user("user").password("pass"));
	  this.mockMvc
  		.perform(logout())
  		.andExpect(redirectedUrl("/login?logout"))
  		.andExpect(status().isFound());
   }
}
