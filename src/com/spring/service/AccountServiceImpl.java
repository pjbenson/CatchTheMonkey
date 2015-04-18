package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dao.AccountDAO;
import com.spring.model.Account;
import com.spring.model.Strategy;
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountDAO accountDao;

	@Override
	public Account getAccount(int id) {
		return accountDao.getAccount(id);
	}

	@Override
	public void addStrategyToAccount(Account acc) {
		accountDao.addStrategyToAccount(acc);
	}

	@Override
	public void updateAccount(Account acc) {
		accountDao.updateAccount(acc);
	}

	@Override
	public List<Account> getAllAcounts() {
		return accountDao.getAllAccounts();
	}

}
