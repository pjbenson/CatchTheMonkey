package com.betfair.strategies;

import java.text.ParseException;
import java.util.List;

import com.betfair.entities.MarketBook;
import com.betfair.entities.MarketCatalogue;
import com.betfair.exceptions.APINGException;

public interface IStrategy {
	
	public void execute() throws ParseException, APINGException;
	
	public void strategyCalculation() throws APINGException, ParseException;
	
	public List<MarketCatalogue> getListMarketCatalogue() throws APINGException, ParseException;
	
	public List<MarketBook> getMarketPrices() throws APINGException, ParseException;
}
