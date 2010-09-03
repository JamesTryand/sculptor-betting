package org.betting.customer.serviceapi;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.betting.customer.domain.CustomerBet;
import org.betting.engine.domain.Bet;
import org.betting.engine.eventapi.BetPlaced;
import org.fornax.cartridges.sculptor.framework.accessimpl.mongodb.DbManager;
import org.fornax.cartridges.sculptor.framework.event.EventBus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.test.context.ContextConfiguration;

/**
 * Spring based test with MongoDB.
 */
@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
public class BettingQueryServiceTest extends AbstractDependencyInjectionSpringContextTests implements
        BettingQueryServiceTestBase {
    @Autowired
    private DbManager dbManager;
    @Autowired
    private BettingQueryService bettingQueryService;

    @Autowired
    @Qualifier("eventBus")
    private EventBus eventBus;

    @Before
    public void initTestData() {
    }

    @Before
    public void initDbManagerThreadInstance() throws Exception {
        // to be able to do lazy loading of associations inside test class
        DbManager.setThreadInstance(dbManager);
    }

    @After
    public void dropDatabase() {
        Set<String> names = dbManager.getDB().getCollectionNames();
        for (String each : names) {
            if (!each.startsWith("system")) {
                dbManager.getDB().getCollection(each).drop();
            }
        }

        // dbManager.getDB().dropDatabase();
    }

    @Test
    public void testGetHighStakes() throws Exception {
        Bet bet1 = new Bet("abc", "1234", 100.0);
        BetPlaced event1 = new BetPlaced(new Date(), bet1);
        eventBus.publish("jms:topic:bet", event1);
        Bet bet2 = new Bet("def", "1234", 30.0);
        BetPlaced event2 = new BetPlaced(new Date(), bet2);
        eventBus.publish("jms:topic:bet", event2);

        Thread.sleep(200);

        List<CustomerBet> highStakes = bettingQueryService.getHighStakes(90.0);
        assertEquals(1, highStakes.size());
        assertEquals("1234", highStakes.get(0).getCustomerId());
        assertEquals(130, highStakes.get(0).getTotalAmount(), 0.01);
    }
}
