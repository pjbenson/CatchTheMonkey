package com.spring.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.betfair.entities.Order;

@Repository("order")
public class OrderDAOImpl implements OrderDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Order getOrder(int orderId) {
		return (Order) sessionFactory.getCurrentSession().get(Order.class, orderId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getOrdersList() {
		return (List<Order>) sessionFactory.getCurrentSession().createCriteria(Order.class).list();
	}

}
