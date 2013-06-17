/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.twitter.automation.testcases;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.twitter.automation.TwitterTestStatus;
import org.mule.twitter.automation.TwitterTestUtils;

import twitter4j.QueryResult;
import twitter4j.Status;



public class SearchTestCases extends TwitterTestParent {
	
    @Before
    public void setUp() {

    	testObjects = new HashMap<String,Object>();
    			
    	TwitterTestStatus aTweetToQueryFor = (TwitterTestStatus) context.getBean("aTweetToQueryFor");
    	
    	try {
    		
        	flow = lookupFlowConstruct("update-status");
        	
        	response = flow.process(getTestEvent(aTweetToQueryFor.getText()));
        	aTweetToQueryFor.setId(((Status) response.getMessage().getPayload()).getId());

        	testObjects.put("aTweetToQueryFor", aTweetToQueryFor);
  	 	
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
    		
    		TwitterTestStatus aTweetToQueryFor = (TwitterTestStatus) testObjects.get("aTweetToQueryFor");
    		
        	flow = lookupFlowConstruct("destroy-status");       	
        	flow.process(getTestEvent(aTweetToQueryFor.getId()));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
   	
    }
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testGetRetweetsByIdsDefaultValues() {
		
		TwitterTestStatus aTweetToQueryFor = (TwitterTestStatus) testObjects.get("aTweetToQueryFor");

		try {
			
			flow = lookupFlowConstruct("search-default-values");
			response = flow.process(getTestEvent("blue+angels"));

			QueryResult responseList = (QueryResult) response.getMessage().getPayload();
			List<Status> tweets = (List<Status>) responseList.getTweets();
			assertTrue(tweets.size() != 0);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
        
	} 
   
}