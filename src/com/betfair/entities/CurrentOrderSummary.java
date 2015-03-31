package com.betfair.entities;

import com.betfair.enums.Side;

public class CurrentOrderSummary {
	private String betId;
	private String marketId;
	private Long selectionId;
	private PriceSize priceSize;
	private Side side;
	
	public String getBetId() {
		return betId;
	}
	public void setBetId(String betId) {
		this.betId = betId;
	}
	public String getMarketId() {
		return marketId;
	}
	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}
	public Long getSelectionId() {
		return selectionId;
	}
	public void setSelectionId(Long selectionId) {
		this.selectionId = selectionId;
	}
	public PriceSize getPriceSize() {
		return priceSize;
	}
	public void setPriceSize(PriceSize priceSize) {
		this.priceSize = priceSize;
	}
	public Side getSide() {
		return side;
	}
	public void setSide(Side side) {
		this.side = side;
	}

}
