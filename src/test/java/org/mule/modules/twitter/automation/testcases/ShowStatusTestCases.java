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
import twitter4j.Status;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class ShowStatusTestCases extends TwitterTestParent {

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("randomStatusTestData");
        long id = ((Status) runFlowAndGetPayload("update-status")).getId();
        upsertOnTestRunMessage("id", id);
        upsertOnTestRunMessage("statusId", id);
    }

    @After
    public void tearDown() throws Exception {
        runFlowAndGetPayload("destroy-status");
    }

    @Category({RegressionTests.class})
    @Test
    public void testShowStatus() {
        try {
            Status status = runFlowAndGetPayload("show-status");

            Long expectedStatusId = getTestRunMessageValue("id");
            Long actualStatusId = status.getId();

            String expectedStatusText = getTestRunMessageValue("status");
            String actualStatusText = status.getText();

            assertEquals(expectedStatusId, actualStatusId);
            assertEquals(expectedStatusText, actualStatusText);

        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

}