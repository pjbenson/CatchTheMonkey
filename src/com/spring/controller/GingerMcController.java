package com.spring.controller;

import static com.googlecode.charts4j.Color.ALICEBLUE;
import static com.googlecode.charts4j.Color.BLACK;
import static com.googlecode.charts4j.Color.LAVENDER;
import static com.googlecode.charts4j.Color.WHITE;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.spring.test.UserUIObject;

@Controller
@SessionAttributes({"strategy", "order", "runners"})
public class GingerMcController {
	
	@Autowired
	private ResultsService resultsService;
	@Autowired
	private StrategyService strategyService;
	@Autowired
	private RunnerService runnerService;
	@Autowired
	private MarketCatalogueService marketCatalogueService;
	
	@RequestMapping(value="gingermc", method = RequestMethod.GET)
	public ModelAndView showGingerMc(ModelMap model){
		Strategy gingermc = strategyService.getStrategy(2);
		List<UserUIObject> orderData = getDataForRunnerTable();
		LineChart lineChart = createLineChart();
		PieChart pieChart = createPieChart();
		Double totalReturnForUser = getUserPercentageOfTotalReturn();
		Double totalReturnForGingerMc = getTotalReturnForStrategy();
		String signUpDate = getDateUserInvested();
		
		model.put("list", orderData);
		model.put("strategy", gingermc);
		model.addAttribute("pieChart", pieChart.toURLString());
		model.addAttribute("signUpDate", signUpDate);
		model.addAttribute("gingerMcSubscribers", gingermc.getAccounts().size());
		model.addAttribute("totalReturn", totalReturnForGingerMc);
		model.addAttribute("totalUserReturn", totalReturnForUser);
		model.addAttribute("lineChart", lineChart.toURLString());
		return new ModelAndView("gingermc");
	}
	
	//TODO change data to match GingerMc strategy
	private LineChart createLineChart() {
		List<Result> temps =  resultsService.getResults();
		List<Result> results = getGingerMcResults(temps);
		Map<Date, Double> dailyReturns = getDailyReturns(results);
		Map<Date, Double> orderedReturns = new TreeMap<Date, Double>(dailyReturns);
		List<String> dates = new ArrayList<String>();    
		List<Double> returns = new ArrayList<Double>();
		
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
			//TODO change to 2 for GingerMc
			if(res.getStrategyId() == 1){
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
				System.out.println(runner);
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
	
	private Double getUserPercentageOfTotalReturn(){
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		if(user == null || user.getAccount().getRaglanReturns() == null)return 0.0;
		
		return user.getAccount().getGingerReturns();
	}
	
	private String getDateUserInvested(){
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
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
