package com.spring.dao;

import com.spring.model.Strategy;

public interface StrategyDAO {
	
	public Strategy getStrategyById(int id);
	
	public void addAccountToStrategy(Strategy strategy);
}
