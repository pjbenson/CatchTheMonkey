package com.spring.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.spring.model.User;

@Repository("loginDAO")
public class LoginDAOImpl implements LoginDAO {	

	@Resource(name="sessionFactory")
	protected SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession(){
		return sessionFactory.openSession();
	}

	@Override
	public User checkLogin(String userEmail, String userPassword) {
		System.out.println("In Check login");
		User loggedIn = null;
		for(User user: getUsers()){
			if(userEmail.equals(user.getUserEmail()) && userPassword.equals(user.getUserPassword())){
				loggedIn = user;
			}
		}
		return loggedIn;
	}

	@Override
	public void logOut() {
		sessionFactory.close();
	}

	@Override
	public List<User> getUsers() {
		Session session = sessionFactory.openSession();
		String SQL_QUERY ="select o from User o";
		Query query = session.createQuery(SQL_QUERY);
		List<User> users = query.list();
		session.close();
		return users;
	}

}
