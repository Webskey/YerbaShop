package org.yerbashop;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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


	@Override  
	protected void configure(HttpSecurity http) throws Exception {  

		http.authorizeRequests()  
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/order/**").access("hasRole('ROLE_USER')")
		.antMatchers("/profile/**").access("hasRole('ROLE_USER')")
		.antMatchers("/basket/**").access("hasRole('ROLE_USER')")
		.and().formLogin().loginPage("/login")
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