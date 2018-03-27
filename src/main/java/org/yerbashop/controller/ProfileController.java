package org.yerbashop.controller;

import java.security.Principal;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.yerbashop.model.Users;
import org.yerbashop.service.UserProfileService;

/**
 * <h1>User profile information controller class.</h1>
 * 
 * @author  <a href="https://github.com/Webskey">Webskey</a>
 * @since   2018-03-25
 */

@Controller
public class ProfileController {

	@Autowired
	private UserProfileService userProfileService;

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView profile (Principal principal) {

		Users user = userProfileService.getUser(principal.getName());

		ModelAndView model = new ModelAndView();

		model.addObject("username", user.getUsername());
		model.addObject("firstname", user.getFirstname());
		model.addObject("lastname", user.getLastname());
		model.addObject("email", user.getEmail());
		model.addObject("adress", user.getAdress());
		model.addObject("phoneNr", user.getPhoneNr());
		model.addObject("orderList", user.getOrders().stream().sorted((a, b) -> Integer.compare(a.getId(), b.getId())).collect(Collectors.toList()));

		model.setViewName("profile");

		return model;
	}
}

