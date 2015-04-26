package com.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.spring.bean.CreditCardBean;
import com.spring.bean.UserBean;
import com.spring.model.Account;
import com.spring.model.CreditCard;
import com.spring.model.MasterCard;
import com.spring.model.Strategy;
import com.spring.model.User;
import com.spring.model.Visa;
import com.spring.service.AccountService;
import com.spring.service.UserService;

@Controller
@SessionAttributes({"user", "account", "strategy", "registerError"})
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveUser(@ModelAttribute("command")@Valid UserBean userBean, ModelMap model) {
		boolean found = false;
		boolean validateForm = validateUser(userBean);
		User user = prepareModel(userBean);
		if(found || !validateForm){
			model.put("registerError", "Error, please re-enter your details");
			return new ModelAndView("redirect:/register.html");
		}
		else{
			userService.addUser(user);
			return new ModelAndView("redirect:/loginform.html");
		}
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView showProfile(ModelMap model) {
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		if(user==null)return new ModelAndView("redirect:/loginform.html");
		User u = userService.getUser(user.getUserId());

		for(Strategy strategy: u.getAccount().getStrategies()){
			if(strategy.getName().equals("RAGLAN_ROAD")){
				model.addAttribute("raglanroad", user.getAccount().getRaglanroad());
			}
			if(strategy.getName().equals("GINGER_MAC")){
				model.addAttribute("gingermc", user.getAccount().getGingermc());
			}
			if(strategy.getName().equals("LUCAYAN")){
				model.addAttribute("lucayan", user.getAccount().getLucayan());
			}
		}	

		return new ModelAndView("profile");
	}

	@RequestMapping(value="/users", method = RequestMethod.GET)
	public ModelAndView listUsers() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("users",  prepareListofBean(userService.userList()));
		return new ModelAndView("userList", model);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showUpdateBalance(ModelMap model) {
		Account acc = new Account();
		model.addAttribute("account", acc);
		return new ModelAndView("updateBalance");
	}

	@RequestMapping(value = "/updateBalance", method = RequestMethod.POST)
	public ModelAndView updateBalance(@ModelAttribute("account") Account acc, Map model) {
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		User userToUpdate = userService.getUser(user.getUserId());
		if(acc.getBalance()<=0)return new ModelAndView("redirect:/updateBalance.html");
		userToUpdate.getAccount().addToBalance(acc.getBalance());
		userService.updateBalance(userToUpdate);
		model.put("user", userToUpdate);
		return new ModelAndView("redirect:/profile.html");
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView addUser(@ModelAttribute("command")  UserBean userBean) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("users",  prepareListofBean(userService.userList()));
		return new ModelAndView("register", model);
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView welcome() {
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteUser(@ModelAttribute("command")  UserBean userBean, BindingResult result) {
		userService.deleteUser(prepareModel(userBean));
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", null);
		model.put("users",  prepareListofBean(userService.userList()));
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public ModelAndView showContactPage(){
		return new ModelAndView("contact");
	}
	
	@RequestMapping(value = "/creditcard", method = RequestMethod.GET)
	public ModelAndView showAddCreditCard(ModelMap model){
		model.addAttribute("creditCard", new CreditCardBean());
		return new ModelAndView("creditcard");
	}
	
	@RequestMapping(value = "/addCreditCard", method = RequestMethod.POST)
	public ModelAndView addCreditCard(@ModelAttribute("creditCard")  CreditCardBean ccBean){
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		if(checkCardValidity(ccBean, user)){
			return new ModelAndView("redirect:/profile.html");
		}
		else{
			return new ModelAndView("redirect:/creditcard.html");
		}
	}
	
	private boolean checkCardValidity(CreditCardBean ccBean, User user){
		boolean valid = true;
		if(ccBean.getCardType().equals("Visa")){
			CreditCard visa = new Visa(ccBean.getCardNumber(), ccBean.getExpiryMonth(), ccBean.getExpiryYear());
			if(!visa.validate()){
				valid = false;
			}
			else{
				Account account = accService.getAccount(user.getAccount().getId());
				visa.setAccount(account);
				visa.setCardType("Visa");
				account.setCreditCard(visa);
				
				accService.saveCreditCard(visa);
				accService.updateAccount(account);
			}
		}
		if(ccBean.getCardType().equals("MasterCard")){
			CreditCard masterCard = new MasterCard(ccBean.getCardNumber(), ccBean.getExpiryMonth(), ccBean.getExpiryYear());
			if(!masterCard.validate()){
				valid = false;
			}
			else{
				Account account = accService.getAccount(user.getAccount().getId());
				masterCard.setAccount(account);
				masterCard.setCardType("MasterCard");
				account.setCreditCard(masterCard);
				
				accService.saveCreditCard(masterCard);
				accService.updateAccount(account);
			}
		}
		return valid;
	}
	
	private User prepareModel(UserBean userBean){
		Account acc = new Account();
		acc.setBalance(0.0);
		acc.addToRaglanroad(0.0);
		acc.addToLucayan(0.0);
		acc.addToGingermc(0.0);
		User user = new User();
		user.setFirstName(userBean.getFirstName());
		user.setLastName(userBean.getLastName());
		user.setUserAge(userBean.getAge());
		user.setUserPassword(userBean.getPassword());
		user.setUserId(userBean.getId());
		user.setUserEmail(userBean.getEmail());
		user.setAccount(acc);
		userBean.setId(null);
		return user;
	}

	private List<UserBean> prepareListofBean(List<User> users){
		List<UserBean> beans = null;
		if(users != null && !users.isEmpty()){
			beans = new ArrayList<UserBean>();
			UserBean bean = null;
			for(User user : users){
				bean = new UserBean();
				bean.setPassword(user.getUserPassword());
				bean.setId(user.getUserId());
				bean.setEmail(user.getUserEmail());
				bean.setAge(user.getUserAge());
				beans.add(bean);
			}
		}
		return beans;
	}
	
	private boolean validateUser(UserBean userBean){
		boolean valid = true;
		InternetAddress internetAddress;
		try {
			internetAddress = new InternetAddress(userBean.getEmail());
			internetAddress.validate();
		} catch (AddressException e1) {
			valid = false;
		}
		
		if(userBean.getFirstName().equals("") || userBean.getFirstName().matches(".*\\d+.*")){
			valid = false;
		}
		if(userBean.getLastName().equals("") || userBean.getLastName().matches(".*\\d+.*")){
			valid = false;
		}
		if(userBean.getAge()==null || userBean.getAge()<18){
			valid = false;
		}
		if(userBean.getEmail().equals("")){
			valid = false;
		}
		if(userBean.getPassword().equals("")){
			valid = false;
		}
		
		return valid;
	}
}
