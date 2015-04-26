package com.betfair.strategies;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Persister;

import com.betfair.api.IMarketDataSource;
import com.betfair.dao.MarketDataPersister;
import com.betfair.entities.LimitOrder;
import com.betfair.entities.MarketBook;
import com.betfair.entities.MarketCatalogue;
import com.betfair.entities.MarketFilter;
import com.betfair.entities.Order;
import com.betfair.entities.PlaceExecutionReport;
import com.betfair.entities.PlaceInstruction;
import com.betfair.entities.PriceProjection;
import com.betfair.entities.Runner;
import com.betfair.entities.RunnerCatalogue;
import com.betfair.entities.TimeRange;
import com.betfair.enums.ExecutionReportStatus;
import com.betfair.enums.MarketProjection;
import com.betfair.enums.OrderProjection;
import com.betfair.enums.OrderType;
import com.betfair.enums.PersistenceType;
import com.betfair.enums.PriceData;
import com.betfair.enums.Side;
import com.betfair.exceptions.APINGException;

public class RaglanRoad implements IStrategy{
	private Double pool = 100.0;
	private IMarketDataSource dataSource;
	private Set<String> eventTypeIds = new HashSet<String>();
	private MarketFilter marketFilter = new MarketFilter();
	private Set<String> countries = new HashSet<String>();
	private Set<String> typesCode = new HashSet<String>();
	private TimeRange time = new TimeRange();
	private Set<MarketProjection> marketProjection = new HashSet<MarketProjection>();
	private MarketDataPersister persister;
	private List<Runner> runners;

	public RaglanRoad(IMarketDataSource marketData){
		dataSource = marketData;
		persister = new MarketDataPersister();
	}
	
	@Override
	public void execute() throws ParseException, APINGException {
		strategyCalculation();
	}
	
	@Override
	public void strategyCalculation() throws APINGException, ParseException{
		List<MarketBook> marketBooks = getMarketPrices();
		List<PlaceInstruction> instructions = new ArrayList<PlaceInstruction>();
		PlaceInstruction instruction1 = new PlaceInstruction();
		PlaceInstruction instruction2 = new PlaceInstruction();
		PlaceInstruction instruction3 = new PlaceInstruction();
		double price1, price2, price3;
		double a,b,c;
		double total;
		double ratio1, ratio2,ratio3;
		double exp1, exp2, exp3;
		
		if(marketBooks.isEmpty()){
			System.out.print("No markets today for RaglanRoad: "+new Date());
			return;
		}
		
		for(MarketBook mb: marketBooks){
			if(mb.getRunners().size() < 3)continue;
			price1 = mb.getRunners().get(0).getEx().getAvailableToBack().get(0).getPrice();
			price2 = mb.getRunners().get(1).getEx().getAvailableToBack().get(0).getPrice();
			price3 = mb.getRunners().get(2).getEx().getAvailableToBack().get(0).getPrice();
			a = price2*price3;
			b = price1*price3;
			c = price1*price2;
			total = a+b+c;
			ratio1 = Math.round((a/total)*pool);
			ratio2 = Math.round((b/total)*pool);
			ratio3 = Math.round((c/total)*pool);
			exp1 = Math.round(ratio1*price1-pool);
			exp2 = Math.round(ratio2*price2-pool);
			exp3 = Math.round(ratio3*price3-pool);
			System.out.print(price1+" "+price2+" "+price3+"\n");
			System.out.print(ratio1+" "+ratio2+" "+ratio3+"\n");
			System.out.print(exp1+" "+exp2+" "+exp3+"\n");
			
			instruction1.setSelectionId(mb.getRunners().get(0).getSelectionId());
			instruction1.setSide(Side.BACK);
			instruction1.setHandicap(0);
			instruction1.setOrderType(OrderType.LIMIT);
			LimitOrder limitOrder = new LimitOrder();
			limitOrder.setPrice(price1);
			limitOrder.setSize(ratio1);
			limitOrder.setPersistenceType(PersistenceType.LAPSE);
			instruction1.setLimitOrder(limitOrder);
			instructions.add(instruction1);

			instruction2.setSelectionId(mb.getRunners().get(1).getSelectionId());
			instruction2.setSide(Side.BACK);
			instruction2.setHandicap(0);
			instruction2.setOrderType(OrderType.LIMIT);
			LimitOrder limitOrder2 = new LimitOrder();
			limitOrder2.setPrice(price2);
			limitOrder2.setSize(ratio2);
			limitOrder2.setPersistenceType(PersistenceType.LAPSE);
			instruction2.setLimitOrder(limitOrder2);
			instructions.add(instruction2);
			
			instruction3.setSelectionId(mb.getRunners().get(2).getSelectionId());
			instruction3.setSide(Side.BACK);
			instruction3.setHandicap(0);
			instruction3.setOrderType(OrderType.LIMIT);
			LimitOrder limitOrder3 = new LimitOrder();
			limitOrder3.setPrice(price3);
			limitOrder3.setSize(ratio3);
			limitOrder3.setPersistenceType(PersistenceType.LAPSE);
			instruction3.setLimitOrder(limitOrder3);
			instructions.add(instruction3);
			
			Order o1 = generateOrderObject(instructions.get(0), price1, ratio1, exp1);
			persister.persistOrder(o1);
			Order o2 = generateOrderObject(instructions.get(1), price2, ratio2, exp2);
			persister.persistOrder(o2);
			Order o3 = generateOrderObject(instructions.get(2), price3, ratio3, exp3);
			persister.persistOrder(o3);
			runners = getRunners(mb);
			Runner r1 = runners.get(0);
			r1.addOrder(o1);
			persister.persistRunner(r1);
			Runner r2 = runners.get(1);
			r2.addOrder(o2);
			persister.persistRunner(r2);
			Runner r3 = runners.get(2);
			r3.addOrder(o3);
			persister.persistRunner(r3);

			PlaceExecutionReport placeBetResult = dataSource.placeOrders(mb.getMarketId(), instructions);
			
			if (placeBetResult.getStatus() == ExecutionReportStatus.SUCCESS) {
                System.out.println("Your bet has been placed!!");
                System.out.println(placeBetResult.getInstructionReports());
            } else if (placeBetResult.getStatus() == ExecutionReportStatus.FAILURE) {
                System.out.println("Your bet has NOT been placed :*( ");
                System.out.println("The error is: " + placeBetResult.getErrorCode() + ": " + placeBetResult.getErrorCode().getMessage());
            }
		}
		persister.closeResources();
	}
	
