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
import org.yerbashop.model.UsersDTO;
import org.yerbashop.service.EmailService;
import org.yerbashop.service.UserRegisterService;

@Controller
public class RegisterController {
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	UserRegisterService userRegisterService;

	ExecutorService executor = Executors.newCachedThreadPool();
	
		@RequestMapping(value = "/register", method = RequestMethod.GET)
		public String register(Model model) {
			model.addAttribute("user", new UsersDTO());
			return "register";
		}
	   
	   @RequestMapping(value = "/reg", method = RequestMethod.POST)
	   public String reg(@Validated @ModelAttribute("user") UsersDTO user, BindingResult bindingResult,Model model) {
		   
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

	   