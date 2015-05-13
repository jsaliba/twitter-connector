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
import twitter4j.Location;
import twitter4j.ResponseList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


public class GetAvailableTrendsTestCases extends TwitterTestParent {

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getAvailableTrendsTestData");
    }

    @Category({RegressionTests.class, SmokeTests.class})
    @Test
    public void testGetAvailableTrendsDefaultValues() {
        try {
            ResponseList<Location> locations = runFlowAndGetPayload("get-available-trends");
            assertNotNull(locations);

        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }
}