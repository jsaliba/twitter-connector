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
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.twitter.automation.TwitterTestStatus;
import org.mule.twitter.automation.TwitterTestUtils;

import twitter4j.ResponseList;
import twitter4j.Status;



public class GetRetweetsTestCases extends TwitterTestParent {
	
	@Before
    public void setUp() {
    	
    	testObjects = new HashMap<String,Object>();
    			
    	TwitterTestStatus aStatus = (TwitterTestStatus) context.getBean("aStatusToRetweet");
    	aStatus.setText(String.format("%s Random Automation status", UUID.randomUUID().toString().substring(0, 9)));
    	try {
    		
        	flow = lookupMessageProcessorConstruct("update-status-aux-sandbox");
        	
        	response = flow.process(getTestEvent(aStatus.getText()));
        	aStatus.setId(((Status) response.getMessage().getPayload()).getId());
        	
        	flow = lookupMessageProcessorConstruct("retweet-status");
	        
        	flow.process(getTestEvent(aStatus.getId()));
        	
        	testObjects.put("aStatusToRetweet", aStatus);
        	
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
    		
    		TwitterTestStatus aRetweetedStatus = (TwitterTestStatus) testObjects.get("aStatusToRetweet");
    		
        	flow = lookupMessageProcessorConstruct("destroy-status-aux-sandbox");
        	
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
		
		TwitterTestStatus aRetweet = (TwitterTestStatus) testObjects.get("aStatusToRetweet");
		
		try {
			
			flow = lookupMessageProcessorConstruct("get-retweets-aux-sandbox");
			response = flow.process(getTestEvent(aRetweet.getId()));

			ResponseList<Status> responseList = (ResponseList<Status>) response.getMessage().getPayload();
			 
			Long expectedStatusId = TwitterTestUtils.getIdForStatusTextOnResponseList(responseList,aRetweet.getText());
			String statusIdErrorMessage = context.getMessage("timeline.statusId.notFound", new Object[] {expectedStatusId}, Locale.getDefault());
			
			assertTrue(statusIdErrorMessage, TwitterTestUtils.isStatusIdOnTimeline(responseList, expectedStatusId));
			
			String expectedStatusText = aRetweet.getText();
			String actualStatusText = TwitterTestUtils.getStatusTextOnTimeline(responseList, expectedStatusId);
			
			String statusTextErrorMessage = context.getMessage("status.text.noMatchForId", new Object[] {expectedStatusId}, Locale.getDefault());
			
			assertTrue(statusTextErrorMessage, actualStatusText.contains(expectedStatusText));
	        
			String responseListLenghtErrorMessage = context.getMessage("timeline.length", new Object[] {TwitterTestUtils.TIMELINE_DEFAULT_LENGTH}, Locale.getDefault());
	        
	        assertTrue(responseListLenghtErrorMessage, responseList.size() <= TwitterTestUtils.TIMELINE_DEFAULT_LENGTH);
	      
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
        
	} 
	
}