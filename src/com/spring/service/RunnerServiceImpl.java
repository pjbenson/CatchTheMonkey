package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.betfair.entities.Runner;
import com.spring.dao.RunnerDAO;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RunnerServiceImpl implements RunnerService {
	
	@Autowired
	private RunnerDAO runnerDao;

	@Override
	public Runner getRunner(long selectionID) {
		return runnerDao.getRunner(selectionID);
	}

	@Override
	public List<Runner> getRunnerList() {
		return runnerDao.getRunnersList();
	}

}
