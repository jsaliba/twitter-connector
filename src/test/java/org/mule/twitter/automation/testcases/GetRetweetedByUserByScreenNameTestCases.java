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



public class GetRetweetedByUserByScreenNameTestCases extends TwitterTestParent {
    
	private TwitterSandbox sandbox;
	
    @Before
    public void setUp() {
    	
    	testObjects = new HashMap<String,Object>();
    			
    	TestStatus firstTweet = (TestStatus) context.getBean("firstStatusToRetweet");
    	TestStatus firstRetweet = new TestStatus();
    	
    	TestStatus secondTweet = (TestStatus) context.getBean("secondStatusToRetweet");
    	TestStatus secondRetweet = new TestStatus();
    	
    	sandbox = (TwitterSandbox) context.getBean("sandbox");
    	
    	try {
    		
        	flow = lookupFlowConstruct("update-status-aux-sandbox");
        	
        	response = flow.process(getTestEvent(firstTweet.getText()));
        	firstTweet.setId(((Status) response.getMessage().getPayload()).getId());

        	response = flow.process(getTestEvent(secondTweet.getText()));
        	secondTweet.setId(((Status) response.getMessage().getPayload()).getId());
        	
        	flow = lookupFlowConstruct("retweet-status");
	        
        	response = flow.process(getTestEvent(firstTweet.getId()));
        	firstRetweet.setId(((Status) response.getMessage().getPayload()).getId());
        	firstRetweet.setText(((Status) response.getMessage().getPayload()).getText());
        	
        	response = flow.process(getTestEvent(secondTweet.getId()));
        	secondRetweet.setId(((Status) response.getMessage().getPayload()).getId());
        	secondRetweet.setText(((Status) response.getMessage().getPayload()).getText());
        	
        	testObjects.put("firstTweetedStatus", firstTweet);
        	testObjects.put("secondTweetedStatus", secondTweet);
        	
        	testObjects.put("firstRetweetedStatus", firstRetweet);
        	testObjects.put("secondRetweetedStatus", secondRetweet);
                	
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
    		
    		TestStatus firstTweetedStatus = (TestStatus) testObjects.get("firstTweetedStatus");
    		TestStatus secondTweetedStatus = (TestStatus) testObjects.get("secondTweetedStatus");
    		
        	flow = lookupFlowConstruct("destroy-status-aux-sandbox");
        	
        	flow.process(getTestEvent(firstTweetedStatus.getId()));
        	flow.process(getTestEvent(secondTweetedStatus.getId()));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
   	
    }
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testGetRetweetedByUserByScreenNameDefaultValues() {
		
		TestStatus aRetweetedStatus = (TestStatus) testObjects.get("firstRetweetedStatus");
		
		try {
			
			flow = lookupFlowConstruct("get-retweeted-by-user-by-screen-name-default-values");
			response = flow.process(getTestEvent(sandbox.getUserScreenName()));

			ResponseList<Status> responseList = (ResponseList<Status>) response.getMessage().getPayload();
			
			String retweetNotFoundErrorMessage = context.getMessage("retweet.notFound", null, Locale.getDefault());
			
			assertTrue(retweetNotFoundErrorMessage, TwitterTestUtils.isStatusTextOnTimeline(responseList, aRetweetedStatus.getText()));
			   
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
	public void testGetRetweetedByUserByScreenNameParametrized() {
	
		int count = new Integer(2);
		
		TestStatus firstRetweetedStatus = (TestStatus) testObjects.get("firstRetweetedStatus");
		TestStatus secondRetweetedStatus = (TestStatus) testObjects.get("secondRetweetedStatus");
		
		Map<String,Object> operationParams = new HashMap<String,Object>();
		operationParams.put("count", count);
		operationParams.put("page", new Integer(1));
		operationParams.put("sinceId", firstRetweetedStatus.getId());
		operationParams.put("screenName", sandbox.getUserScreenName());
		
		try {
			
			flow = lookupFlowConstruct("get-retweeted-by-user-by-screen-name-parametrized");
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