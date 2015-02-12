/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.twitter.automation.testcases;

import org.junit.After;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;
import org.mule.modules.twitter.automation.RegressionTests;
import org.mule.modules.twitter.automation.SmokeTests;
import org.mule.modules.twitter.automation.TwitterTestParent;
import twitter4j.Status;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class UpdateStatusTestCases extends TwitterTestParent {

    private Status status;

    @After
    public void tearDown() throws Exception {
        if (status != null) {
            upsertOnTestRunMessage("statusId", status.getId());
            runFlowAndGetPayload("destroy-status");
        }
    }

    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testUpdateStatus() {
        try {

            initializeTestRunMessage("aRandomStatus");

            status = runFlowAndGetPayload("update-status");

            assertEquals(getTestRunMessageValue("status"), status.getText());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

}