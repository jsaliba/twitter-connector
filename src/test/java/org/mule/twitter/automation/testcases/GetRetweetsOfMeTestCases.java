/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.twitter.automation.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;
import org.mule.twitter.automation.RegressionTests;
import org.mule.twitter.automation.TwitterTestParent;
import org.mule.twitter.automation.TwitterTestUtils;
import twitter4j.ResponseList;
import twitter4j.Status;

import static org.junit.Assert.*;


public class GetRetweetsOfMeTestCases extends TwitterTestParent {

    private Status firstRetweet;
    private Status secondRetweet;

    @Before
    public void setUp() throws Exception {
        firstRetweet = runFlowAndGetPayload("update-status-aux-sandbox", "aRandomStatus");
        initializeTestRunMessage("statusId", firstRetweet.getId());
        runFlowAndGetPayload("retweet-status");

        secondRetweet = runFlowAndGetPayload("update-status-aux-sandbox", "aRandomStatus");
        initializeTestRunMessage("statusId", secondRetweet.getId());
        runFlowAndGetPayload("retweet-status");

        Thread.sleep(TwitterTestUtils.SETUP_DELAY);

    }

    @After
    public void tearDown() throws Exception {
        upsertOnTestRunMessage("statusId", firstRetweet.getId());
        runFlowAndGetPayload("destroy-status-aux-sandbox");
        upsertOnTestRunMessage("statusId", secondRetweet.getId());
        runFlowAndGetPayload("destroy-status-aux-sandbox");

    }

    @Category({RegressionTests.class})
    @Test
    public void testGetRetweetsOfMeDefaultValues() {
        Long expectedStatusId = firstRetweet.getId();
        try {
            ResponseList<Status> responseList = runFlowAndGetPayload("get-retweets-of-me-default-values");

            assertTrue(TwitterTestUtils.isStatusIdOnTimeline(responseList, expectedStatusId));
            assertEquals(firstRetweet.getText(), TwitterTestUtils.getStatusTextOnTimeline(responseList, expectedStatusId));
            assertTrue(responseList.size() <= TwitterTestUtils.TIMELINE_DEFAULT_LENGTH);

        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

    @Category({RegressionTests.class})
    @Test
    public void testGetRetweetsOfMeParameterized() {
        Long expectedStatusId = secondRetweet.getId();

        initializeTestRunMessage("getRetweetsOfMeTestData");
        upsertOnTestRunMessage("sinceId", firstRetweet.getId());

        try {
            ResponseList<Status> responseList = runFlowAndGetPayload("get-retweets-of-me-parametrized");

            assertTrue(TwitterTestUtils.isStatusIdOnTimeline(responseList, expectedStatusId));
            assertEquals(secondRetweet.getText(), TwitterTestUtils.getStatusTextOnTimeline(responseList, expectedStatusId));
            assertTrue(responseList.size() <= Integer.parseInt(getTestRunMessageValue("count").toString()));

        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

}