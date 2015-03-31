package com.spring.service;

import java.util.List;

import com.betfair.entities.Order;

public interface OrderService {
	
	public Order getOrder(int orderId);
	
	public List<Order> getOrderList();

}
