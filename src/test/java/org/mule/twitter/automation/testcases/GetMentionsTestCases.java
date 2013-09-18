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
import twitter4j.User;

public class GetMentionsTestCases extends TwitterTestParent {
		
    @Before
    public void setUp() {
    	
    	testObjects = new HashMap<String,Object>();
    	
    	TwitterTestStatus firstTweet = (TwitterTestStatus) context.getBean("firstMention");
    	TwitterTestStatus secondTweet = (TwitterTestStatus) context.getBean("secondMention");
    	
    	try {
    		
    		flow = lookupMessageProcessorConstruct("show-user");
        	
    		response = flow.process(getTestEvent(null));
    		
    		String screenName = ((User) response.getMessage().getPayload()).getScreenName();
        	firstTweet.setText('@' + screenName + ' ' + firstTweet.getText());
        	secondTweet.setText('@' + screenName + ' ' + secondTweet.getText());
    		
    		flow = lookupMessageProcessorConstruct("update-status-aux-sandbox");

        	response = flow.process(getTestEvent(firstTweet.getText()));
        	firstTweet.setId(((Status) response.getMessage().getPayload()).getId());
        	testObjects.put("firstMention", firstTweet);
        	
        	response = flow.process(getTestEvent(secondTweet.getText()));
        	secondTweet.setId(((Status) response.getMessage().getPayload()).getId());
        	testObjects.put("secondMention", secondTweet);
        	
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
    		
    		TwitterTestStatus firstMention = (TwitterTestStatus) testObjects.get("firstMention");
    		TwitterTestStatus secondMention = (TwitterTestStatus) testObjects.get("secondMention");
    		
        	flow = lookupMessageProcessorConstruct("destroy-status-aux-sandbox");
        	
        	flow.process(getTestEvent(firstMention.getId()));
        	flow.process(getTestEvent(secondMention.getId()));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
   	
    }
	
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testGetMentionsDefaultValues() {
		
		TwitterTestStatus testTweet = (TwitterTestStatus) testObjects.get("firstMention");
		
		try {
			
			flow = lookupMessageProcessorConstruct("get-mentions-default-values");
			response = flow.process(getTestEvent(null));

			ResponseList<Status> mentions = (ResponseList<Status>) response.getMessage().getPayload();
			
			Long expectedStatusId = testTweet.getId();
			String statusIdErrorMessage = context.getMessage("timeline.statusId.notFound", new Object[] {expectedStatusId}, Locale.getDefault());
			
			assertTrue(statusIdErrorMessage, TwitterTestUtils.isStatusIdOnTimeline(mentions, expectedStatusId));
			
			String expectedStatusText = testTweet.getText();
			String actualStatusText = TwitterTestUtils.getStatusTextOnTimeline(mentions, expectedStatusId);
			
			String statusTextErrorMessage = context.getMessage("status.text.noMatchForId", new Object[] {expectedStatusId}, Locale.getDefault());
			
	        assertEquals(statusTextErrorMessage, expectedStatusText, actualStatusText);
	        
			String timelineLenghtErrorMessage = context.getMessage("timeline.length", new Object[] {TwitterTestUtils.TIMELINE_DEFAULT_LENGTH}, Locale.getDefault());
	        
	        assertTrue(timelineLenghtErrorMessage, mentions.size() <= TwitterTestUtils.TIMELINE_DEFAULT_LENGTH);
	      
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
        
	} 
	
    @Category({RegressionTests.class})
	@Test
	public void testGetMentionsParametrized() {
	
		int count = new Integer(2);
		
		TwitterTestStatus firstMention = (TwitterTestStatus) testObjects.get("firstMention");
		TwitterTestStatus secondMention = (TwitterTestStatus) testObjects.get("secondMention");
		
		Map<String,Object> operationParams = new HashMap<String,Object>();
		operationParams.put("count", count);
		operationParams.put("page", new Integer(1));
		operationParams.put("sinceId", firstMention.getId());
		
		try {
			
			flow = lookupMessageProcessorConstruct("get-mentions-parametrized");
			response = flow.process(getTestEvent(operationParams));

			ResponseList<Status> mentions = (ResponseList<Status>) response.getMessage().getPayload();
			
			Long expectedStatusId = secondMention.getId();
			String statusIdErrorMessage = context.getMessage("timeline.statusId.notFound", new Object[] {expectedStatusId}, Locale.getDefault());
			
			assertTrue(statusIdErrorMessage, TwitterTestUtils.isStatusIdOnTimeline(mentions, expectedStatusId));
			
			String expectedStatusText = secondMention.getText();
			String actualStatusText = TwitterTestUtils.getStatusTextOnTimeline(mentions, expectedStatusId);
			
			String statusTextErrorMessage = context.getMessage("status.text.noMatchForId", new Object[] {expectedStatusId, expectedStatusText, actualStatusText}, Locale.getDefault());
			
	        assertEquals(statusTextErrorMessage, expectedStatusText, actualStatusText);
	        
			String timelineLenghtErrorMessage = context.getMessage("timeline.length", new Object[] {count}, Locale.getDefault());
	        
	        assertTrue(timelineLenghtErrorMessage, mentions.size() <= count);
	      
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
        
	}
	
}
