package com.betfair.main;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.betfair.entities.Result;
import com.spring.model.Account;
import com.spring.model.Strategy;

public class DistributeProfits {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public DistributeProfits() throws IOException, ParseException{
		new ReadExcelFile();
//		emf = Persistence.createEntityManagerFactory("CatchTheMonkey");
//		em = emf.createEntityManager();
//		disperseProfitsToUsers();
	}
	
	private void disperseProfitsToUsers(){
		Date today = new Date();
		em.getTransaction().begin();
		Query q = em.createQuery("select o from Account o");
		List<Account> accounts = q.getResultList();
		for(Account acc: accounts){
			if(acc.getRaglanRegisterDate() != null && today.after(acc.getRaglanRegisterDate())){
				Strategy strat = em.find(Strategy.class, 1);
				Double raglanReturns = (acc.getRaglanroad()/strat.getPool())*getStrategyResults(1);
				acc.addToBalance(raglanReturns);
				acc.addToLucayanReturns(raglanReturns);
			}
			if(acc.getGingerRegisterDate() != null && today.after(acc.getGingerRegisterDate())){
				Strategy strat = em.find(Strategy.class, 2);
				Double gingerReturns = (acc.getGingermc()/strat.getPool())*getStrategyResults(2);
				acc.addToBalance(gingerReturns);
				acc.addToGingerReturns(gingerReturns);
			}
			if(acc.getLucayanRegisterDate() != null && today.after(acc.getLucayanRegisterDate())){
				Strategy strat = em.find(Strategy.class, 3);
				Double lucayanReturns = (acc.getLucayan()/strat.getPool())*getStrategyResults(3);
				acc.addToBalance(lucayanReturns);
				acc.addToLucayanReturns(lucayanReturns);
			}
			em.getTransaction().commit();
		}
	}
	
	private Double getStrategyResults(int strategyId){
		Date today = new Date();
		Double daysReturn = 0.0;
		Query q = em.createQuery("select o from Result o");
		List<Result> results = q.getResultList();
		for(Result res:results){
			if(res.getDate().getDate()==today.getDate() && res.getStrategyId()==strategyId){
				daysReturn += res.getAmount();
			}
		}
		return daysReturn;
	}

}
