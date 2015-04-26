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
import org.springframework.web.bind.annotation.PathVariable;
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
import com.spring.controller.PieChartData.KeyValue;
import com.spring.model.Account;
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
@SessionAttributes({"strategy", "order", "runners", "rrLineChart", "rrBarChart"})
public class RaglanRoadController {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private RunnerService runnerService;
	@Autowired
	private StrategyService strategyService;
	@Autowired
	private MarketCatalogueService marketCatalogueService;
	@Autowired
	private ResultsService resultsService;
	@Autowired
	private UserService userService;
	private String months[] = {"January", "February", "March", "April",
            "May", "June", "July", "August", "September",
            "October", "November", "December"};

	@RequestMapping(value="raglanroad", method = RequestMethod.GET)
	public ModelAndView showRagalanRoad(ModelMap model) throws ParseException {
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		if(user==null)return new ModelAndView("redirect:/loginform.html");
		String lineChart = (String) RequestContextHolder.currentRequestAttributes().getAttribute("rrLineChart", RequestAttributes.SCOPE_SESSION);
		String barChart = (String) RequestContextHolder.currentRequestAttributes().getAttribute("rrLineChart", RequestAttributes.SCOPE_SESSION);
		if(lineChart==null)model.put("rrLineChart", createLineChart(3).toURLString());
		if(barChart==null)model.put("rrBarChart", createBarChart(3).toURLString());
		
		PieChart pieChart = createPieChart();
		Strategy raglanRoad = strategyService.getStrategy(1);
		List<KeyValue> pie = PieChartData.getPieDataList();
		
		model.addAttribute("pie", pie);
		model.put("list", getDataForRunnerTable());
		model.put("strategy", raglanRoad);
		model.addAttribute("date", new Date());
		model.addAttribute("number", 44);
		model.addAttribute("signUpDate", getDateUserInvested());
		model.addAttribute("totalReturn", getTotalReturnForStrategy());
		model.addAttribute("totalUserReturn", getUserPercentageOfTotalReturn());
		model.addAttribute("raglanSubscribers", raglanRoad.getAccounts().size());
		model.addAttribute("pieChart", pieChart.toURLString());
		return new ModelAndView("raglanroad");
	}

