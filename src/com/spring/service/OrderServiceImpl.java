package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.betfair.entities.Order;
import com.spring.dao.OrderDAO;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderDAO orderDao;

	@Override
	public Order getOrder(int orderId) {
		return orderDao.getOrder(orderId);
	}

	@Override
	public List<Order> getOrderList() {
		return orderDao.getOrdersList();
	}

}
