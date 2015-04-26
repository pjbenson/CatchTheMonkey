package com.spring.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.spring.bean.UserBean;
import com.spring.model.LoginForm;
import com.spring.model.Strategy;
import com.spring.model.User;
import com.spring.service.LoginService;
import com.spring.service.UserService;

@Controller
@SessionAttributes({"user", "loginError"})
@RequestMapping("loginform.html")
public class LoginController {

	@Autowired
	public LoginService loginService;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(ModelMap model) {
		User user = new User();
		model.addAttribute(user);
		return "loginform";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processForm(@ModelAttribute("user")@Valid User user, Map model) {
		User currentUser = loginService.checkLogin(user.getUserEmail(), user.getUserPassword());
		if(currentUser != null){
			model.put("user", currentUser);
			return new ModelAndView("redirect:/profile.html");
		}else{
			model.put("loginError", "Error, please re-enter your details!");
			return new ModelAndView("redirect:/loginform.html");
		}
	}
}
