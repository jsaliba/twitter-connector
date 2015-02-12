/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.twitter.automation.testcases;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;
import org.mule.modules.twitter.automation.RegressionTests;
import org.mule.modules.twitter.automation.TwitterTestParent;
import twitter4j.Place;
import twitter4j.ResponseList;

import static org.junit.Assert.*;

public class ReverseGeoCodeTestCases extends TwitterTestParent {

    @Category({RegressionTests.class})
    @Test
    public void testReverseGeoCodesByCoordinates() {
        try {
            initializeTestRunMessage("reverseGeoCodesByCoordinatesTestData");
            ResponseList<Place> placesList = runFlowAndGetPayload("reverse-geo-code-by-coordinates");
            assertNotNull(placesList);
            assertEquals(getTestRunMessageValue("name"), placesList.get(0).getName());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }


}
