package com.betfair.main;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.betfair.exceptions.APINGException;

public class Listener implements ServletContextListener {
	private MainDriver driver;
	private DistributeProfits profits;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			driver = new MainDriver();
			driver.executeStrategies();
			profits = new DistributeProfits();
		} catch (APINGException | Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
