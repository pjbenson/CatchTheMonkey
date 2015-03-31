package com.spring.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.model.Account;

@Repository("accountDAO")
public class AccountDAOImpl implements AccountDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Account getAccount(int id) {
		return (Account) sessionFactory.getCurrentSession().get(Account.class, id);
	}

	@Override
	@Transactional(readOnly = false)
	public void addStrategyToAccount(Account acc) {
		sessionFactory.getCurrentSession().update(acc);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateAccount(Account acc) {
		sessionFactory.getCurrentSession().update(acc);
	}

}
