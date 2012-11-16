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

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.twitter.automation.TwitterTestPlace;

import twitter4j.Place;

public class GetGeoDetailsTestCases extends TwitterTestParent {
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testGetGeoDetails() {
		
    	TwitterTestPlace placeById = (TwitterTestPlace) context.getBean("placeById");
    	
		try {

			flow = lookupFlowConstruct("get-geo-details");
        	response = flow.process(getTestEvent(placeById.getId()));
        	Place place = (Place) response.getMessage().getPayload();
        	
        	assertNotNull(place);
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} 
     
	}

}
