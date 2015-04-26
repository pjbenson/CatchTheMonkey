package com.betfair.main;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.betfair.exceptions.APINGException;

public class Listener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
//		MainDriver driver;
//		try {
////			driver = new MainDriver();
////			driver.test();
//		} catch (APINGException | Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
}
