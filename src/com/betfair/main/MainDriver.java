package com.betfair.main;

import java.text.ParseException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.betfair.api.IMarketDataSource;
import com.betfair.api.MarketDataWrapper;
import com.betfair.exceptions.APINGException;
import com.betfair.strategies.Context;
import com.betfair.strategies.GingerMc;
import com.betfair.strategies.Lucayan;
import com.betfair.strategies.RaglanRoad;

public class MainDriver {
	private static Context raglanRoadContext;
	private static Context gingerMcContext;
	private static Context lucayanContext;

	public MainDriver() throws APINGException, Exception{
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
			} catch (ParseException | APINGException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void test() throws APINGException, Exception{
		new MainDriver();
		TimerTask strategyTask = new RunStrategyTimerTask();
		Timer timer = new Timer();
		Date strategyDate = new Date();
		strategyDate.setHours(13);
		strategyDate.setMinutes(00);
		timer.schedule(strategyTask, strategyDate, 1000*60*60*24);
	}

	public static void main(String[] args) throws APINGException, Exception{
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
