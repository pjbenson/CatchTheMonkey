package com.betfair.enums;

public enum Operation {
	LISTEVENTTYPES("listEventTypes"), 
	LISTCOMPETITIONS("listCompetitions"),
	LISTTIMERANGES("listTimeRanges"),
	LISTEVENTS("listEvents"),
	LISTMARKETTYPES("listMarketTypes"),
	LISTCOUNTRIES("listCountries"),
	LISTVENUES("listVenues"),
	LISTMARKETCATALOGUE("listMarketCatalogue"),
	LISTMARKETBOOK("listMarketBook"),
	PLACORDERS("placeOrders");
	
	private String operationName;
	
	private Operation(String operationName){
		this.operationName = operationName;
	}

	public String getOperationName() {
		return operationName;
	}

}
