package com.spring.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import static com.googlecode.charts4j.Color.RED;

import com.betfair.entities.MarketCatalogue;
import com.betfair.entities.Order;
import com.betfair.entities.Runner;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Line;
import com.googlecode.charts4j.LineChart;
import com.googlecode.charts4j.LineStyle;
import com.googlecode.charts4j.PieChart;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.Slice;
import com.spring.model.Account;
import com.spring.model.Strategy;
import com.spring.model.User;
import com.spring.service.AccountService;
import com.spring.service.MarketCatalogueService;
import com.spring.service.OrderService;
import com.spring.service.RunnerService;
import com.spring.service.StrategyService;
import com.spring.service.UserService;

@Controller
@SessionAttributes({"strategy","order", "runner"})
public class StrategyController {
	
	@Autowired
	private UserService userService;
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

	@RequestMapping(value="strategyChoice", method = RequestMethod.GET)
	public ModelAndView showStrategy(ModelMap model) {
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		if(user == null){
			return new ModelAndView("strategyChoice");
		}
		else{
			User loadedUser = userService.getUser(user.getUserId());
			Account acc = accountService.getAccount(user.getAccount().getId());
			Strategy strategy = new Strategy();
			if(acc.getStrategies().isEmpty()){
				model.addAttribute("strategy", strategy);
				return new ModelAndView("strategyChoice");
			}
			else{				
				for(Strategy strat: loadedUser.getAccount().getStrategies()){
					if(strat.getName().equals("RAGLAN_ROAD")){
						model.addAttribute("raglanroad", loadedUser.getAccount().getRaglanroad());
					}
					if(strat.getName().equals("GINGER_MAC")){
						model.addAttribute("gingermc", loadedUser.getAccount().getGingermc());
					}
					if(strat.getName().equals("LUCAYAN")){
						model.addAttribute("lucayan", loadedUser.getAccount().getLucayan());
					}
				}
				return new ModelAndView("strategyChoice");
			}
		}
	}

	@RequestMapping(value = "/strategy1", method = RequestMethod.POST)
	public ModelAndView chooseStrategy1(@ModelAttribute("strategy") Strategy strategy, ModelMap model) throws ParseException {
		ModelAndView modelAndView = new ModelAndView();
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		if(user.getAccount().getRaglanRegisterDate() != null){
			return new ModelAndView("redirect:/raglanroad.html");
		}
		Account acc = accountService.getAccount(user.getAccount().getId());
		acc.addToRaglanReturns(0.0);
		Strategy strat = strategyService.getStrategy(1);
		acc.setRaglanRegisterDate(getCurrentDate());
		acc.addStrategy(strat);
		strat.addAccount(acc);
		
		accountService.addStrategyToAccount(acc);
		strategyService.addAccountToStrategy(strat);
		model.put("raglanroad", strategy);
		modelAndView.addObject("strategy", strat);
		return new ModelAndView("redirect:/raglanroad.html");

	}

	@RequestMapping(value = "/strategy2", method = RequestMethod.POST)
	public ModelAndView chooseStrategy2(@ModelAttribute("strategy") Strategy strategy, ModelMap model) throws ParseException {
		ModelAndView modelAndView = new ModelAndView();
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		if(user.getAccount().getGingerRegisterDate() != null){
			return new ModelAndView("redirect:/gingermc.html");
		}
		Account acc = accountService.getAccount(user.getAccount().getId());
		Strategy strat = strategyService.getStrategy(2);
		acc.setGingerRegisterDate(getCurrentDate());
		acc.addStrategy(strat);
		acc.addToGingermc(0.0);
		strat.addAccount(acc);
		accountService.addStrategyToAccount(acc);
		strategyService.addAccountToStrategy(strat);
		model.put("gingermc", strategy);
		modelAndView.addObject("strategy", strat);
		return new ModelAndView("redirect:/gingermc.html");
	}

	@RequestMapping(value = "/strategy3", method = RequestMethod.POST)
	public ModelAndView chooseStrategy3(@ModelAttribute("strategy") Strategy strategy, ModelMap model) throws ParseException {
		ModelAndView modelAndView = new ModelAndView();
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_SESSION);
		if(user.getAccount().getLucayanRegisterDate() != null){
			return new ModelAndView("redirect:/lucayan.html");
		}
		Account acc = accountService.getAccount(user.getAccount().getId());
		Strategy strat = strategyService.getStrategy(3);
		acc.setLucayanRegisterDate(getCurrentDate());
		acc.addStrategy(strat);
		acc.addToLucayan(0.0);
		strat.addAccount(acc);
		accountService.addStrategyToAccount(acc);
		strategyService.addAccountToStrategy(strat);
		model.put("lucayan", strategy);
		modelAndView.addObject("strategy", strat);
		return new ModelAndView("redirect:/lucayan.html");
	}
	
	private Date getCurrentDate() throws ParseException{
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date today = new Date();
		Date todayWithZeroTime =formatter.parse(formatter.format(today));
		return todayWithZeroTime;
	}
}
