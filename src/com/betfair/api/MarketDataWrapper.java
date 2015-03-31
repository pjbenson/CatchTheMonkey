package com.betfair.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.betfair.containers.ListMarketBooks;
import com.betfair.containers.ListMarketCatalogue;
import com.betfair.containers.PlaceOrders;
import com.betfair.entities.MarketBook;
import com.betfair.entities.MarketCatalogue;
import com.betfair.entities.MarketFilter;
import com.betfair.entities.PlaceExecutionReport;
import com.betfair.entities.PlaceInstruction;
import com.betfair.entities.PriceProjection;
import com.betfair.enums.MarketProjection;
import com.betfair.enums.MatchProjection;
import com.betfair.enums.Operation;
import com.betfair.enums.OrderProjection;
import com.betfair.exceptions.APINGException;
import com.betfair.jsonrpc.JsonConverter;
import com.betfair.jsonrpc.JsonrpcRequest;

public class MarketDataWrapper implements IMarketDataSource{
	private final String INSTRUCTIONS = "instructions";
	private final String CUSTOMER_REF = null;
	private static MarketDataWrapper instance;
	private final String MAX_RESULT = "maxResults";
	private final String MARKET_IDS = "marketIds";
	private final String MARKET_ID = "marketId";
	private final String FILTER = "filter";
	private final String MARKET_PROJECTION = "marketProjection";
	private final String PRICE_PROJECTION = "priceProjection";
	
	public static MarketDataWrapper getInstance(){
		if(instance == null){
			instance =  new MarketDataWrapper();
		}
		return instance;
	}

	@Override
	public List<MarketCatalogue> listMarketCatalogue(MarketFilter filter, Set<MarketProjection> marketProjection, String maxResult) throws APINGException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(FILTER, filter);
		parameters.put(MARKET_PROJECTION, marketProjection);
		parameters.put(MAX_RESULT, maxResult);
		String result = getInstance().request(Operation.LISTMARKETCATALOGUE.getOperationName(), parameters);
		ListMarketCatalogue catalogue = JsonConverter.convertFromJson(result, ListMarketCatalogue.class);
		
		return catalogue.getResult();
	}

	@Override
	public List<MarketBook> listMarketBook(List<String> marketIds, PriceProjection priceProjection, OrderProjection orderProjection) throws APINGException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MARKET_IDS, marketIds);
		params.put(PRICE_PROJECTION, priceProjection);
		String result = getInstance().request(Operation.LISTMARKETBOOK.getOperationName(), params);
		ListMarketBooks listMarketBooks = JsonConverter.convertFromJson(result, ListMarketBooks.class);
		
		return listMarketBooks.getResult();
	}

	@Override
	public String request(String operation, Map<String, Object> params) throws APINGException {
		String requestString;
        //Handling the JSON-RPC request
        JsonrpcRequest request = new JsonrpcRequest();
        request.setId("1");
        request.setMethod("SportsAPING/v1.0/" + operation);
        request.setParams(params);
        
        requestString =  JsonConverter.convertToJson(request);
        System.out.println(requestString);
        
        HttpUtil requester = new HttpUtil();
        String response = requester.sendPostRequestJsonRpc(requestString, operation);
        System.out.println(response);
        return response;
	}

	@Override
	public PlaceExecutionReport placeOrders(String marketId, List<PlaceInstruction> instructions) throws APINGException {
		Map<String, Object> params = new HashMap<String, Object>();
        params.put(MARKET_ID, marketId);
        params.put(INSTRUCTIONS, instructions);
        String result = getInstance().request(Operation.PLACORDERS.getOperationName(), params);

        PlaceOrders orders = JsonConverter.convertFromJson(result, PlaceOrders.class);

//        if(container.getError() != null)
//            throw container.getError().getData().getAPINGException();

        return orders.getResult();
	}

}
