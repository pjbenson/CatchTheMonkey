package com.betfair.main;

import java.io.IOException;
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
	private static Context raglanRoadContext;
	private static Context gingerMcContext;
	private static Context lucayanContext;

	public MainDriver() throws APINGException, ParseException, IOException{
		IMarketDataSource marketDS = new MarketDataWrapper();
		raglanRoadContext = new Context(new RaglanRoad(marketDS));
		gingerMcContext = new Context(new GingerMc(marketDS));
		lucayanContext = new Context(new Lucayan(marketDS));
	}

	static class RunStrategyTimerTask extends TimerTask {
		@Override
		public void run(){
			try {
				raglanRoadContext.executeStrategy();
				gingerMcContext.executeStrategy();
				lucayanContext.executeStrategy();
			} catch (APINGException | ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws APINGException, InterruptedException, ParseException, IOException{
		new MainDriver();
		TimerTask strategyTask = new RunStrategyTimerTask();
		Timer timer = new Timer();
		Date strategyDate = new Date();
		strategyDate.setHours(13);
		strategyDate.setMinutes(00);
		timer.schedule(strategyTask, strategyDate, 1000*60*60*24);
		
//		new DistributeProfits();
	}

}
