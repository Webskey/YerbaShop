package org.yerbashop;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <h1>Spring Security configuration class.</h1>
 *
 * @author  <a href="https://github.com/Webskey">Webskey</a>
 * @since   2018-03-25
 */

@EnableWebSecurity  
@ComponentScan("org.yerbashop")  
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter { 

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	/**
	 *This method sets permissions, adress to login, logout and acces denied pages. 
	 */
	@Override  
	protected void configure(HttpSecurity http) throws Exception {  

		http.authorizeRequests()  
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/order/**").access("hasRole('ROLE_USER')")
		.antMatchers("/profile/**").access("hasRole('ROLE_USER')")
		.antMatchers("/remove-from-basket/**").access("hasRole('ROLE_USER')")
		.antMatchers("/add-to-basket/**").access("hasRole('ROLE_USER')")
		.antMatchers("/basket/**").access("hasRole('ROLE_USER')")
		.and().formLogin().loginPage("/login")//.defaultSuccessUrl("/403", true)
		.and()  
		.httpBasic()  
		.and()  
		.logout() 
		.and()
		.exceptionHandling().accessDeniedPage("/403")
		.and()
		.csrf();
	}  
}  