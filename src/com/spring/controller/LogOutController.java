package com.spring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.spring.model.User;
import com.spring.service.LoginService;


@Controller
public class LogOutController {
	
	@Autowired
	public LoginService loginService;
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
    public ModelAndView logout(@ModelAttribute User user, HttpSession session) {
       loginService.logOut();
       session.removeAttribute("user");
       return new ModelAndView("redirect:/index.html");
    }

}
