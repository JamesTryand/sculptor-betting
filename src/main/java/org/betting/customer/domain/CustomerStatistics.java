package org.betting.customer.domain;

import org.betting.engine.domain.Bet;

/**
 * Entity representing CustomerStatistics.
 * <p>
 * This class is responsible for the domain object related business logic for
 * CustomerStatistics. Properties and associations are implemented in the
 * generated base class
 * {@link org.betting.customer.domain.CustomerStatisticsBase}.
 */
public class CustomerStatistics extends CustomerStatisticsBase {
    private static final long serialVersionUID = 1L;

    protected CustomerStatistics() {
    }

    public CustomerStatistics(String customerId, String customerName) {
        super(customerId, customerName);
    }

    public void addBet(Bet bet) {
        int n = getNumberOfBets();
        double newAverage = ((getAverageAmount() * n) + bet.getAmount()) / (n + 1);
        setAverageAmount(newAverage);

        setNumberOfBets(n + 1);

    }
}
