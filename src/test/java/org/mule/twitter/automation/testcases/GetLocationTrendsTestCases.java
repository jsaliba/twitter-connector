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

public class GetLocationTrendsTestCases extends TwitterTestCase {
    
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testGetLocationTrendsDefaultValues() {
    	
		try {

			flow = lookupFlowConstruct("get-location-trends-default-values");
        	response = flow.process(getTestEvent(null));
        	Trends trends = (Trends) response.getMessage().getPayload();
        	
        	assertNotNull(trends);
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
    }
    
    @Category({RegressionTests.class})
	@Test
	public void testGetLocationTrendsParametrized() {
		
    	TestPlace place = (TestPlace) context.getBean("placeByWOEID");
    	
		try {

			flow = lookupFlowConstruct("get-location-trends-parametrized");
        	response = flow.process(getTestEvent(place.getWoeid()));
        	Trends trends = (Trends) response.getMessage().getPayload();
        	
        	assertNotNull(trends);
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}

}
