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
import org.mule.twitter.automation.TwitterTestUtils;

import twitter4j.ResponseList;
import twitter4j.Status;

public class GetUserTimelineTestCases extends TwitterTestParent {
	
    @Before
    public void setUp() {
    	
    	testObjects = new HashMap<String,Object>();
    	
    	TwitterTestStatus firstTweet = (TwitterTestStatus) context.getBean("firstStatus");
    	TwitterTestStatus secondTweet = (TwitterTestStatus) context.getBean("secondStatus");
    	
    	try {
    		
        	flow = lookupMessageProcessorConstruct("update-status");
        	
        	response = flow.process(getTestEvent(firstTweet.getText()));
        	firstTweet.setId(((Status) response.getMessage().getPayload()).getId());
        	testObjects.put("firstTweet", firstTweet);
        	
        	response = flow.process(getTestEvent(secondTweet.getText()));
        	secondTweet.setId(((Status) response.getMessage().getPayload()).getId());
        	testObjects.put("secondTweet", secondTweet);
        	
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
    		
    		TwitterTestStatus firstTweet = (TwitterTestStatus) testObjects.get("firstTweet");
    		TwitterTestStatus secondTweet = (TwitterTestStatus) testObjects.get("secondTweet");
    		
        	flow = lookupMessageProcessorConstruct("destroy-status");
        	flow.process(getTestEvent(firstTweet.getId()));
        	flow.process(getTestEvent(secondTweet.getId()));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
   	
    }
	
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testGetUserTimelineDefaultValues() {
		
		TwitterTestStatus testTweet = (TwitterTestStatus) testObjects.get("firstTweet");
		
		try {
			
			flow = lookupMessageProcessorConstruct("get-user-timeline-default-values");
			response = flow.process(getTestEvent(null));

			ResponseList<Status> timeLine = (ResponseList<Status>) response.getMessage().getPayload();
			
			Long expectedStatusId = testTweet.getId();
			String statusIdErrorMessage = context.getMessage("timeline.statusId.notFound", new Object[] {expectedStatusId}, Locale.getDefault());
			
			assertTrue(statusIdErrorMessage, TwitterTestUtils.isStatusIdOnTimeline(timeLine, expectedStatusId));
			
			String expectedStatusText = testTweet.getText();
			String actualStatusText = TwitterTestUtils.getStatusTextOnTimeline(timeLine, expectedStatusId);
			
			String statusTextErrorMessage = context.getMessage("status.text.noMatchForId", new Object[] {expectedStatusId}, Locale.getDefault());
			
	        assertEquals(statusTextErrorMessage, expectedStatusText, actualStatusText);
	        
			String timelineLenghtErrorMessage = context.getMessage("timeline.length", new Object[] {TwitterTestUtils.TIMELINE_DEFAULT_LENGTH}, Locale.getDefault());
	        
	        assertTrue(timelineLenghtErrorMessage, timeLine.size() <= TwitterTestUtils.TIMELINE_DEFAULT_LENGTH);
	      
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
        
	} 
	
    @Category({RegressionTests.class})
	@Test
	public void testGetUserTimelineParametrized() {
	
		int count = new Integer(2);
		
		TwitterTestStatus firstTweet = (TwitterTestStatus) testObjects.get("firstTweet");
		TwitterTestStatus secondTweet = (TwitterTestStatus) testObjects.get("secondTweet");
		
		Map<String,Object> operationParams = new HashMap<String,Object>();
		operationParams.put("count", count);
		operationParams.put("page", new Integer(1));
		operationParams.put("sinceId", firstTweet.getId());
		
		try {
			
			flow = lookupMessageProcessorConstruct("get-user-timeline-parameterized");
			response = flow.process(getTestEvent(operationParams));

			ResponseList<Status> timeLine = (ResponseList<Status>) response.getMessage().getPayload();
			
			Long expectedStatusId = secondTweet.getId();
			String statusIdErrorMessage = context.getMessage("timeline.statusId.notFound", new Object[] {expectedStatusId}, Locale.getDefault());
			
			assertTrue(statusIdErrorMessage, TwitterTestUtils.isStatusIdOnTimeline(timeLine, expectedStatusId));
			
			String expectedStatusText = secondTweet.getText();
			String actualStatusText = TwitterTestUtils.getStatusTextOnTimeline(timeLine, expectedStatusId);
			
			String statusTextErrorMessage = context.getMessage("status.text.noMatchForId", new Object[] {expectedStatusId}, Locale.getDefault());
			
	        assertEquals(statusTextErrorMessage, expectedStatusText, actualStatusText);
	        
			String timelineLenghtErrorMessage = context.getMessage("timeline.length", new Object[] {count}, Locale.getDefault());
	        
	        assertTrue(timelineLenghtErrorMessage, timeLine.size() <= count);
	      
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
        
	}
	
}
