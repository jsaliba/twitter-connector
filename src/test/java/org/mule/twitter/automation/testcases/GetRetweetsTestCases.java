/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.twitter.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.twitter.automation.TestStatus;
import org.mule.twitter.automation.TwitterSandbox;
import org.mule.twitter.automation.TwitterTestUtils;

import twitter4j.ResponseList;
import twitter4j.Status;



public class GetRetweetsTestCases extends TwitterTestCase {
	
	@Before
    public void setUp() {
    	
    	testObjects = new HashMap<String,Object>();
    			
    	TestStatus aStatus = (TestStatus) context.getBean("aStatusToRetweet");
    	
    	try {
    		
        	flow = lookupFlowConstruct("update-status-aux-sandbox");
        	
        	response = flow.process(getTestEvent(aStatus.getText()));
        	aStatus.setId(((Status) response.getMessage().getPayload()).getId());
        	
        	flow = lookupFlowConstruct("retweet-status");
	        
        	flow.process(getTestEvent(aStatus.getId()));
        	
        	testObjects.put("aStatusToRetweet", aStatus);
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
    	 
    }
    
    @After
    public void tearDown() {
    	
    	try {
    		
    		TestStatus aRetweetedStatus = (TestStatus) testObjects.get("aStatusToRetweet");
    		
        	flow = lookupFlowConstruct("destroy-status-aux-sandbox");
        	
        	flow.process(getTestEvent(aRetweetedStatus.getId()));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
   	
    }
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testGetRetweets() {
		
		TestStatus aRetweet = (TestStatus) testObjects.get("aStatusToRetweet");
		
		try {
			
			flow = lookupFlowConstruct("get-retweets-aux-sandbox");
			response = flow.process(getTestEvent(aRetweet.getId()));

			ResponseList<Status> responseList = (ResponseList<Status>) response.getMessage().getPayload();
			
			Long expectedStatusId = aRetweet.getId();
			String statusIdErrorMessage = context.getMessage("timeline.statusId.notFound", new Object[] {expectedStatusId}, Locale.getDefault());
			
			assertTrue(statusIdErrorMessage, TwitterTestUtils.isStatusIdOnTimeline(responseList, expectedStatusId));
			
			String expectedStatusText = aRetweet.getText();
			String actualStatusText = TwitterTestUtils.getStatusTextOnTimeline(responseList, expectedStatusId);
			
			String statusTextErrorMessage = context.getMessage("status.text.noMatchForId", new Object[] {expectedStatusId}, Locale.getDefault());
			
	        assertEquals(statusTextErrorMessage, expectedStatusText, actualStatusText);
	        
			String responseListLenghtErrorMessage = context.getMessage("timeline.length", new Object[] {TwitterTestUtils.TIMELINE_DEFAULT_LENGTH}, Locale.getDefault());
	        
	        assertTrue(responseListLenghtErrorMessage, responseList.size() <= TwitterTestUtils.TIMELINE_DEFAULT_LENGTH);
	      
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
        
	} 
	
}