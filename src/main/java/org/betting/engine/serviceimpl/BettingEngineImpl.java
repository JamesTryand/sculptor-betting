package org.betting.engine.serviceimpl;

import java.util.Date;

import org.betting.engine.domain.Bet;
import org.betting.engine.eventapi.BetPlaced;
import org.springframework.stereotype.Service;

/**
 * Implementation of BettingEngine.
 */
@Service("bettingService")
public class BettingEngineImpl extends BettingEngineImplBase {
    public BettingEngineImpl() {
    }

    public void placeBet(Bet bet) {
        getBetRepository().save(bet);
        BetPlaced betPlaced = new BetPlaced(new Date(), bet);
        getBettingPublisher().publishEvent(betPlaced);
    }
}
