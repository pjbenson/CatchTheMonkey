package com.betfair.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.betfair.api.IMarketDataSource;
import com.betfair.api.MarketDataWrapper;
import com.betfair.entities.MarketBook;
import com.betfair.entities.MarketCatalogue;
import com.betfair.entities.Result;
import com.betfair.entities.Runner;
import com.betfair.entities.RunnerCatalogue;
import com.betfair.exceptions.APINGException;
import com.betfair.strategies.Context;
import com.betfair.strategies.GingerMc;
import com.betfair.strategies.Lucayan;
import com.betfair.strategies.RaglanRoad;

public class MainDriver {
	private static RaglanRoad rg = null;
	private List<MarketCatalogue> list = null;
	private static Context raglanRoadContext;
	private static Context gingerMcContext;
	private static Context lucayanContext;

	public MainDriver() throws APINGException, ParseException{
		IMarketDataSource marketDS = new MarketDataWrapper();
		raglanRoadContext = new Context(new RaglanRoad(marketDS));
		gingerMcContext = new Context(new GingerMc(marketDS));
		lucayanContext = new Context(new Lucayan(marketDS));
//		GingerMc ginger = new GingerMc(marketDS);
//		ginger.strategyCalculation();
//		rg = new RaglanRoad(marketDS);
//		list = ginger.getListMarketCatalogue();
////
//		printMarketCatalogue(list);
//		List<MarketBook> list2 = ginger.getMarketPrices();
//		printMarketBook(list2);
//		context.executeStrategy();
//		rg.strategyCalculation();
//		list = persister.getAllMarkets();
//		printMarketCatalogue(list);
	}

//	private void printMarketCatalogue(List<MarketCatalogue> mk){
//		for(MarketCatalogue mc: mk){
//			System.out.println("Market Name: "+mc.getMarketName() + "; Id: "+mc.getMarketId()+"Time: "+mc.getMarketTime().toString());
//			for(RunnerCatalogue rc: mc.getRunners()){
//				System.out.println("Runner Name: " + rc.getRunnerName() + "; Id: " + rc.getSelectionId());
//			}
//		}
//
//	}

//	private void printMarketBook(List<MarketBook> mb){
//		for(MarketBook market: mb){
//			System.out.println("Market id: "+ market.getMarketId());
//			for(Runner runner: market.getRunners()){
//				System.out.println("Selection id: "+ runner.getSelectionId() + ", Price: "+ runner.getLastPriceTraded());
//			}
//		}
//	}

	static class RunStrategyTimerTask extends TimerTask {
		@Override
		public void run(){
			try {
				//raglanRoadContext.executeStrategy();
				gingerMcContext.executeStrategy();
				//lucayanContext.executeStrategy();
			} catch (APINGException | ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws APINGException, InterruptedException, ParseException{
		new MainDriver();
		TimerTask timerTask = new RunStrategyTimerTask();
		Timer timer = new Timer();
		Date date = new Date();
		date.setHours(13);
		date.setMinutes(00);
		timer.schedule(timerTask, date);
		
		
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("CatchTheMonkey");
//		EntityManager em = emf.createEntityManager();
//		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
//		Date date = fmt.parse("04-03-2015");
//		Result result = new Result();
//		result.setAmount(12.5);
//		result.setDate(date);
//		result.setHorseName("Akorakor");
//		em.getTransaction().begin();
//		em.persist(result);
//		em.getTransaction().commit();
//		em.close();
//		System.out.println("HAPPY DAYS!!");
	}

}
