package com.spring.service;

import java.util.List;

import com.betfair.entities.Runner;

public interface RunnerService {
	
	public Runner getRunner(long selectionID);
	
	public List<Runner> getRunnerList();

}
