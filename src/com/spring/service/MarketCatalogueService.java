package com.spring.service;

import java.util.List;

import com.betfair.entities.MarketCatalogue;

public interface MarketCatalogueService {

	public MarketCatalogue getMarketCatalogue(String marketId);

	public List<MarketCatalogue> getMarketCatalogueList();

}
