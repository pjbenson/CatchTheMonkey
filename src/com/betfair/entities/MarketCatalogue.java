package com.betfair.entities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@NamedQueries({		
	@NamedQuery(name = "Market.findById", query = "select o from MarketCatalogue o where o.marketId=:a"),
	@NamedQuery(name = "Market.findAll", query = "select o from MarketCatalogue o")
})

@Entity
@Table(name="Market_Catalogue")
public class MarketCatalogue implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="MARKET_ID")
	private String marketId;
	@Column(name="MARKET_NAME")
	private String marketName;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "market_runner", joinColumns = @JoinColumn(name = "market_catalogueId"), inverseJoinColumns = @JoinColumn(name = "runner_catalogueId"))
	private List<RunnerCatalogue> runners = new ArrayList<RunnerCatalogue>();
	@Column(name="MARKET_START_NAME")
	private Date marketStartTime;
	@Column(name="STRATEGY_ID")
	private Integer strategyId;
	
	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public List<RunnerCatalogue> getRunners() {
		return runners;
	}

	public void setRunners(List<RunnerCatalogue> runners) {
		this.runners = runners;
	}
	
	public Date getMarketTime() {
		return marketStartTime;
	}
	
	public void addRunnner(RunnerCatalogue runner){
		runners.add(runner);
	}

	public void setMarketTime(Date marketTime) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
		String m = formatter.format(marketTime);
		Date date = formatter.parse(m);
		this.marketStartTime = date;
	}

	public String toString() {
		return getMarketName();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(Integer strategyId) {
		this.strategyId = strategyId;
	}

}
