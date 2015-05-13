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
import org.mule.modules.twitter.automation.testutils.TwitterTestUtils;
import twitter4j.ResponseList;
import twitter4j.Status;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class GetRetweetsTestCases extends TwitterTestParent {

    private Status aRetweet;

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("randomStatusTestData");
        aRetweet = runFlowAndGetPayload("update-status-aux-sandbox");
        upsertOnTestRunMessage("statusId", aRetweet.getId());
        runFlowAndGetPayload("retweet-status");
    }

    @Category({RegressionTests.class})
    @Test
    public void testGetRetweets() {
        String aRetweetStatus = aRetweet.getText();

        try {
            ResponseList<Status> responseList = runFlowAndGetPayload("get-retweets-aux-sandbox");
            Long expectedStatusId = TwitterTestUtils.getIdForStatusTextOnResponseList(responseList, aRetweetStatus);

            assertTrue(TwitterTestUtils.isStatusIdOnTimeline(responseList, expectedStatusId));
            assertTrue(TwitterTestUtils.getStatusTextOnTimeline(responseList, expectedStatusId).contains(aRetweetStatus));
            assertTrue(responseList.size() <= TwitterTestUtils.TIMELINE_DEFAULT_LENGTH);

        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

    @After
    public void tearDown() throws Exception {
        upsertOnTestRunMessage("statusId", aRetweet.getId());
        runFlowAndGetPayload("destroy-status-aux-sandbox");

    }

}