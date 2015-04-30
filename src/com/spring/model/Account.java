package com.spring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="Account")
public class Account implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "USER_ID")
	private Integer id;
	
	@Column(name = "BALANCE")
	private Double balance;
	
	@Column(name = "raglanroad")
	private Double raglanroad;
	
	@Column(name="gingermc")
	private Double gingermc;
	
	@Column(name="lucayan")
	private Double lucayan;
	
	@Column(name="raglan_register_date")
	private Date raglanRegisterDate;
	
	@Column(name="ginger_register_date")
	private Date gingerRegisterDate;
	
	@Column(name="lucayan_register_date")
	private Date lucayanRegisterDate;
	
	@Column(name="raglan_returns")
	private Double raglanReturns;
	
	@Column(name="ginger_returns")
	private Double gingerReturns;
	
	@Column(name="lucayan_returns")
	private Double lucayanReturns;
	
	@OneToOne
    @PrimaryKeyJoinColumn
	private User user;
	
	@OneToOne(mappedBy="account", cascade=CascadeType.ALL)
    private CreditCard creditCard;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "accounts_strategies", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "strategy_id"))
	private List<Strategy> strategies;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public void addToBalance(Double amount){
		if(this.balance == null)this.balance = amount;
		this.balance = this.balance + amount;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Strategy> getStrategies() {
		return strategies;
	}
	public void addStrategy(Strategy strategy) {
		if (strategies==null) {
			strategies = new ArrayList<Strategy>();
		}
		if (!strategies.contains(strategy)) {
			strategies.add(strategy);
		}
	}
	public Double getRaglanroad() {
		return raglanroad;
	}
	public void addToRaglanroad(Double raglanroad) {
		if(this.raglanroad == null)this.raglanroad = 0.0;
		this.raglanroad = this.raglanroad + raglanroad;
	}
	public void removeFromRaglanRoad(Double amount){
		if(this.raglanroad == null)this.raglanroad = 0.0;
		this.raglanroad = this.raglanroad - amount;
	}
	public Double getGingermc() {
		return gingermc;
	}
	public void addToGingermc(Double gingermc) {
		if(this.gingermc == null)
			this.gingermc = 0.0;
		this.gingermc = this.gingermc + gingermc;
	}
	public void removeFromGingerMc(Double amount){
		if(this.gingermc == null)this.gingermc = 0.0;
		this.gingermc = this.gingermc - amount;
	}
	public Double getLucayan() {
		return lucayan;
	}
	public void addToLucayan(Double lucayan) {
		if(this.lucayan == null)
			this.lucayan = 0.0;
		this.lucayan = this.lucayan + lucayan;
	}
	public void removeFromLucayan(Double amount){
		if(this.lucayan == null)this.lucayan = 0.0;
		this.lucayan = this.lucayan - amount;
	}
	public Date getRaglanRegisterDate() {
		return raglanRegisterDate;
	}
	public void setRaglanRegisterDate(Date raglanRegisterDate) {
		this.raglanRegisterDate = raglanRegisterDate;
	}
	public Date getGingerRegisterDate() {
		return gingerRegisterDate;
	}
	public void setGingerRegisterDate(Date gingerRegisterDate) {
		this.gingerRegisterDate = gingerRegisterDate;
	}
	public Date getLucayanRegisterDate() {
		return lucayanRegisterDate;
	}
	public void setLucayanRegisterDate(Date lucayanRegisterDate) {
		this.lucayanRegisterDate = lucayanRegisterDate;
	}
	public Double getRaglanReturns() {
		return raglanReturns;
	}
	public void addToRaglanReturns(Double amount) {
		if(this.raglanReturns == null)this.raglanReturns = 0.0;
		this.raglanReturns = this.raglanReturns + amount;
	}
	public Double getGingerReturns() {
		return gingerReturns;
	}
	public void addToGingerReturns(Double amount) {
		if(this.gingerReturns == null)this.gingerReturns = 0.0;
		this.gingerReturns = this.gingerReturns + amount;
	}
	public Double getLucayanReturns() {
		return lucayanReturns;
	}
	public void addToLucayanReturns(Double amount) {
		if(this.lucayanReturns == null)this.lucayanReturns = 0.0;
		this.lucayanReturns = this.lucayanReturns + amount;
	}
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
}
