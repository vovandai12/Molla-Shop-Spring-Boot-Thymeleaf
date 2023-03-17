package com.example.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.OrderDetailService;
import com.example.service.OrderService;

@Controller
@RequestMapping(value = "statistics")
public class StatisticsController {

	@Autowired
	OrderDetailService orderDetailService;
	
	@Autowired
	OrderService orderService;

	@GetMapping(value = "/revenue")
	public String revenue(Model model) {
		model.addAttribute("years", orderService.getYearOrder());
		return "admin/statistics/revenue-statistics";
	}

	@GetMapping(value = "/like")
	public String like() {
		return "admin/statistics/like-statistics";
	}

	@GetMapping(value = "/view")
	public String view() {
		return "admin/statistics/view-statistics";
	}

	@GetMapping(value = "/revenue/revenue-year/{year}")
	public ResponseEntity<List<Object[]>> revenueStatisticsYear(@PathVariable(name = "year") int year) {
		List<Object[]> list = orderDetailService.statisticsRevenueMonthByYear(year);
		return new ResponseEntity<List<Object[]>>(list, HttpStatus.OK);
	}
}
