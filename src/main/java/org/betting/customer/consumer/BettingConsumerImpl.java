package org.betting.customer.consumer;

import static org.fornax.cartridges.sculptor.framework.event.DynamicMethodDispatcher.dispatch;

import org.betting.customer.domain.Customer;
import org.betting.customer.domain.CustomerStatistics;
import org.betting.customer.exception.CustomerNotFoundException;
import org.betting.customer.exception.CustomerStatisticsNotFoundException;
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

        CustomerStatistics stat;
        try {
            stat = getCustomerStatisticsRepository().findByKey(customerId);
            stat.setCustomerName(customerName);
        } catch (CustomerStatisticsNotFoundException e) {
            // first bet for this customer
            stat = new CustomerStatistics(customerId, customerName);
        }

        stat.addBet(bet);

        getCustomerStatisticsRepository().save(stat);
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
