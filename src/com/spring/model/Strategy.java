package com.spring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;

@SuppressWarnings("serial")
@Entity
@Table(name="Strategy")
public class Strategy implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "STRATEGY_ID")
	private Integer id;
	
	@Column(name = "STRATEGY_NAME")
	private String name;
	
	@Column(name = "STRATEGY_POOL")
	private Double pool;
	
	@ManyToMany(mappedBy = "strategies", fetch = FetchType.EAGER)
	private List<Account> accounts = new ArrayList<Account>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Account> getAccounts() {
		return accounts;
	}
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	public Double getPool() {
		return pool;
	}
	public void addToPool(Double pool) {
		this.pool = this.pool+pool;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

}
