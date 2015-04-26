package com.spring.controller;

import static com.googlecode.charts4j.Color.ALICEBLUE;
import static com.googlecode.charts4j.Color.BLACK;
import static com.googlecode.charts4j.Color.BLUEVIOLET;
import static com.googlecode.charts4j.Color.LAVENDER;
import static com.googlecode.charts4j.Color.WHITE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.Slice;
import com.spring.model.Strategy;
import com.spring.model.User;
import com.spring.service.MarketCatalogueService;
import com.spring.service.ResultsService;
import com.spring.service.RunnerService;
import com.spring.service.StrategyService;
import com.spring.service.UserService;
import com.spring.test.UserUIObject;

@Controller
@SessionAttributes({"strategy", "order", "runners", "gmLineChart", "gMcBarChart"})
public class GingerMcController {

	@Autowired
	private ResultsService resultsService;
	@Autowired
	private UserService userService;
	@Autowired
	private StrategyService strategyService;
	@Autowired
	private RunnerService runnerService;
	@Autowired
	private MarketCatalogueService marketCatalogueService;
	private String months[] = {"January", "February", "March", "April",
			"May", "June", "July", "August", "September",
			"October", "November", "December"};


	@RequestMapping(value="gingermc", method = RequestMethod.GET)
	public ModelAndView showGingerMc(ModelMap model){
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		if(user==null)return new ModelAndView("redirect:/loginform.html");
		String lineChart = (String) RequestContextHolder.currentRequestAttributes().getAttribute("gmLineChart", RequestAttributes.SCOPE_SESSION);
		String barChart = (String) RequestContextHolder.currentRequestAttributes().getAttribute("gMcBarChart", RequestAttributes.SCOPE_SESSION);
		if(lineChart==null)model.put("gmLineChart", createLineChart(3).toURLString());
		if(barChart==null)model.put("gMcBarChart", createBarChart(3).toURLString());

		Strategy gingermc = strategyService.getStrategy(2);
		PieChart pieChart = createPieChart();

		model.put("list", getDataForRunnerTable());
		model.put("strategy", gingermc);
		model.addAttribute("pieChart", pieChart.toURLString());
		model.addAttribute("signUpDate", getDateUserInvested());
		model.addAttribute("gingerMcSubscribers", gingermc.getAccounts().size());
		model.addAttribute("totalReturn", getTotalReturnForStrategy());
		model.addAttribute("totalUserReturn", getUserPercentageOfTotalReturn());
		return new ModelAndView("gingermc");
	}

	@RequestMapping(value="/gMcBarChartMonth/{id}")
	public ModelAndView updateBarChart(@PathVariable("id") Integer id, ModelMap model){
		BarChart barChart = createBarChart(id);
		model.put("gMcBarChart", barChart.toURLString());
		return new ModelAndView("redirect:/gingermc.html");
	}
	
	@RequestMapping(value = "/addGmcCash", method = RequestMethod.POST)
	public ModelAndView addCashToPool(@RequestParam("amount") Double amount, ModelMap model){
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		Strategy gingerMc = strategyService.getStrategy(2);
		if(amount>user.getAccount().getBalance()){
			return new ModelAndView("redirect:/gingermc.html");
		}else{
			gingerMc.addToPool(amount);
			strategyService.addAccountToStrategy(gingerMc);
			user.getAccount().addToGingermc(amount);
			user.getAccount().setBalance(user.getAccount().getBalance() - amount);
			userService.updateBalance(user);
			model.put("user", user);
			return new ModelAndView("redirect:/gingermc.html");
		}
	}

