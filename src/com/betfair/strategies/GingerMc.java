package com.betfair.strategies;

import java.text.ParseException;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

public class GingerMc implements IStrategy {

	private Set<String> eventTypeIds = new HashSet<String>();
	private MarketFilter marketFilter = new MarketFilter();
	private Set<String> countries = new HashSet<String>();
	private Set<String> typesCode = new HashSet<String>();
	private TimeRange time = new TimeRange();
	private Set<MarketProjection> marketProjection = new HashSet<MarketProjection>();
	private IMarketDataSource dataSource;
	private MarketDataPersister persister;
	private Double pool = 100.0;
	private List<Long> selectionIds = new ArrayList<Long>();

	public GingerMc(IMarketDataSource marketData) {
		dataSource = marketData;
		persister = new MarketDataPersister();
	}

	@Override
	public void execute() throws ParseException, APINGException {
		strategyCalculation();
	}

	@Override
	public void strategyCalculation() throws APINGException, ParseException {
		List<MarketBook> marketBooks = getMarketPrices();
		Double size = pool*.1;
		int index = 0;

		if(marketBooks.isEmpty()){
			System.out.print("No markets today for GingerMC: "+new Date());
			return;
		}

		for(MarketBook mktBook: marketBooks){
			for(Runner runner: mktBook.getRunners()){
				if(mktBook.getRunners().get(0) == runner && selectionIds.contains(runner.getSelectionId()) && !runner.getStatus().equals("REMOVED")){
					PlaceInstruction instruction = new PlaceInstruction();
					List<PlaceInstruction> instructions = new ArrayList<PlaceInstruction>();
					instruction.setSelectionId(runner.getSelectionId());
					instruction.setSide(Side.LAY);
					instruction.setHandicap(0);
					instruction.setOrderType(OrderType.LIMIT);
					LimitOrder limitOrder = new LimitOrder();
					limitOrder.setPrice(runner.getEx().getAvailableToLay().get(0).getPrice());
					limitOrder.setSize(size);
					limitOrder.setPersistenceType(PersistenceType.LAPSE);
					instruction.setLimitOrder(limitOrder);
					instructions.add(instruction);

					Order order = generateOrder(size, runner, instruction);
					persister.persistOrder(order);
					runner.addOrder(order);
					persister.persistRunner(runner);

					PlaceExecutionReport placeBetResult = dataSource.placeOrders(mktBook.getMarketId(), instructions);
					if (placeBetResult.getStatus() == ExecutionReportStatus.SUCCESS) {
						System.out.println("Your bet has been placed!!");
						System.out.println(placeBetResult.getInstructionReports());
					} else if (placeBetResult.getStatus() == ExecutionReportStatus.FAILURE) {
						System.out.println("Your bet has NOT been placed :*( ");
						System.out.println("The error is: " + placeBetResult.getErrorCode() + ": " + placeBetResult.getErrorCode().getMessage());
					}
				}
			}

		}
		persister.closeResources();
	}

	private Order generateOrder(Double size, Runner runner, PlaceInstruction instruction) {
		Order order = new Order();
		order.setPrice(runner.getEx().getAvailableToLay().get(0).getPrice());
		order.setSide("LAY");
		order.setSize(size);
		order.setExp_winnigs(pool*.1);
		order.setBspLiability((runner.getEx().getAvailableToLay().get(0).getPrice())*size);
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
		int index = 0;
		for(MarketCatalogue market: markets){
			if(isChosenRunnerPresent(market.getRunners().get(0))){
				marketIds.add(market.getMarketId());
				persister.persistMarketCatalogue(market, 2, selectionIds.get(index));
				index++;
			}
		}

		return dataSource.listMarketBook(marketIds, priceProjection, OrderProjection.EXECUTABLE);
	}

	private boolean isChosenRunnerPresent(RunnerCatalogue runner){
		boolean found = false;
		
		for (Entry<String, String> entry : runner.getMetadata().entrySet()){
			if(entry.getKey().contains("FORM")){
				String form = entry.getValue();
				if(form != null && form.charAt(form.length()-1) == '2'){
					//TODO add multiple runners in the same race 
					selectionIds.add(runner.getSelectionId());
					persister.persistRunnerCatalogue(runner);
					found = true;
				}
				if(form != null && form.charAt(form.length()-1)=='-' && form.charAt(form.length()-2)=='2'){
					selectionIds.add(runner.getSelectionId());
					persister.persistRunnerCatalogue(runner);
					found = true;
				}
			}
		}

		return found;
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
		marketProjection.add(MarketProjection.RUNNER_METADATA);
		String maxResults = "50";
		List<MarketCatalogue> listMarketCatalogue = dataSource.listMarketCatalogue(marketFilter, marketProjection, maxResults);
		List<MarketCatalogue> result = new ArrayList<MarketCatalogue>();

		for(MarketCatalogue marketCatalogue: listMarketCatalogue){
			if(marketCatalogue.getMarketName().contains("Mdn") && !marketCatalogue.getMarketName().contains("Hrd") && !marketCatalogue.getMarketName().contains("Chs")){
				result.add(marketCatalogue);
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
