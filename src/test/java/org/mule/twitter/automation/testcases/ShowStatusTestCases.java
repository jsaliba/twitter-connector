/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.twitter.automation.testcases;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleException;
import org.mule.twitter.automation.TestStatus;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import twitter4j.Status;



public class ShowStatusTestCases extends TwitterTestCase {
    
	@Before
    public void setUp() {
    	
    	testObjects = new HashMap<String,Object>();
    			
    	TestStatus aTweet = (TestStatus) context.getBean("aStatus");
    	
    	try {
    		
        	flow = lookupFlowConstruct("update-status");
        	response = flow.process(getTestEvent(aTweet.getText()));
        	aTweet.setId(((Status) response.getMessage().getPayload()).getId());
        	testObjects.put("testTweet", aTweet);
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
    	 
    }
    
    @After
    public void tearDown() {
    	
    	try {
    		
    		TestStatus testTweet = (TestStatus) testObjects.get("testTweet");
    		
        	flow = lookupFlowConstruct("destroy-status");
        	flow.process(getTestEvent(testTweet.getId()));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
   	
    }
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testShowStatus() {
    	
    	TestStatus testTweet = (TestStatus) testObjects.get("testTweet");
		
		try {
			
			flow = lookupFlowConstruct("show-status");
	        response = flow.process(getTestEvent(testTweet.getId()));
	        	
	        Long expectedStatusId = testTweet.getId();
	        Long actualStatusId = ((Status) response.getMessage().getPayload()).getId();
			
			String expectedStatusText = testTweet.getText();
			String actualStatusText = ((Status) response.getMessage().getPayload()).getText();
			
			String statusIdErrorMessage = context.getMessage("status.id.noMatch", null, Locale.getDefault());
			String statusTextErrorMessage = context.getMessage("status.text.noMatchForId", new Object[] {expectedStatusId}, Locale.getDefault());
			
	        assertEquals(statusIdErrorMessage, expectedStatusId, actualStatusId);
	        assertEquals(statusTextErrorMessage, expectedStatusText, actualStatusText);  
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}

}