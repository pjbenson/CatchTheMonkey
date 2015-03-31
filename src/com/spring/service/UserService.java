package com.spring.service;

import java.util.List;

import com.spring.model.User;

public interface UserService {
	
	public void updateBalance(User user);
	
	public User getUser(int userId);
	
	public void addUser(User user);
	
	public List<User> userList();
	
	public void deleteUser(User user);
}
