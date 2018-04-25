package org.yerbashop.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.yerbashop.model.UsersDTO;
import org.yerbashop.service.UserProfileService;

/**
 * <h1>User profile information controller class.</h1>
 * 
 * @author  <a href="https://github.com/Webskey">Webskey</a>
 * @since   2018-03-25
 */
@Transactional
@Controller
public class ProfileController {

	@Autowired
	private UserProfileService userProfileService;

	@RequestMapping(value = "/profile/{flag}", method = RequestMethod.GET)
	public ModelAndView profile (Principal principal, @PathVariable("flag") String flag) {

		UsersDTO user = userProfileService.getUser(principal.getName());

		ModelAndView model = new ModelAndView();

		if(flag.equals("info"))
			model.addObject("flag", false);
		else
			model.addObject("flag", true);

		model.addObject("user", user);
		model.setViewName("profile");

		return model;
	}

	@RequestMapping(value = "/profile/changed", method = RequestMethod.POST)
	public String update (@Validated @ModelAttribute("user") UsersDTO user, BindingResult bindingResult, Model model) {

		if(bindingResult.hasErrors())
			return "profile";
		userProfileService.update(user);
		return "redirect:/";		
	}
}

