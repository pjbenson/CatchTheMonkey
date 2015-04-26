package com.spring.dao;

import java.util.List;

import com.spring.model.Account;
import com.spring.model.CreditCard;
import com.spring.model.Strategy;

public interface AccountDAO {

	public Account getAccount(int id);
	
	public List<Account> getAllAccounts();
	
	public void addStrategyToAccount(Account acc);
	
	public void updateAccount(Account acc);
	
	public void saveCreditCard(CreditCard cc);
	
	public CreditCard getCrediCard(int id);
}
