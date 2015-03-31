package com.spring.controller;

import static com.googlecode.charts4j.Color.ALICEBLUE;
import static com.googlecode.charts4j.Color.BLACK;
import static com.googlecode.charts4j.Color.BLUEVIOLET;
import static com.googlecode.charts4j.Color.LAVENDER;
import static com.googlecode.charts4j.Color.LIMEGREEN;
import static com.googlecode.charts4j.Color.ORANGERED;
import static com.googlecode.charts4j.Color.WHITE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.betfair.entities.MarketCatalogue;
import com.betfair.entities.Result;
import com.betfair.entities.Runner;
import com.googlecode.charts4j.AxisLabels;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.AxisStyle;
import com.googlecode.charts4j.AxisTextAlignment;
import com.googlecode.charts4j.BarChart;
import com.googlecode.charts4j.BarChartPlot;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Line;
import com.googlecode.charts4j.LineChart;
import com.googlecode.charts4j.LinearGradientFill;
import com.googlecode.charts4j.PieChart;
import com.googlecode.charts4j.Plot;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.Slice;
import com.spring.model.Strategy;
import com.spring.model.User;
import com.spring.service.AccountService;
import com.spring.service.MarketCatalogueService;
import com.spring.service.OrderService;
import com.spring.service.ResultsService;
import com.spring.service.RunnerService;
import com.spring.service.StrategyService;
import com.spring.service.UserService;
import com.spring.test.UserUIObject;

@Controller
@SessionAttributes({"strategy", "order", "runners"})
public class RaglanRoadController {

	@Autowired
	private RunnerService runnerService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private StrategyService strategyService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private MarketCatalogueService marketCatalogueService;
	@Autowired
	private ResultsService resultsService;
	@Autowired
	private UserService userService;

	@RequestMapping(value="raglanroad", method = RequestMethod.GET)
	public ModelAndView showRagalanRoad(ModelMap model) throws ParseException {
		LineChart lineChart = createLineChart();
		PieChart pieChart = createPieChart();
		BarChart barChart = createBarChart();
		Strategy raglanRoad = strategyService.getStrategy(1);
		List<UserUIObject> orderData = getDataForRunnerTable();
		Double totalReturnForRaglanRoad = getTotalReturnForStrategy();
		Double totalReturnForUser = getUserPercentageOfTotalReturn();
		String signUpDate = getDateUserInvested();
				
		model.put("list", orderData);
		model.put("strategy", raglanRoad);
		model.addAttribute("signUpDate", signUpDate);
		model.addAttribute("totalReturn", totalReturnForRaglanRoad);
		model.addAttribute("totalUserReturn", totalReturnForUser);
		model.addAttribute("raglanSubscribers", raglanRoad.getAccounts().size());
		model.addAttribute("pieChart", pieChart.toURLString());
		model.addAttribute("lineChart", lineChart.toURLString());
		model.addAttribute("barChart", barChart.toURLString());
		return new ModelAndView("raglanroad");
	}

	@RequestMapping(value = "/addCash", method = RequestMethod.POST)
	public ModelAndView addCashToPool(@RequestParam("amount") Double amount){
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		Strategy raglanRoad = strategyService.getStrategy(1);
		if(amount>user.getAccount().getBalance()){
			return new ModelAndView("redirect:/raglanroad.html");
		}else{
			raglanRoad.addToPool(amount);
			strategyService.addAccountToStrategy(raglanRoad);
			user.getAccount().addToRaglanroad(amount);
			user.getAccount().setBalance(user.getAccount().getBalance() - amount);
			userService.updateBalance(user);
			return new ModelAndView("redirect:/raglanroad.html");
		}
		
	}

	@RequestMapping(value = "/month", method = RequestMethod.POST)
	public ModelAndView sortLineChartByMonth(@RequestParam Integer val, ModelMap model){
		System.out.print("heeloo" + "  "+val);
        return new ModelAndView("redirect:/index.html");
	}
	
