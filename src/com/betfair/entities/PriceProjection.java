package com.betfair.entities;

import java.util.HashSet;
import java.util.Set;

import com.betfair.enums.PriceData;

public class PriceProjection {
	
	private Set<PriceData> priceData = new HashSet<PriceData>();

	public Set<PriceData> getPriceData() {
		return priceData;
	}

	public void setPriceData(Set<PriceData> priceData) {
		this.priceData = priceData;
	}
	
	public void addPriceData(PriceData data){
		this.priceData.add(data);
	}

}
