package com.spring.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.betfair.entities.Runner;

@Repository("runner")
public class RunnerDAOImpl implements RunnerDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Runner getRunner(long selectionID) {
		List<Runner> list = getRunnersList();
		Runner r = null;
		for(Runner runner: list){
			if(runner.getSelectionId()==selectionID){
				r = runner;
			}
		}
		return r;
		//return (Runner) sessionFactory.getCurrentSession().get(Runner.class, selectionID);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Runner> getRunnersList() {
		return (List<Runner>) sessionFactory.getCurrentSession().createCriteria(Runner.class).list();
	}

}
