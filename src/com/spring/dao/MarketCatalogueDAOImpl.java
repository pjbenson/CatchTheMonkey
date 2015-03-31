package com.spring.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.betfair.entities.MarketCatalogue;

@Repository("marketcatalogue")
public class MarketCatalogueDAOImpl implements MarketCatalogueDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public MarketCatalogue getMarketCatalogue(String marketId) {
		return (MarketCatalogue) sessionFactory.getCurrentSession().get(MarketCatalogue.class, marketId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarketCatalogue> getMarketCatalogueList() {
		return (List<MarketCatalogue>) sessionFactory.getCurrentSession().createCriteria(MarketCatalogue.class).list();
	}

}
