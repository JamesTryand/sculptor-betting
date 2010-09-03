package org.betting.engine.web;

import org.betting.engine.domain.Bet;
import org.betting.engine.serviceapi.BettingEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BetController {

    @Autowired
    private BettingEngine bettingEngine;

    @InitBinder
    protected void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }

    @RequestMapping(value = "/bet/form", method = RequestMethod.GET)
    public String createForm(org.springframework.ui.ModelMap modelMap) {
        modelMap.addAttribute("bet", new BetFormData());
        return "bet/create";
    }

    @RequestMapping(value = "/bet", method = RequestMethod.POST)
    public String create(@ModelAttribute("bet") BetFormData betForm, BindingResult result) {
        if (betForm == null) {
            throw new IllegalArgumentException("A bet is required");
        }
        if (result.hasErrors()) {
            return "bet/create";
        }

        Bet bet = new Bet(betForm.getBetOfferId(), betForm.getCustomerId(), betForm.getAmount());

        bettingEngine.placeBet(bet);

        return "redirect:/rest/front";
    }

}
