package org.betting.customer.serviceapi;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.betting.customer.domain.CustomerStatistics;
import org.betting.engine.domain.Bet;
import org.betting.engine.domain.BetPlaced;
import org.fornax.cartridges.sculptor.framework.accessimpl.mongodb.DbManager;
import org.fornax.cartridges.sculptor.framework.event.EventBus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Spring based test with MongoDB.
 */
@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
public class BettingQueryServiceTest extends AbstractJUnit4SpringContextTests implements BettingQueryServiceTestBase {
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

    @Override
    @Test
    public void testGetHighBetters() throws Exception {
        Bet bet1 = new Bet("abc", "1234", 95.0);
        BetPlaced event1 = new BetPlaced(new Date(), bet1);
        eventBus.publish("jms:topic:bet", event1);
        Bet bet2 = new Bet("def", "1234", 105.0);
        BetPlaced event2 = new BetPlaced(new Date(), bet2);
        eventBus.publish("jms:topic:bet", event2);
        Bet bet3 = new Bet("abc", "9876", 30.0);
        BetPlaced event3 = new BetPlaced(new Date(), bet3);
        eventBus.publish("jms:topic:bet", event3);

        Thread.sleep(200);

        List<CustomerStatistics> high = bettingQueryService.getHighBetters(99.0);
        assertEquals(1, high.size());
        assertEquals("1234", high.get(0).getCustomerId());
        assertEquals(100, high.get(0).getAverageAmount(), 0.01);
        assertEquals(2, high.get(0).getNumberOfBets());

    }
}
