package com.betfair.strategies;

import java.text.ParseException;

import com.betfair.exceptions.APINGException;

public class Context {
	private IStrategy strategy;

	public Context(IStrategy strategy){
		this.strategy = strategy;
	}
	public void executeStrategy() throws ParseException, APINGException{
		strategy.execute();
	}
}
