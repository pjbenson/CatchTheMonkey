package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.betfair.entities.MarketCatalogue;
import com.spring.dao.MarketCatalogueDAO;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MarketCatalogueServiceImpl implements MarketCatalogueService {
	
	@Autowired
	private MarketCatalogueDAO marketCatalogueDAO;

	@Override
	public MarketCatalogue getMarketCatalogue(String marketId) {
		return marketCatalogueDAO.getMarketCatalogue(marketId);
	}

	@Override
	public List<MarketCatalogue> getMarketCatalogueList() {
		return marketCatalogueDAO.getMarketCatalogueList();
	}

}