	private BarChart createBarChart(){
		User u = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		User user = userService.getUser(u.getUserId());
		List<Result> results =  resultsService.getResults();
		Map<Date, Double> dailyReturns = getDailyReturns(results);
		Map<Date, Double> orderedReturns = new TreeMap<Date, Double>(dailyReturns);
		List<String> dates = new ArrayList<String>();
		List<Double> returns = new ArrayList<Double>();
		for (Map.Entry<Date, Double> entry : orderedReturns.entrySet())
		{
			if(entry.getKey().after(user.getAccount().getLucayanRegisterDate())){
				Integer temp = entry.getKey().getDate();
				String date = Integer.toString(temp);
				System.out.println(date);
				dates.add(date);
				if(entry.getValue() > 100){
					Double val = entry.getValue()-100;
					returns.add(val);
				}else{
					System.out.println(entry.getValue());
					returns.add(entry.getValue());
				}
			}
		}
		
		//BarChartPlot team1 = Plots.newBarChartPlot(Data.newData(0, 90, 30, 20, 5, 80, 45, 10, 0, 0, 0, 0), BLUEVIOLET);
		BarChartPlot team1 = Plots.newBarChartPlot(Data.newData(returns), BLUEVIOLET);
        // Instantiating chart.
        BarChart chart = GCharts.newBarChart(team1);

        // Defining axis info and styles
        AxisStyle axisStyle = AxisStyle.newAxisStyle(BLACK, 13, AxisTextAlignment.CENTER);
        AxisLabels score = AxisLabelsFactory.newAxisLabels("€", 50.0);
        score.setAxisStyle(axisStyle);
        AxisLabels year = AxisLabelsFactory.newAxisLabels("Month", 50.0);
        year.setAxisStyle(axisStyle);

        // Adding axis info to chart.
        chart.addXAxisLabels(AxisLabelsFactory.newAxisLabels(dates));
//        chart.addXAxisLabels(AxisLabelsFactory.newAxisLabels("Jan", "Feb", "Mar", "Apr", "May",
//        													"Jun", "Jul", "Aug", "Sep", "Oct",
//        													"Nov", "Dec"));
        chart.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(0, 100));
        chart.addYAxisLabels(score);
        chart.addXAxisLabels(year);

        chart.setSize(600, 450);
        chart.setBarWidth(25);
        chart.setSpaceWithinGroupsOfBars(20);
        chart.setDataStacked(true);
        chart.setGrid(100, 10, 3, 2);
        chart.setBackgroundFill(Fills.newSolidFill(ALICEBLUE));
        LinearGradientFill fill = Fills.newLinearGradientFill(0, LAVENDER, 100);
        fill.addColorAndOffset(WHITE, 0);
        chart.setAreaFill(fill);
        
