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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.yerbashop.mailMessages.OrderToAdmin;
import org.yerbashop.mailMessages.OrderToUser;
import org.yerbashop.model.Products;
import org.yerbashop.model.Users;
import org.yerbashop.service.EmailService;
import org.yerbashop.service.ProductsService;
import org.yerbashop.service.SaveOrdersService;
import org.yerbashop.service.UserProfileService;

/**
 * <h1>Products and orders controller class.</h1>
 * This provides products from database and handles taking order from user. 
 * 
 * @author  <a href="https://github.com/Webskey">Webskey</a>
 * @since   2018-03-25
 */

@Controller
<<<<<<< HEAD
@SessionAttributes("orderList")
=======
@SuppressWarnings("unchecked")
>>>>>>> branch 'PC' of https://github.com/Webskey/YerbaShop
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

<<<<<<< HEAD
	@ModelAttribute("orderList")
	private Set<Products> orderList(){
		Set<Products> orderList = new HashSet<Products>();
		return orderList;
	}
=======
	Set<Products> orderList;
>>>>>>> branch 'PC' of https://github.com/Webskey/YerbaShop

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
<<<<<<< HEAD
	public ModelAndView basket(@ModelAttribute("orderList") HashSet<Products> orderList, Model model, HttpServletRequest req) {
		model.addAttribute("orderList",orderList);
		model.addAttribute("priceSum",orderList.stream().map(p->p.getPrice()).reduce(0, Integer::sum));
=======
	public ModelAndView basket(Model model, HttpServletRequest req) {
		try {
			model.addAttribute("orderList",orderList);
			model.addAttribute("priceSum",orderList.stream().map(p->p.getPrice()).reduce(0, Integer::sum));
		}catch(NullPointerException ex) {
			req.getSession().setAttribute("basket", new HashSet<Products>());
			orderList = (HashSet<Products>) req.getSession().getAttribute("basket");
		}
>>>>>>> branch 'PC' of https://github.com/Webskey/YerbaShop
		return new ModelAndView("basket", "command", new Products());
	}

	@RequestMapping(value = "/add-to-basket", method = RequestMethod.POST)
<<<<<<< HEAD
	public String addToBasket(@ModelAttribute("orderList") HashSet<Products> orderList, @ModelAttribute("Products")Products product, ModelMap model, HttpServletRequest req) {
		if(!orderList.contains(product))
			orderList.add(product);
=======
	public String addToBasket(@ModelAttribute("Products")Products product, ModelMap model, HttpServletRequest req) {
		try {
			if(!orderList.contains(product))
				orderList.add(product);
		}catch(NullPointerException ex) {
			req.getSession().setAttribute("basket", new HashSet<Products>());
			orderList = (Set<Products>) req.getSession().getAttribute("basket");
		}
>>>>>>> branch 'PC' of https://github.com/Webskey/YerbaShop
		model.addAttribute("productAdded", product);
		return "add-to-basket";
	}

	@RequestMapping(value = "/remove-from-basket", method = RequestMethod.POST)
	public String removeFromBasket(@ModelAttribute("orderList") HashSet<Products> orderList, @ModelAttribute("Products")Products product) {
		orderList.remove(orderList.stream().filter(s->s.getName().equals(product.getName())).findAny().get());
		return "redirect:basket";
	}

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public String order(@ModelAttribute("orderList") HashSet<Products> orderList, ModelMap model, Principal principal) {
		Users user = userProfileService.getUser(principal.getName());

		executor.execute(()->{
			emailService.sendEmail(new OrderToAdmin(user,orderList));
			emailService.sendEmail(new OrderToUser(user,orderList));
			saveOrdersService.saveOrders(user, orderList);
			orderList.removeAll(orderList);
		});

		return "order";
	}
}