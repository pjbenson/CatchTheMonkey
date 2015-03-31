package com.spring.dao;

import java.util.List;

import com.betfair.entities.MarketCatalogue;

public interface MarketCatalogueDAO {
	
	public MarketCatalogue getMarketCatalogue(String marketId);
	
	public List<MarketCatalogue> getMarketCatalogueList();
}
