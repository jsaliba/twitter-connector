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

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.twitter.automation.TestPlace;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import twitter4j.Place;
import twitter4j.ResponseList;
import twitter4j.Trends;

public class GetGeoDetailsTestCases extends TwitterTestCase {
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testGetGeoDetails() {
		
    	TestPlace placeById = (TestPlace) context.getBean("placeById");
    	
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
