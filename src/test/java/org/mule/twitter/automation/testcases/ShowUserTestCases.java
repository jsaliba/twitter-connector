/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.twitter.automation.testcases;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;
import org.mule.twitter.automation.RegressionTests;
import org.mule.twitter.automation.SmokeTests;
import org.mule.twitter.automation.TwitterTestParent;
import twitter4j.User;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;



public class ShowUserTestCases extends TwitterTestParent {
     
    @Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testShowUser() {
    	
    	Map<String,Object> sandbox = getBeanFromContext("sandbox");
    	
		try {
	        User user = runFlowAndGetPayload("show-user");
	        
	        assertEquals(sandbox.get("userId").toString(), String.valueOf(user.getId())); 
	        assertEquals(sandbox.get("userName").toString(), String.valueOf(user.getName())); 
	        assertEquals(sandbox.get("userScreenName").toString(), String.valueOf(user.getScreenName())); 

		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
     
	}

}