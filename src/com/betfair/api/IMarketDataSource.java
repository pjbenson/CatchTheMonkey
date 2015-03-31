package com.betfair.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.betfair.entities.MarketBook;
import com.betfair.entities.MarketCatalogue;
import com.betfair.entities.MarketFilter;
import com.betfair.entities.PlaceExecutionReport;
import com.betfair.entities.PlaceInstruction;
import com.betfair.entities.PriceProjection;
import com.betfair.enums.MarketProjection;
import com.betfair.enums.MatchProjection;
import com.betfair.enums.OrderProjection;
import com.betfair.exceptions.APINGException;

public interface IMarketDataSource {
	
	public List<MarketCatalogue> listMarketCatalogue(MarketFilter filter, Set<MarketProjection> marketProjection, String maxResult)  throws APINGException;
	
	public List<MarketBook> listMarketBook(List<String> marketIds, PriceProjection priceProjection, OrderProjection orderProjection) throws APINGException;
	
	public PlaceExecutionReport placeOrders(String marketId, List<PlaceInstruction> instructions) throws APINGException;
	
	public String request(String operation, Map<String, Object> params) throws APINGException;

}
