/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.twitter.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.twitter.automation.TwitterTestStatus;
import org.mule.twitter.automation.TwitterSandbox;
import org.mule.twitter.automation.TwitterTestUtils;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.User;



public class GetRetweetedByTestCases extends TwitterTestParent {
    
	private TwitterSandbox sandbox;
	
    @Before
    public void setUp() {
    	
    	sandbox = (TwitterSandbox) context.getBean("sandbox");
    	
    	testObjects = new HashMap<String,Object>();
    			
    	TwitterTestStatus firstTweet = (TwitterTestStatus) context.getBean("firstStatusToRetweet");
    	TwitterTestStatus secondTweet = (TwitterTestStatus) context.getBean("secondStatusToRetweet");

    	try {
    		
        	flow = lookupFlowConstruct("update-status-aux-sandbox");
        	
        	response = flow.process(getTestEvent(firstTweet.getText()));
        	firstTweet.setId(((Status) response.getMessage().getPayload()).getId());

        	response = flow.process(getTestEvent(secondTweet.getText()));
        	secondTweet.setId(((Status) response.getMessage().getPayload()).getId());
        	
        	flow = lookupFlowConstruct("retweet-status");
        	flow.process(getTestEvent(firstTweet.getId()));
        	flow.process(getTestEvent(secondTweet.getId()));

        	testObjects.put("firstTweetedStatus", firstTweet);
        	testObjects.put("secondTweetedStatus", secondTweet);

        	 	
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
    		
    		TwitterTestStatus firstTweetedStatus = (TwitterTestStatus) testObjects.get("firstTweetedStatus");
    		TwitterTestStatus secondTweetedStatus = (TwitterTestStatus) testObjects.get("secondTweetedStatus");
    		
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
	public void testGetRetweetsByDefaultValues() {
		
		TwitterTestStatus aRetweetedStatus = (TwitterTestStatus) testObjects.get("firstTweetedStatus");

		try {
			
			flow = lookupFlowConstruct("get-retweeted-by-default-values");
			response = flow.process(getTestEvent(aRetweetedStatus.getId()));

			ResponseList<User> responseList = (ResponseList<User>) response.getMessage().getPayload();
			
			long expectedUserId = sandbox.getUserId();
			String userNotFoundErrorMessage = context.getMessage("user.id.notFound", new Object[] {expectedUserId}, Locale.getDefault());
			
			assertTrue(userNotFoundErrorMessage, TwitterTestUtils.isUserOnList(responseList,expectedUserId));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
        
	} 
   
}