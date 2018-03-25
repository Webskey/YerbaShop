package org.yerbashop.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.yerbashop.mailMessages.OrderToAdmin;
import org.yerbashop.mailMessages.OrderToUser;
import org.yerbashop.model.Products;
import org.yerbashop.model.Users;
import org.yerbashop.service.EmailService;
import org.yerbashop.service.ProductsService;
import org.yerbashop.service.SaveOrdersService;
import org.yerbashop.service.UserProfileService;

@Controller
public class ProductsController{

	@Autowired
	ProductsService productsService;

	@Autowired
	EmailService emailService;

	@Autowired
	UserProfileService userProfileService;

	@Autowired
	SaveOrdersService saveOrdersService;

	ExecutorService executor = Executors.newCachedThreadPool();

	Set<Products> orderList = new HashSet<Products>();

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ModelAndView products(ModelMap model,HttpServletRequest req, HttpServletResponse resp) {
		if(req.getParameter("cat") != null)
			model.addAttribute("products", productsService.getProductList().stream().filter(s -> s.getCategory().contains(req.getParameter("cat"))).collect(Collectors.toList()));
		else {
			model.addAttribute("products", productsService.getProductList());
		}
		return new ModelAndView("products", "command", new Products());
	}

	@RequestMapping(value = "/basket", method = RequestMethod.GET)
	public ModelAndView basket(Model model) {
		model.addAttribute("orderList",orderList);
		model.addAttribute("priceSum",orderList.stream().map(p->p.getPrice()).reduce(0, Integer::sum));
		return new ModelAndView("basket", "command", new Products());
	}

	@RequestMapping(value = "/add-to-basket", method = RequestMethod.POST)
	public String addToBasket(@ModelAttribute("Products")Products product, ModelMap model) {
		if(!orderList.contains(product))
			orderList.add(product);
		else {
			System.out.println("zawiera!");
		}
		model.addAttribute("productAdded", product);
		return "add-to-basket";
	}

	@RequestMapping(value = "/remove-from-basket", method = RequestMethod.POST)
	public String removeFromBasket(@ModelAttribute("Products")Products product) {
		orderList.remove(orderList.stream().filter(s->s.getName().equals(product.getName())).findAny().get());
		return "redirect:basket";
	}

	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public String order(ModelMap model, Principal principal) {

		Users user = userProfileService.getUser(principal.getName());

		executor.execute(()->{
			emailService.sendEmail(new OrderToAdmin(user,orderList));
			System.out.println("Mail sent to admin");
			emailService.sendEmail(new OrderToUser(user,orderList));
			System.out.println("Mail sent to user");
			saveOrdersService.saveOrders(user, orderList);
			System.out.println("Order saved");
			orderList.removeAll(orderList);
		});


		/*
		executor.execute(()->{
			emailService.sendEmail(new OrderToUser(user,orderList));
    		System.out.println("Mail sent to user");
    	});

		executor.execute(()->{
			saveOrdersService.saveOrders(user, orderList);
    		System.out.println("Order saved");
    	});
		 */
		return "order";
	}
}