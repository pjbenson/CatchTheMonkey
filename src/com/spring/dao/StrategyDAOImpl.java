package com.spring.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.model.Strategy;

@Repository("strategyDao")
public class StrategyDAOImpl implements StrategyDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Strategy getStrategyById(int id) {
		return (Strategy) sessionFactory.getCurrentSession().get(Strategy.class, id);
	}

	@Override
	@Transactional(readOnly = false)
	public void addAccountToStrategy(Strategy strategy) {
		sessionFactory.getCurrentSession().update(strategy);
	}

}
