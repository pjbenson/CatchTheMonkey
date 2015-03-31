package com.spring.service;

import com.spring.model.Strategy;

public interface StrategyService {
	
	public Strategy getStrategy(int id);
	
	public void addAccountToStrategy(Strategy strategy);

}