	private BarChart createBarChart(Integer month){
		User u = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		User user = userService.getUser(u.getUserId());
		List<Result> gingerMcResults = getGingerMcResults(resultsService.getResults());
		Map<Date, Double> dailyReturns = getDailyReturns(gingerMcResults);
		Map<Date, Double> orderedReturns = new TreeMap<Date, Double>(dailyReturns);
		List<String> dates = new ArrayList<String>();
		List<Double> returns = new ArrayList<Double>();

		for (Map.Entry<Date, Double> entry : orderedReturns.entrySet())
		{
			if(entry.getKey().after(user.getAccount().getGingerRegisterDate())  && entry.getKey().getMonth() == month){
				Double profitPercentage = (user.getAccount().getGingermc()/10)*entry.getValue();
				Integer temp = entry.getKey().getDate();
				String date = Integer.toString(temp);
				dates.add(date);
				if(profitPercentage > 100){
					returns.add(100.);
				}else if(profitPercentage < 0){
					returns.add(0.0);
				}else{
					returns.add(profitPercentage);
				}
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

	@RequestMapping(value="/updateGingerMcLineChart/{id}")
	public ModelAndView updateLineChartByMonth(@PathVariable("id") Integer id, ModelMap model) throws ParseException{
		LineChart lineChart = createLineChart(id);
		model.addAttribute("gmLineChart", lineChart.toURLString());
		return new ModelAndView("redirect:/gingermc.html");
	}

	private LineChart createLineChart(Integer month) {
		List<Result> temps =  resultsService.getResults();
		List<Result> results = getGingerMcResults(temps);
		Map<Date, Double> dailyReturns = getDailyReturns(results);
		Map<Date, Double> orderedReturns = new TreeMap<Date, Double>(dailyReturns);
		List<String> dates = new ArrayList<String>();    
		List<Double> returns = new ArrayList<Double>();
		Double monthlyReturn = 0.0;

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

		final Line line = Plots.newLine(DataUtil.scaleWithinRange(-50,40,returns), BLUEVIOLET);
		line.setColor(BLACK);
		final LineChart chart = GCharts.newLineChart(line);
		chart.setSize(650, 450);
		chart.setGrid(100, 10, 3, 2);
		chart.setBackgroundFill(Fills.newSolidFill(ALICEBLUE));
		AxisStyle axisStyle = AxisStyle.newAxisStyle(BLACK, 13, AxisTextAlignment.CENTER);
		AxisLabels amount = AxisLabelsFactory.newAxisLabels("€", 50.0);
		amount.setAxisStyle(axisStyle);
		AxisLabels Day = AxisLabelsFactory.newAxisLabels("Day of the Month", 50.0);
		Day.setAxisStyle(axisStyle);

		chart.setTitle("€"+monthlyReturn+"|(per €100 invested in "+months[month]+")", BLACK, 14);
		chart.addXAxisLabels(AxisLabelsFactory.newAxisLabels(dates));
		chart.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(-50, 40));
		chart.setBackgroundFill(Fills.newSolidFill(ALICEBLUE));
		LinearGradientFill fill = Fills.newLinearGradientFill(0, LAVENDER, 10);
		fill.addColorAndOffset(WHITE, 0);
		chart.setAreaFill(fill);    
		chart.addYAxisLabels(amount);
		chart.addXAxisLabels(Day);

		return chart;
	}

	private PieChart createPieChart() {
		List<UserUIObject> orderData = getDataForRunnerTable();
		List<Slice> slices = new ArrayList<Slice>();
		List<Color> colours = getColours();
		PieChart pieChart = GCharts.newPieChart(slices);
		int index=0;

		for(UserUIObject object: orderData){
			Double d = new Double(object.getExpWinnings());
			int percent = d.intValue();
			String avg = Integer.toString(percent);
			Slice slice = Slice.newSlice(percent, colours.get(index), avg, object.getRaceName());
			slices.add(slice);
			index++;
			pieChart.setTitle("Average return for today's race", Color.BLACK, 15);
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

	private List<Result> getGingerMcResults(List<Result> results) {
		List<Result> temps = new ArrayList<Result>();
		for(Result res: results){
			if(res.getStrategyId() == 2){
				temps.add(res);
			}

		}
		return temps;
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

	private List<UserUIObject> getDataForRunnerTable(){
		List<UserUIObject> list = new ArrayList<UserUIObject>();
		List<MarketCatalogue> markets = marketCatalogueService.getMarketCatalogueList();
		ModelMap model = new ModelMap();
		Date today = new Date();
		int index = 0;

		for(MarketCatalogue mc: markets){
			if(mc.getMarketTime().getDate() == today.getDate() && mc.getMarketTime().getMonth() == today.getMonth() && mc.getStrategyId()==2){
				if(index>0){
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

	private Double getUserPercentageOfTotalReturn(){
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		if(user == null || user.getAccount().getGingerReturns() == null)return 0.0;
		return user.getAccount().getGingerReturns();
	}

	private String getDateUserInvested(){
		User u = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		User user = userService.getUser(u.getUserId());
		if(user == null)return null;
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
		String date = fmt.format(user.getAccount().getGingerRegisterDate());
		return date;
	}

	private Double getTotalReturnForStrategy(){
		List<Result> results =  resultsService.getResults();
		Double total = 0.0;

		for(Result result: results){
			if(result.getStrategyId()==2)
				total = total + result.getAmount();
		}
		return total;
	}

}
