package org.yerbashop.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yerbashop.mailMessages.WelcomeMessage;
import org.yerbashop.model.UsersValidate;
import org.yerbashop.service.EmailService;
import org.yerbashop.service.UserRegisterService;

/**
 * <h1>Register new user controller class.</h1>
 * 
 * @author  <a href="https://github.com/Webskey">Webskey</a>
 * @since   2018-03-25
 */

@Controller
public class RegisterController {

	@Autowired
	EmailService emailService;

	@Autowired
	UserRegisterService userRegisterService;

	ExecutorService executor = Executors.newCachedThreadPool();

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {
		model.addAttribute("user", new UsersValidate());
		return "register";
	}
	/**
	 * This POST method validates user details, if they meet requirements, saves new user into database and sends him a welcome email.
	 */
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public String reg(@Validated @ModelAttribute("user") UsersValidate user, BindingResult bindingResult,Model model) {

		if (bindingResult.hasErrors()) {
			return "register";
		}else {
			try {
				userRegisterService.register(user);
			}catch(ConstraintViolationException e) {
				bindingResult.rejectValue("username","userAlreadyExist","User with that username already exists.");
				return "register";
			}

			executor.execute(()->{
				emailService.sendEmail(new WelcomeMessage(user));
				System.out.println("Mail sent");
			});

			return "reg";
		}
	}
}

