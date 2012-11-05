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
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.twitter.automation.TestStatus;
import org.mule.twitter.automation.TwitterSandbox;
import org.mule.twitter.automation.TwitterTestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import twitter4j.ResponseList;
import twitter4j.Status;



public class GetPublicTimelineTestCases extends TwitterTestCase {
	
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
	public void testGetPublicTimeline() {
		
		TestStatus testTweet = (TestStatus) testObjects.get("testTweet");
		
		try {
			
			flow = lookupFlowConstruct("get-public-timeline");
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
	
}