/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.twitter.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Locale;

import org.junit.After;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.twitter.automation.TwitterTestStatus;

import twitter4j.Status;


public class UpdateStatusTestCases extends TwitterTestParent {
	
    @After
    public void tearDown() {
    	
    	try {
    		
    		TwitterTestStatus testTweet = (TwitterTestStatus) testObjects.get("testTweet");
    		
        	flow = lookupMessageProcessor("destroy-status");
        	flow.process(getTestEvent(testTweet.getId()));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
   	
    }
    
    @Category({SmokeTests.class, SanityTests.class, RegressionTests.class})
	@Test
    public void testUpdateStatus() {
    	
    	testObjects = new HashMap<String,Object>();
    	
    	TwitterTestStatus aTweet = (TwitterTestStatus) context.getBean("aStatus");
    	
    	try {
    		
        	flow = lookupMessageProcessor("update-status");
        	response = flow.process(getTestEvent(aTweet.getText()));
        	
			String expectedStatusText = aTweet.getText();
			String actualStatusText = ((Status) response.getMessage().getPayload()).getText();
			
			String statusTextErrorMessage = context.getMessage("status.text.noMatch", null, Locale.getDefault());
			
	        assertEquals(statusTextErrorMessage, expectedStatusText, actualStatusText); 
        	
        	aTweet.setId(((Status) response.getMessage().getPayload()).getId());
        	testObjects.put("testTweet", aTweet);
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
    	 
    }
	
}