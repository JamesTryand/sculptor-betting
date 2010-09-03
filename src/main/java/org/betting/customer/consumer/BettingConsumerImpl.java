package org.betting.customer.consumer;

import static org.fornax.cartridges.sculptor.framework.event.DynamicMethodDispatcher.dispatch;

import org.betting.customer.domain.Customer;
import org.betting.customer.domain.CustomerBet;
import org.betting.customer.exception.CustomerBetNotFoundException;
import org.betting.customer.exception.CustomerNotFoundException;
import org.betting.engine.domain.Bet;
import org.betting.engine.eventapi.BetPlaced;
import org.fornax.cartridges.sculptor.framework.event.Event;
import org.springframework.stereotype.Component;

/**
 * Implementation of BettingConsumer.
 */
@Component("bettingConsumer")
public class BettingConsumerImpl extends BettingConsumerImplBase {
    public BettingConsumerImpl() {
    }

    public void receive(Event event) {
        dispatch(this, event, "handle");
    }

    public void handle(BetPlaced betPlaced) {
        System.out.println("### Consumed betPlaced:" + betPlaced);
        Bet bet = betPlaced.getBet();
        String customerId = bet.getCustomerId();

        String customerName = tryGetCustomerName(customerId);

        CustomerBet customerBet;
        try {
            customerBet = getCustomerBetRepository().findByKey(customerId);
            customerBet.setCustomerName(customerName);
            customerBet.setTotalAmount(customerBet.getTotalAmount() + bet.getAmount());
        } catch (CustomerBetNotFoundException e) {
            // first bet for this customer
            customerBet = new CustomerBet(customerId, customerName, bet.getAmount());
        }

        getCustomerBetRepository().save(customerBet);
    }

    private String tryGetCustomerName(String customerId) {
        String customerName = null;
        try {
            Customer customer = getCustomerRepository().findByKey(customerId);
            customerName = customer.getCustomerName();
        } catch (CustomerNotFoundException e) {
            // ok
        }
        return customerName;
    }
}
