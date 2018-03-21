package org.yerbashop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String homePage() {
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value="/logout", method=RequestMethod.GET)  
	public String logout(HttpServletRequest request, HttpServletResponse response) { 
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/";  
	}  

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accesssDenied() {
		return "403";
	}

	@RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
	public String admin(Model model) {
		//check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			//UserDetails userDetail = (UserDetails) auth.getPrincipal();
			//model.addObject("username", userDetail.getUsername());
			model.addAttribute("siemka", auth.getName());
		}
		return "admin";
	}
}