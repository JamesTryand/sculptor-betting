package org.betting.customer.web;

import java.util.List;

import org.betting.customer.domain.CustomerBet;
import org.betting.customer.serviceapi.BettingQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerBetController {

    @Autowired
    private BettingQueryService bettingQueryService;

    @RequestMapping(value = "/customerbet", method = RequestMethod.GET)
    public String getHighStakes(@RequestParam("limit") Double limit, ModelMap modelMap) {
        List<CustomerBet> highStakes = bettingQueryService.getHighStakes(limit);

        modelMap.addAttribute("highStakes", highStakes);
        return "customer/highStakes";
    }

}