	public List<Runner> getRunners(MarketBook marketBook){
		List<Runner> runners = new ArrayList<Runner>(); 
		for(Runner runner: marketBook.getRunners()){
			runners.add(runner);
		}
		return runners;
	}
	
	public Order generateOrderObject(PlaceInstruction instruction, Double price, Double size, Double expReturn){
		Order order = new Order();
		order.setPrice(price);
		order.setSide("BACK");
		order.setSize(size);
		order.setExp_winnigs(expReturn);
		order.setBspLiability(size);
		order.setOrderType(instruction.getOrderType().toString());
		order.setPlacedDate(new Date());
		return order;
	}
	
	@Override
	public List<MarketBook> getMarketPrices() throws APINGException, ParseException{
		List<MarketCatalogue> markets = getListMarketCatalogue();
		List<String> marketIds = new ArrayList<String>();
		PriceProjection priceProjection = new PriceProjection();
		priceProjection.addPriceData(PriceData.EX_BEST_OFFERS);
		for(MarketCatalogue market: markets){
			marketIds.add(market.getMarketId());
		}

		return dataSource.listMarketBook(marketIds, priceProjection, OrderProjection.EXECUTABLE);
	}
	
	@Override
	public List<MarketCatalogue> getListMarketCatalogue() throws APINGException, ParseException{
		Date dt = new Date();
		dt.setHours(21);
		eventTypeIds.add("7");
		countries.add("GB");
		countries.add("IE");
		typesCode.add("WIN");
		time.setFrom(new Date());
		time.setTo(dt);
		marketFilter.setMarketCountries(countries);
		marketFilter.setMarketTypeCodes(typesCode);
		marketFilter.setEventTypeIds(eventTypeIds);
		marketFilter.setMarketStartTime(time);
		marketProjection.add(MarketProjection.MARKET_START_TIME);
		marketProjection.add(MarketProjection.RUNNER_DESCRIPTION);
		String maxResults = "50";
		List<MarketCatalogue> listMarketCatalogue = dataSource.listMarketCatalogue(marketFilter, marketProjection, maxResults);
		List<MarketCatalogue> result = new ArrayList<MarketCatalogue>();
	
		for(MarketCatalogue marketCatalogue: listMarketCatalogue){
			if(marketCatalogue.getMarketName().contains("Mdn") && marketCatalogue.getMarketName().contains("Hrd")){
				result.add(marketCatalogue);
				persister.persistRunnerCatalogue(marketCatalogue.getRunners().get(0));
				persister.persistRunnerCatalogue(marketCatalogue.getRunners().get(1));
				persister.persistRunnerCatalogue(marketCatalogue.getRunners().get(2));
				persister.persistMarketCatalogue(marketCatalogue, 1, 0);
			}
		}
		return result;
	}

	public Double getPool() {
		return pool;
	}

	public void addToPool(Double pool) {
		this.pool = this.pool + pool;
	}
}
