package org.betting.engine.serviceapi;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.betting.engine.domain.Bet;
import org.betting.engine.mapper.BetMapper;
import org.fornax.cartridges.sculptor.framework.accessimpl.mongodb.DbManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Spring based test with MongoDB.
 */
@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
public class BettingEngineTest extends AbstractJUnit4SpringContextTests implements BettingEngineTestBase {
    @Autowired
    private DbManager dbManager;
    @Autowired
    private BettingEngine bettingEngine;

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

    private int countRowsInDBCollection(String name) {
        return (int) dbManager.getDBCollection(name).getCount();
    }

    @Override
    @Test
    public void testPlaceBet() throws Exception {
        Bet bet = new Bet("abc", "1111", 10.0);
        bettingEngine.placeBet(bet);
        assertEquals(1, countRowsInDBCollection(BetMapper.getInstance().getDBCollectionName()));
    }
}
