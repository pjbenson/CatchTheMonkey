package com.betfair.containers;

import java.util.List;

import com.betfair.entities.MarketBook;

public class ListMarketBooks extends Container{
	
	private List<MarketBook> result;
		
	public List<MarketBook> getResult() {
		return result;
	}
	public void setResult(List<MarketBook> result) {
		this.result = result;
	}
}
