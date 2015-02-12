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

import static org.junit.Assert.*;

public class GetMentionsTestCases extends TwitterTestParent {

    private Status firstMention;

    @Before
    public void setUp() throws Exception {
        firstMention = runFlowAndGetPayload("update-status", "aRandomMention");
    }

    @After
    public void tearDown() throws Exception {
        upsertOnTestRunMessage("statusId", firstMention.getId());
        runFlowAndGetPayload("destroy-status");

    }

    @Category({RegressionTests.class})
    @Test
    public void testGetMentionsDefaultValues() {
        Long expectedStatusId = firstMention.getId();

        try {
            ResponseList<Status> mentions = runFlowAndGetPayload("get-mentions-default-values");

            assertTrue(TwitterTestUtils.isStatusIdOnTimeline(mentions, expectedStatusId));
            assertEquals(firstMention.getText(), TwitterTestUtils.getStatusTextOnTimeline(mentions, expectedStatusId));
            assertTrue(mentions.size() <= TwitterTestUtils.TIMELINE_DEFAULT_LENGTH);

        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

    @Category({RegressionTests.class})
    @Test
    public void testGetMentionsParameterized() {
        Long expectedStatusId = firstMention.getId();

        initializeTestRunMessage("getMentionsTestData");

        try {
            ResponseList<Status> mentions = runFlowAndGetPayload("get-mentions-parametrized");

            assertTrue(TwitterTestUtils.isStatusIdOnTimeline(mentions, expectedStatusId));
            assertEquals(firstMention.getText(), TwitterTestUtils.getStatusTextOnTimeline(mentions, expectedStatusId));
            assertTrue(mentions.size() <= Integer.parseInt(getTestRunMessageValue("count").toString()));

        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

}
