package com.spring.service;

import com.spring.model.Account;
import com.spring.model.Strategy;

public interface AccountService {
	
	public Account getAccount(int id);
	
	public void addStrategyToAccount(Account acc);
	
	public void updateAccount(Account acc);
}
