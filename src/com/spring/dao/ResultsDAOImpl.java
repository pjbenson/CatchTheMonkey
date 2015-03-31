package com.spring.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.betfair.entities.Result;

@Repository("results")
public class ResultsDAOImpl implements ResultsDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Result> getResults() {
		return (List<Result>) sessionFactory.getCurrentSession().createCriteria(Result.class).list();
	}

}
