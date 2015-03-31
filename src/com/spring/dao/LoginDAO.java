package com.spring.dao;

import java.util.List;

import com.spring.model.User;

public interface LoginDAO{   
	
    public User checkLogin(String userEmail, String userPassword);
    
    public void logOut();
    
    public List<User> getUsers();
}
