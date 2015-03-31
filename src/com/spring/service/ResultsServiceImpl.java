package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.betfair.entities.Result;
import com.spring.dao.ResultsDAO;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ResultsServiceImpl implements ResultsService {

	@Autowired
	private ResultsDAO resultsDAO;
	
	@Override
	public List<Result> getResults() {
		return resultsDAO.getResults();
	}

}