		return chart;
	}
	
	private LineChart createLineChart() throws ParseException {
		List<Result> temps =  resultsService.getResults();
		List<Result> results = getRaglanRoadResults(temps);
		Map<Date, Double> dailyReturns = getDailyReturns(results);
		Map<Date, Double> orderedReturns = new TreeMap<Date, Double>(dailyReturns);
		List<String> dates = new ArrayList<String>();    
		List<Double> returns = new ArrayList<Double>();
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");

		for (Map.Entry<Date, Double> entry : orderedReturns.entrySet())
		{
			//TODO set to show February's data only, change when user selects different month
			if(entry.getKey().getMonth() == 2){
				Integer temp = entry.getKey().getDate();
				String date = Integer.toString(temp);
				dates.add(date);
				if(entry.getValue() > 100){
					Double val = entry.getValue()-100;
					returns.add(val);
				}else{
					returns.add(entry.getValue());
				}
			}
		}

		Data data = DataUtil.scale(returns);
//		List<Plot> plots = new LinkedList<Plot>();
//		plots.add(Plots.newPlot(data));
		final Line line = Plots.newLine(Data.newData(returns));
		line.setColor(BLACK);
		final LineChart chart = GCharts.newLineChart(line);
		chart.setSize(650, 450);
		chart.setGrid(200, 5, 5, 0);
		AxisStyle axisStyle = AxisStyle.newAxisStyle(BLACK, 13, AxisTextAlignment.CENTER);
		AxisLabels amount = AxisLabelsFactory.newAxisLabels("€", 50.0);
        amount.setAxisStyle(axisStyle);
        AxisLabels Day = AxisLabelsFactory.newAxisLabels("Day of the Month", 50.0);
        Day.setAxisStyle(axisStyle);
        
		chart.setTitle("Returns|(per €100 invested)", BLACK, 14);
		chart.addXAxisLabels(AxisLabelsFactory.newAxisLabels(dates));
		chart.addYAxisLabels(AxisLabelsFactory.newAxisLabels("0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100"));
		chart.setBackgroundFill(Fills.newSolidFill(ALICEBLUE));
		LinearGradientFill fill = Fills.newLinearGradientFill(0, LAVENDER, 100);
        fill.addColorAndOffset(WHITE, 0);
        chart.setAreaFill(fill);    
		chart.addYAxisLabels(amount);
        chart.addXAxisLabels(Day);

		return chart;
	}

	private List<Result> getRaglanRoadResults(List<Result> results) {
		List<Result> temps = new ArrayList<Result>();
		for(Result res: results){
			if(res.getStrategyId() == 1){
				temps.add(res);
			}
			
		}
		return temps;
	}

	private PieChart createPieChart() {
		List<UserUIObject> orderData = getDataForRunnerTable();
		List<Slice> slices = new ArrayList<Slice>();
		List<Color> colours = getColours();
		Map<Date, Double> avgDailyReturns = getAverageDailyReturns(orderData);
		PieChart pieChart = GCharts.newPieChart(slices);
		int index=0;

		if(avgDailyReturns.size() > 1){
			for (Map.Entry<Date, Double> entry : avgDailyReturns.entrySet()){
				Double d = new Double(entry.getValue()/3);
				int percent = d.intValue();
				String avg = Integer.toString(percent);
				Slice slice = Slice.newSlice(percent, colours.get(index), avg, entry.getKey().toString());
				slices.add(slice);
				index++;
				pieChart.setTitle("Average € return per race today", Color.BLACK, 15);
			}
		}
		else{
			for(UserUIObject object: orderData){
				Double d = new Double(object.getExpWinnings());
				int percent = d.intValue();
				String avg = Integer.toString(percent);
				Slice slice = Slice.newSlice(percent, colours.get(index), avg, object.getRaceName());
				slices.add(slice);
				index++;
				pieChart.setTitle("Average return for today's race", Color.BLACK, 15);
			}
		}
		
		pieChart.setSize(720, 360);
		pieChart.setThreeD(true);
		pieChart.setBackgroundFill(Fills.newSolidFill(ALICEBLUE));
		return pieChart;
	}

	private List<Color> getColours(){
		List<Color> colours = new ArrayList<Color>();
		colours.add(Color.newColor("CACACA"));
		colours.add(Color.newColor("DF7417"));
		colours.add(Color.newColor("951800"));
		colours.add(Color.newColor("01A1DB"));
		colours.add(Color.newColor("01A1DB"));
		return colours;
	}
	
	private List<UserUIObject> getDataForRunnerTable(){
		List<UserUIObject> list = new ArrayList<UserUIObject>();
		List<MarketCatalogue> markets = marketCatalogueService.getMarketCatalogueList();
		ModelMap model = new ModelMap();
		Date today = new Date();
		int index = 0;
		
		for(MarketCatalogue mc: markets){
			if(mc.getMarketTime().getDate() == today.getDate() && mc.getMarketTime().getMonth() == today.getMonth() && mc.getStrategyId()==1){
				if(index>2){
					index = 0;
				}
				Runner runner = runnerService.getRunner(mc.getRunners().get(index).getSelectionId());
				UserUIObject tempRunner = new UserUIObject();
				tempRunner.setMarketId(mc.getMarketId());
				tempRunner.setDate(mc.getMarketTime());
				tempRunner.setRaceName(mc.getMarketName());
				tempRunner.setPrice(runner.getLastPriceTraded());
				tempRunner.setExpWinnings(runner.getOrders().get(0).getExp_winnigs());
				tempRunner.setHorseName(mc.getRunners().get(index).getRunnerName());
				tempRunner.setLiability(runner.getOrders().get(0).getBspLiability());
				list.add(tempRunner);
				model.addAttribute("marketCat", mc.getMarketId());
				index++;
			}
		}
		return list;
	}

	private Map<Date, Double> getAverageDailyReturns(List<UserUIObject> runners){
		Map<Date, Double> map = new HashMap<Date, Double>();

		for(UserUIObject object: runners){
			Double winning = map.get(object.getDate());
			if(winning == null)winning=0.0;
			map.put(object.getDate(), object.getExpWinnings()+winning);
		}
		return map;
	}

	private Map<Date, Double> getDailyReturns(List<Result> results) {
		Map<Date, Double> pairs = new HashMap<Date, Double>();
		for(Result r: results){
			Double winning = pairs.get(r.getDate());
			if(winning == null)winning=0.0;
			pairs.put(r.getDate(), r.getAmount() + winning);
		}
		return pairs;
	}
	
	private Double getUserPercentageOfTotalReturn(){
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		if(user == null || user.getAccount().getRaglanReturns() == null)return 0.0;
//		Double userInvestment = user.getAccount().getRaglanroad();
//		Double total = getTotalReturnForStrategy();
//		Double percentage = userInvestment/total;
		
		return user.getAccount().getRaglanReturns();
	}
	
	private String getDateUserInvested(){
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		if(user == null)return null;
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
		String date = fmt.format(user.getAccount().getRaglanRegisterDate());
		return date;
	}
	
	private Double getTotalReturnForStrategy(){
		List<Result> results =  resultsService.getResults();
		Double total = 0.0;
		
		for(Result result: results){
			if(result.getStrategyId()==1)
			total = total + result.getAmount();
		}
		return total;
	}

}
