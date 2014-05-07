/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.twitter.automation.testcases;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;
import org.mule.twitter.automation.RegressionTests;
import org.mule.twitter.automation.SmokeTests;
import org.mule.twitter.automation.TwitterTestParent;
import twitter4j.Status;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DestroyStatusTestCases extends TwitterTestParent {
	
    @Before
    public void setUp() throws Exception {
    	initializeTestRunMessage("aRandomStatus");
        upsertOnTestRunMessage("statusId", ((Status) runFlowAndGetPayload("update-status")).getId());
        
    }
    
    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testDestroyStatus() {
    	try {
    		
    		Status destroyedStatus = runFlowAndGetPayload("destroy-status");
    		
        	Long expectedStatusId = getTestRunMessageValue("statusId");
	        Long actualStatusId = destroyedStatus.getId();
			
			String expectedStatusText = getTestRunMessageValue("status");
			String actualStatusText = destroyedStatus.getText();
			
	        assertEquals(expectedStatusId, actualStatusId);
	        assertEquals(expectedStatusText, actualStatusText);  

		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}     

    }

}