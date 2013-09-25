/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.twitter.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.twitter.automation.TwitterTestPlace;

import twitter4j.Location;
import twitter4j.ResponseList;



public class GetAvailableTrendsTestCases extends TwitterTestParent {
	
    @Category({SanityTests.class, RegressionTests.class})
	@Test
    public void testGetAvailableTrendsDefaultValues() {
    	
    	try {
    		
        	flow = lookupMessageProcessor("get-available-trends-default-values");
        	response = flow.process(getTestEvent(null));
        	ResponseList<Location> locations = (ResponseList<Location>) response.getMessage().getPayload();
        
        	assertNotNull(locations);
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} 
    	 
    }
    
    @Category({RegressionTests.class})
	@Test
    public void testGetAvailableTrendsParametrized() {
    	
    	TwitterTestPlace aPlace = (TwitterTestPlace) context.getBean("placeByCoordinates");
    	
    	Map<String,Object> operationParams = new HashMap<String,Object>();
		operationParams.put("latitude", aPlace.getLatitude());
		operationParams.put("longitude", aPlace.getLongitude());
    	
    	try {
    		
        	flow = lookupMessageProcessor("get-available-trends-parametrized");
        	response = flow.process(getTestEvent(operationParams));
        	ResponseList<Location> locations = (ResponseList<Location>) response.getMessage().getPayload();
        
        	assertNotNull(locations);
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} 
    	 
    }
	
}