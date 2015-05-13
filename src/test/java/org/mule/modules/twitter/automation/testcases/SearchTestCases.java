/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.twitter.automation.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;
import org.mule.modules.twitter.automation.RegressionTests;
import org.mule.modules.twitter.automation.TwitterTestParent;
import twitter4j.QueryResult;
import twitter4j.Status;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class SearchTestCases extends TwitterTestParent {


    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("aTweetToQueryForTestData");
        upsertOnTestRunMessage("statusId", ((Status) runFlowAndGetPayload("update-status")).getId());
        upsertBeanFromContextOnTestRunMessage("searchTestData");
    }

    @Category({RegressionTests.class})
    @Test
    public void testSearch() {
        try {
            QueryResult responseList = runFlowAndGetPayload("search-default-values");
            List<Status> tweets = responseList.getTweets();
            assertTrue(tweets.size() != 0);

        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

    @After
    public void tearDown() throws Exception {
        runFlowAndGetPayload("destroy-status");
    }

}