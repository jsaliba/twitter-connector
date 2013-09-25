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
import org.mule.twitter.automation.TwitterTestStatus;
import org.mule.twitter.automation.TwitterSandbox;
import org.mule.twitter.automation.TwitterTestUtils;

import twitter4j.ResponseList;
import twitter4j.Status;



public class GetRetweetsOfMeTestCases extends TwitterTestParent {
	
	private TwitterSandbox sandbox;
    
    @Before
    public void setUp() {
    	
    	testObjects = new HashMap<String,Object>();
    			
    	TwitterTestStatus firstTweet = (TwitterTestStatus) context.getBean("firstStatusToRetweet");
    	TwitterTestStatus secondTweet = (TwitterTestStatus) context.getBean("secondStatusToRetweet");
    	
    	try {
    		
        	flow = lookupMessageProcessor("update-status-aux-sandbox");
        	
        	response = flow.process(getTestEvent(firstTweet.getText()));
        	firstTweet.setId(((Status) response.getMessage().getPayload()).getId());
        	
        	response = flow.process(getTestEvent(secondTweet.getText()));
        	secondTweet.setId(((Status) response.getMessage().getPayload()).getId());
        	
        	flow = lookupMessageProcessor("retweet-status");
	        
        	flow.process(getTestEvent(firstTweet.getId()));
        	flow.process(getTestEvent(secondTweet.getId()));
        	
        	testObjects.put("firstRetweetedStatus", firstTweet);
        	testObjects.put("secondRetweetedStatus", secondTweet);
        	
        	Thread.sleep(TwitterTestUtils.SETUP_DELAY);
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
    	 
    }
    
    @After
    public void tearDown() {
    	
    	try {
    		
    		TwitterTestStatus firstRetweetedStatus = (TwitterTestStatus) testObjects.get("firstRetweetedStatus");
    		TwitterTestStatus secondRetweetedStatus = (TwitterTestStatus) testObjects.get("secondRetweetedStatus");
    		
        	flow = lookupMessageProcessor("destroy-status-aux-sandbox");
        	
        	flow.process(getTestEvent(firstRetweetedStatus.getId()));
        	flow.process(getTestEvent(secondRetweetedStatus.getId()));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
   	
    }
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testGetRetweetsOfMeDefaultValues() {
		
		TwitterTestStatus aRetweet = (TwitterTestStatus) testObjects.get("firstRetweetedStatus");
		
		try {
			
			flow = lookupMessageProcessor("get-retweets-of-me-default-values");
			response = flow.process(getTestEvent(null));

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
	
    @Category({RegressionTests.class})
	@Test
	public void testGetRetweetsOfMeParametrized() {
	
		int count = new Integer(2);
		
		TwitterTestStatus firstRetweetedStatus = (TwitterTestStatus) testObjects.get("firstRetweetedStatus");
		TwitterTestStatus secondRetweetedStatus = (TwitterTestStatus) testObjects.get("secondRetweetedStatus");
		
		Map<String,Object> operationParams = new HashMap<String,Object>();
		operationParams.put("count", count);
		operationParams.put("page", new Integer(1));
		operationParams.put("sinceId", firstRetweetedStatus.getId());
		
		try {
			
			flow = lookupMessageProcessor("get-retweets-of-me-parametrized");
			response = flow.process(getTestEvent(operationParams));

			ResponseList<Status> responseList = (ResponseList<Status>) response.getMessage().getPayload();
			
			Long expectedStatusId = secondRetweetedStatus.getId();
			String statusIdErrorMessage = context.getMessage("timeline.statusId.notFound", new Object[] {expectedStatusId}, Locale.getDefault());
			
			assertTrue(statusIdErrorMessage, TwitterTestUtils.isStatusIdOnTimeline(responseList, expectedStatusId));
			
			String expectedStatusText = secondRetweetedStatus.getText();
			String actualStatusText = TwitterTestUtils.getStatusTextOnTimeline(responseList, expectedStatusId);
			
			String statusTextErrorMessage = context.getMessage("status.text.noMatchForId", new Object[] {expectedStatusId, expectedStatusText, actualStatusText}, Locale.getDefault());
			
	        assertEquals(statusTextErrorMessage, expectedStatusText, actualStatusText);
	        
			String responseListLenghtErrorMessage = context.getMessage("timeline.length", new Object[] {count}, Locale.getDefault());
	        
	        assertTrue(responseListLenghtErrorMessage, responseList.size() <= count);
	      
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
        
	}

}