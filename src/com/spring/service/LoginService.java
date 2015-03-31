package com.spring.service;

import com.spring.model.User;

public interface LoginService {
	
	public User checkLogin(String userEmail, String userPassword);

	public void logOut();
}
