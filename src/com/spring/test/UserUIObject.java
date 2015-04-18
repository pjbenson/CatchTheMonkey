package com.spring.test;

import java.util.Date;

public class UserUIObject {
	
	private String marketId;
	private String raceName;
	private String horseName;
	private Date date;
	private Double price;
	private String side;
	private Double expWinnings;
	private Double liability;
	
	public String getRaceName() {
		return raceName;
	}
	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}
	public String getHorseName() {
		return horseName;
	}
	public void setHorseName(String horseName) {
		this.horseName = horseName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getExpWinnings() {
		return expWinnings;
	}
	public void setExpWinnings(Double expWinnings) {
		this.expWinnings = expWinnings;
	}
	public String getMarketId() {
		return marketId;
	}
	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}
	public Double getLiability() {
		return liability;
	}
	public void setLiability(Double liability) {
		this.liability = liability;
	}
	public String getSide() {
		return side;
	}
	public void setSide(String side) {
		this.side = side;
	}

}
