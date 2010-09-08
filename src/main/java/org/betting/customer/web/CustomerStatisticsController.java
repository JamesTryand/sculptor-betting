package org.betting.customer.web;

import java.util.List;

import org.betting.customer.domain.CustomerStatistics;
import org.betting.customer.serviceapi.BettingQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerStatisticsController {

    @Autowired
    private BettingQueryService bettingQueryService;

    @RequestMapping(value = "/customerstat", method = RequestMethod.GET)
    public String getHighBetters(@RequestParam("limit") Double limit, ModelMap modelMap) {
        List<CustomerStatistics> stats = bettingQueryService.getHighBetters(limit);

        modelMap.addAttribute("stats", stats);
        return "customer/high";
    }

}
