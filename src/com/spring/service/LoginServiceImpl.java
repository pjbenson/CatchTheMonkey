package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.LoginDAO;
import com.spring.model.User;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginDAO loginDAO;

	public void setLoginDAO(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}

	@Override
	public User checkLogin(String userEmail, String userPassword) {
		System.out.println("In Service class...Check Login");
        return loginDAO.checkLogin(userEmail, userPassword);
	}

	@Override
	public void logOut() {
		System.out.println("Logging out...");
		loginDAO.logOut();
	}

}
