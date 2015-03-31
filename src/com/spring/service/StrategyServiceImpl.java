package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dao.StrategyDAO;
import com.spring.model.Strategy;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class StrategyServiceImpl implements StrategyService {
	
	@Autowired
	private StrategyDAO straegyDao;

	@Override
	public Strategy getStrategy(int id) {
		return straegyDao.getStrategyById(id);
	}

	@Override
	public void addAccountToStrategy(Strategy strategy) {
		straegyDao.addAccountToStrategy(strategy);
	}
}
