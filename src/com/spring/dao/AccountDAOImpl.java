package com.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.model.Account;
import com.spring.model.CreditCard;
import com.spring.model.User;

@Repository("accountDAO")
public class AccountDAOImpl implements AccountDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session openSession() {
        return sessionFactory.getCurrentSession();
    }

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> getAllAccounts() {
		return (List<Account>) sessionFactory.getCurrentSession().createCriteria(Account.class).list();
	}

	@Override
	@Transactional
	public void saveCreditCard(CreditCard cc) {
		sessionFactory.getCurrentSession().save(cc);
	}

	@Override
	@Transactional
	public CreditCard getCrediCard(int id) {
		return (CreditCard) sessionFactory.getCurrentSession().get(CreditCard.class, id);
	}

}