	@RequestMapping(value = "/addRRCash", method = RequestMethod.POST)
	public ModelAndView addCashToPool(@RequestParam("amount") Double amount, ModelMap model){
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
			model.put("user", user);
			return new ModelAndView("redirect:/raglanroad.html");
		}
	}
	
	@RequestMapping(value="/barChartMonth/{id}")
	public ModelAndView updateBarChart(@PathVariable("id") Integer id, ModelMap model){
		BarChart barChart = createBarChart(id);
		model.put("rrBarChart", barChart.toURLString());
		return new ModelAndView("redirect:/raglanroad.html");
	}

	private BarChart createBarChart(Integer month){
		User u = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		User user = userService.getUser(u.getUserId());
		List<Result> results =  getRaglanRoadResults(resultsService.getResults());
		Map<Date, Double> dailyReturns = getDailyReturns(results);
		Map<Date, Double> orderedReturns = new TreeMap<Date, Double>(dailyReturns);
		List<String> dates = new ArrayList<String>();
		List<Double> returns = new ArrayList<Double>();
		
		for (Map.Entry<Date, Double> entry : orderedReturns.entrySet())
		{
			if(entry.getKey().after(user.getAccount().getRaglanRegisterDate()) && entry.getKey().getMonth() == month){
				Double profitPercentage = (user.getAccount().getRaglanroad()/100)*entry.getValue();
				if(profitPercentage<0){
					returns.add(0.0);
				}else if(profitPercentage > 100){
					returns.add(100.0);
				}else{
					returns.add(profitPercentage);
				}
				
				Integer temp = entry.getKey().getDate();
				String date = Integer.toString(temp);
				dates.add(date);
				
			}
		}

		BarChartPlot team1 = Plots.newBarChartPlot(Data.newData(returns), BLUEVIOLET);
		// Instantiating chart.
		BarChart chart = GCharts.newBarChart(team1);
		// Defining axis info and styles
		AxisStyle axisStyle = AxisStyle.newAxisStyle(BLACK, 13, AxisTextAlignment.CENTER);
		AxisLabels score = AxisLabelsFactory.newAxisLabels("€", 50.0);
		score.setAxisStyle(axisStyle);
		AxisLabels year = AxisLabelsFactory.newAxisLabels("Day of the Month", 50.0);
		year.setAxisStyle(axisStyle);

		// Adding axis info to chart.
		chart.addXAxisLabels(AxisLabelsFactory.newAxisLabels(dates));
		chart.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(0, 100));
		chart.addYAxisLabels(score);
		chart.addXAxisLabels(year);
		chart.setTitle("("+months[month]+")", BLACK, 14);
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
	
	@RequestMapping(value="/lineChartMonth/{id}")
	public ModelAndView updateLineChartByMonth(@PathVariable("id") Integer id, ModelMap model) throws ParseException{
		LineChart lineChart = createLineChart(id);
		model.put("rrLineChart", lineChart.toURLString());
		return new ModelAndView("redirect:/raglanroad.html");
	}

	private LineChart createLineChart(Integer month) throws ParseException {
		List<Result> temps =  resultsService.getResults();
		int count=0;
		for(Result r: temps){
			if(r.getDate().getMonth() == month)count++;
		}

		List<Result> results = getRaglanRoadResults(temps);
		Map<Date, Double> dailyReturns = getDailyReturns(results);
		Map<Date, Double> orderedReturns = new TreeMap<Date, Double>(dailyReturns);
		List<String> dates = new ArrayList<String>();    
		List<Double> returns = new ArrayList<Double>();
		Double monthlyReturn = 0.0;

		if(count!=0){
			for (Map.Entry<Date, Double> entry : orderedReturns.entrySet())
			{
				if(entry.getKey().getMonth() == month){
					Integer temp = entry.getKey().getDate();
					String date = Integer.toString(temp);
					dates.add(date);
					returns.add(entry.getValue());
					monthlyReturn+=entry.getValue();
				}
			}
		}
		
		final Line line = Plots.newLine(DataUtil.scaleWithinRange(-300,200,returns));
		line.setColor(BLACK);
		final LineChart chart = GCharts.newLineChart(line);
		chart.setSize(650, 450);
		chart.setGrid(500, 20, 20, 0);
		AxisStyle axisStyle = AxisStyle.newAxisStyle(BLACK, 13, AxisTextAlignment.CENTER);
		AxisLabels amount = AxisLabelsFactory.newAxisLabels("€", 50.0);
		amount.setAxisStyle(axisStyle);
		AxisLabels Day = AxisLabelsFactory.newAxisLabels("Day of the Month", 50.0);
		Day.setAxisStyle(axisStyle);

		chart.setTitle("€"+monthlyReturn+"|(per €100 invested in "+months[month]+")", BLACK, 14);
		chart.addXAxisLabels(AxisLabelsFactory.newAxisLabels(dates));
		chart.addYAxisLabels(AxisLabelsFactory.newAxisLabels("-300", "-280", "-260", "-240", "-220","-200", "-180", "-160", "-140", "-120", "-100", "-80", "-60", "-40", "-20", "0", "20", "40", "60", "80", "100", "120", "140", "160", "180", "200"));
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
				Slice slice = Slice.newSlice(percent, colours.get(index), avg, object.getHorseName());
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
				tempRunner.setSide(runner.getOrders().get(0).getSide());
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
		return user.getAccount().getRaglanReturns();
	}

	private String getDateUserInvested(){
		User u = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		User user = userService.getUser(u.getUserId());
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
