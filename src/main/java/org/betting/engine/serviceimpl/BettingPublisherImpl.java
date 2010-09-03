package org.betting.engine.serviceimpl;

import org.betting.engine.eventapi.BetPlaced;
import org.betting.engine.serviceimpl.BettingPublisherImplBase;
import org.fornax.cartridges.sculptor.framework.event.annotation.Publish;
import org.springframework.stereotype.Service;

/**
 * Implementation of BettingPublisher.
 */
@Service("bettingPublisher")
public class BettingPublisherImpl extends BettingPublisherImplBase {
    public BettingPublisherImpl() {
    }

    @Publish(topic = "jms:topic:bet")
    public void publishEvent(BetPlaced betEvent) {
        // betEvent will be published
    }
}
