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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleException;

import twitter4j.Location;
import twitter4j.ResponseList;
import twitter4j.Trends;



public class GetDailyTrendsTestCases extends TwitterTestParent {
	
    @Category({SanityTests.class, RegressionTests.class})
	@Test
    public void testGetDailyTrendsDefaultValues() {
    	
    	try {
    		
        	flow = lookupFlowConstruct("get-daily-trends-default-values");
        	response = flow.process(getTestEvent(null));
        	List<Trends> trends = (List<Trends>) response.getMessage().getPayload();
        
        	assertNotNull(trends);
        	
		} catch (MuleException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
    }
    
    @Category({RegressionTests.class})
	@Test
    public void testGetDailyTrendsParametrized() {
    	
    	Calendar calendar = new GregorianCalendar();
    	calendar.add(Calendar.DATE, -5);
        Date startingDate = calendar.getTime();
	
    	try {
    		
        	flow = lookupFlowConstruct("get-daily-trends-parametrized");
        	response = flow.process(getTestEvent(startingDate));
        	ResponseList<Location> locations = (ResponseList<Location>) response.getMessage().getPayload();
        
        	assertNotNull(locations);
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
    	 
    }
	
}