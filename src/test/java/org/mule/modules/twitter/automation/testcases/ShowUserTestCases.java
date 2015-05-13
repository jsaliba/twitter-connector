/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.twitter.automation.testcases;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;
import org.mule.modules.twitter.automation.RegressionTests;
import org.mule.modules.twitter.automation.SmokeTests;
import org.mule.modules.twitter.automation.TwitterTestParent;
import twitter4j.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class ShowUserTestCases extends TwitterTestParent {

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("sandboxTestData");
    }

    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testShowUser() {
        try {
            User user = runFlowAndGetPayload("show-user");
            assertEquals(getTestRunMessageValue("userId"), String.valueOf(user.getId()));
            assertEquals(getTestRunMessageValue("userName"), String.valueOf(user.getName()));
            assertEquals(getTestRunMessageValue("userScreenName"), String.valueOf(user.getScreenName()));
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }
}