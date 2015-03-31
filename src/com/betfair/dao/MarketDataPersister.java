package com.betfair.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.betfair.entities.MarketBook;
import com.betfair.entities.MarketCatalogue;
import com.betfair.entities.Order;
import com.betfair.entities.Runner;
import com.betfair.entities.RunnerCatalogue;

public class MarketDataPersister {
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public MarketDataPersister(){
	    emf = Persistence.createEntityManagerFactory("CatchTheMonkey");
		em = emf.createEntityManager();
	}
	
	public void persistMarketBook(MarketBook mktBook){
		em.getTransaction().begin();
		em.persist(mktBook);
		em.getTransaction().commit();
	    System.out.println("Market Book saved!!!");
	}
	
	public void persistGingerMcMarketCatalogue(MarketCatalogue mk, long runnerId) throws ParseException{
		MarketCatalogue marketC = getGingerMcMarketCat(mk, runnerId);
		em.getTransaction().begin();
		mk.setStrategyId(2);
		em.persist(marketC);
		em.getTransaction().commit();
		System.out.println("Market Catalogue saved!!!");
	}
	
	public void persistMarketCatalogue(MarketCatalogue mk, Integer strategyId, long runnerId) throws ParseException{
		MarketCatalogue marketC = null;
		if(strategyId==1){
			marketC = getOptimisedMarketC(mk, strategyId);
			em.getTransaction().begin();
			em.persist(marketC);
			em.getTransaction().commit();
		    System.out.println("Market Catalogue saved!!!");
		}
		if(strategyId==2){
			marketC = getGingerMcMarketCat(mk, runnerId);
			em.getTransaction().begin();
			em.persist(marketC);
			em.getTransaction().commit();
			System.out.println("Market Catalogue saved!!!");
		}
		if(strategyId==3){
			marketC = getOptimisedMarketC(mk, strategyId);
			em.getTransaction().begin();
			em.persist(marketC);
			em.getTransaction().commit();
		    System.out.println("Market Catalogue saved!!!");
		}
	}
	
	public void persistRunner(Runner runner){
		em.getTransaction().begin();
		em.persist(runner);
		em.getTransaction().commit();
	    System.out.println("Runner saved!!!");
	}
	
	public void persistRunnerCatalogue(RunnerCatalogue rc){
		em.getTransaction().begin();
		em.persist(rc);
		em.getTransaction().commit();
	    System.out.println("Runner Catalogue saved!!!");
	}
	
	public void persistOrders(List<Order> orders){
		em.getTransaction().begin();
		for(Order order: orders){
			em.persist(order);
			em.getTransaction().commit();
			System.out.println("Order saved!!!");
		}
	}
	
	public void persistOrder(Order order){
		em.getTransaction().begin();
		em.persist(order);
		em.getTransaction().commit();
		System.out.println("Order saved!!!");
	}
	
	private MarketCatalogue getOptimisedMarketC(MarketCatalogue mk, Integer strategyId) throws ParseException {
		MarketCatalogue marketC = new MarketCatalogue();
		marketC.setMarketId(mk.getMarketId());
		marketC.setMarketName(mk.getMarketName());
		marketC.setMarketTime(mk.getMarketTime());
		List<RunnerCatalogue> runners = new ArrayList<RunnerCatalogue>();
		runners.add(mk.getRunners().get(0));
		runners.add(mk.getRunners().get(1));
		runners.add(mk.getRunners().get(2));
		marketC.setRunners(runners);
		marketC.setStrategyId(strategyId);
		return marketC;
	}
	
	private MarketCatalogue getGingerMcMarketCat(MarketCatalogue mk, long runnerId) throws ParseException{
		MarketCatalogue market = new MarketCatalogue();
		market.setMarketId(mk.getMarketId());
		market.setMarketName(mk.getMarketName());
		market.setMarketTime(mk.getMarketTime());
		List<RunnerCatalogue> runners = new ArrayList<RunnerCatalogue>();
		for(RunnerCatalogue runner: mk.getRunners()){
			if(runner.getSelectionId()==runnerId)
				runners.add(runner);
		}
		market.setRunners(runners);
		market.setStrategyId(2);
		return market;
	}
	
	@SuppressWarnings("unchecked")
	public List<MarketCatalogue> getAllMarkets() {
		em.getTransaction().begin();
		List<MarketCatalogue> list = this.em.createNamedQuery("Market.findAll").getResultList();
		em.getTransaction().commit();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<RunnerCatalogue> getAllRunners() {
		em.getTransaction().begin();
		List<RunnerCatalogue> list = this.em.createNamedQuery("Runners.findAll").getResultList();
		em.getTransaction().commit();
		return list;
	}
	
	public void closeResources(){
		em.close();
		emf.close();
	}

}
