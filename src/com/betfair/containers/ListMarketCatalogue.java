package com.betfair.containers;

import java.util.List;

import com.betfair.entities.MarketCatalogue;

public class ListMarketCatalogue extends Container {

	private List<MarketCatalogue> result;

	public List<MarketCatalogue> getResult() {
		return result;
	}

	public void setResult(List<MarketCatalogue> result) {
		this.result = result;
	}

}
