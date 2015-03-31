package com.betfair.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Runner")
public class Runner {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="SELECTION_ID")
	private Long selectionId;
	@Transient
	private Double handicap;
	@Transient
	private String status;
	@Transient
	private Double adjustmentFactor;
	@Column(name="PRICE")
	private Double lastPriceTraded;
	@Transient
	private Double totalMatched;
	@Transient
	private Date removalDate;
	@Transient
	private StartingPrices sp;
	@Transient
	private ExchangePrices ex;
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "runner_order", joinColumns = @JoinColumn(name = "runner_id"), inverseJoinColumns = @JoinColumn(name = "order_id"))
	private List<Order> orders;
	@Transient
	private List<Match> matches;

	public Long getSelectionId() {
		return selectionId;
	}

	public void setSelectionId(Long selectionId) {
		this.selectionId = selectionId;
	}

	public Double getHandicap() {
		return handicap;
	}

	public void setHandicap(Double handicap) {
		this.handicap = handicap;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getAdjustmentFactor() {
		return adjustmentFactor;
	}

	public void setAdjustmentFactor(Double adjustmentFactor) {
		this.adjustmentFactor = adjustmentFactor;
	}

	public Double getLastPriceTraded() {
		return lastPriceTraded;
	}

	public void setLastPriceTraded(Double lastPriceTraded) {
		this.lastPriceTraded = lastPriceTraded;
	}

	public Double getTotalMatched() {
		return totalMatched;
	}

	public void setTotalMatched(Double totalMatched) {
		this.totalMatched = totalMatched;
	}

	public Date getRemovalDate() {
		return removalDate;
	}

	public void setRemovalDate(Date removalDate) {
		this.removalDate = removalDate;
	}

	public StartingPrices getSp() {
		return sp;
	}

	public void setSp(StartingPrices sp) {
		this.sp = sp;
	}

	public ExchangePrices getEx() {
		return ex;
	}

	public void setEx(ExchangePrices ex) {
		this.ex = ex;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	public void addOrder(Order order){
		if(orders == null){
			orders = new ArrayList<Order>();
		}
		this.orders.add(order);
	}

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}

	public String toString() {
		return "{" + "" + "selectionId=" + getSelectionId() + "," + "handicap="
				+ getHandicap() + "," + "status=" + getStatus() + ","
				+ "adjustmentFactor=" + getAdjustmentFactor() + ","
				+ "lastPriceTraded=" + getLastPriceTraded() + ","
				+ "totalMatched=" + getTotalMatched() + "," + "removalDate="
				+ getRemovalDate() + "," + "sp=" + getSp() + "," + "ex="
				+ getEx() + "," + "orders=" + getOrders() + "," + "matches="
				+ getMatches() + "," + "}";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
