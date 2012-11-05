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



public class GetRetweetedToMeTestCases extends TwitterTestCase {
    
    @Before
    public void setUp() {
    	
    	testObjects = new HashMap<String,Object>();
    			
    	TestStatus firstTweet = (TestStatus) context.getBean("firstStatusToRetweet");
    	TestStatus secondTweet = (TestStatus) context.getBean("secondStatusToRetweet");
    	
    	try {
    		
        	flow = lookupFlowConstruct("update-status-aux-sandbox");
        	
        	response = flow.process(getTestEvent(firstTweet.getText()));
        	firstTweet.setId(((Status) response.getMessage().getPayload()).getId());
        	
        	response = flow.process(getTestEvent(secondTweet.getText()));
        	secondTweet.setId(((Status) response.getMessage().getPayload()).getId());
        	
        	flow = lookupFlowConstruct("retweet-status");
	        
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
    		
    		TestStatus firstRetweetedStatus = (TestStatus) testObjects.get("firstRetweetedStatus");
    		TestStatus secondRetweetedStatus = (TestStatus) testObjects.get("secondRetweetedStatus");
    		
        	flow = lookupFlowConstruct("destroy-status-aux-sandbox");
        	
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
	public void testGetRetweetedToMeDefaultValues() {
		
		TestStatus firstRetweetedStatus = (TestStatus) testObjects.get("firstRetweetedStatus");
		TestStatus secondRetweetedStatus = (TestStatus) testObjects.get("secondRetweetedStatus");
		
		String responseListLenghtErrorMessage;
		
		try {
			
			flow = lookupFlowConstruct("get-retweeted-to-me-default-values");
			response = flow.process(getTestEvent(null));

			ResponseList<Status> responseList = (ResponseList<Status>) response.getMessage().getPayload();
			
			String retweetNotFoundErrorMessage = context.getMessage("retweet.notFound", null, Locale.getDefault());
			
			assertTrue(retweetNotFoundErrorMessage, TwitterTestUtils.isStatusTextOnTimeline(responseList, firstRetweetedStatus.getText()));
			   
			responseListLenghtErrorMessage = context.getMessage("timeline.length", new Object[] {TwitterTestUtils.TIMELINE_DEFAULT_LENGTH}, Locale.getDefault());
	        
	        assertTrue(responseListLenghtErrorMessage, responseList.size() <= TwitterTestUtils.TIMELINE_DEFAULT_LENGTH);
	        
	     // test parametrized operation

	        long firstRetweetedStatusId  = TwitterTestUtils.getIdForStatusTextOnResponseList(responseList, firstRetweetedStatus.getText());
	        long secondRetweetedStatusId = TwitterTestUtils.getIdForStatusTextOnResponseList(responseList, secondRetweetedStatus.getText());
			
	        int count = new Integer(2);
	        
			Map<String,Object> operationParams = new HashMap<String,Object>();
			operationParams.put("count", count);
			operationParams.put("page", new Integer(1));
	        operationParams.put("sinceId", firstRetweetedStatusId);
	        
			flow = lookupFlowConstruct("get-retweeted-to-me-parametrized");
			response = flow.process(getTestEvent(operationParams));
	        
			responseList = (ResponseList<Status>) response.getMessage().getPayload();
			
			long expectedStatusId = secondRetweetedStatusId;
			String statusIdErrorMessage = context.getMessage("timeline.statusId.notFound", new Object[] {expectedStatusId}, Locale.getDefault());
			
			assertTrue(statusIdErrorMessage, TwitterTestUtils.isStatusIdOnTimeline(responseList, expectedStatusId));
			
			String expectedStatusText = secondRetweetedStatus.getText();
			String actualStatusText = TwitterTestUtils.getStatusTextOnTimeline(responseList, expectedStatusId);
			
	        assertTrue(actualStatusText.contains(expectedStatusText));
	        
			responseListLenghtErrorMessage = context.getMessage("timeline.length", new Object[] {count}, Locale.getDefault());
	        
	        assertTrue(responseListLenghtErrorMessage, responseList.size() <= count);
	      
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
        
	} 

}