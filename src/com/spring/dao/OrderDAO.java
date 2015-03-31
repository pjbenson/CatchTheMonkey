package com.spring.dao;

import java.util.List;

import com.betfair.entities.Order;

public interface OrderDAO {
	
	public Order getOrder(int orderId);
	
	public List<Order> getOrdersList();
}
