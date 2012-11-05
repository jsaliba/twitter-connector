/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.twitter.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.twitter.automation.TwitterSandbox;

import twitter4j.User;



public class ShowUserTestCases extends TwitterTestCase {
     
    @Category({SmokeTests.class, SanityTests.class, RegressionTests.class})
	@Test
	public void testShowUser() {
    	
    	TwitterSandbox sandbox = (TwitterSandbox) context.getBean("sandbox");
    	
		try {
			
			flow = lookupFlowConstruct("show-user");
	        response = flow.process(getTestEvent(null));
	        
	        User user = (User) response.getMessage().getPayload();
	        
	        assertEquals(sandbox.getUserId(), user.getId()); 
	        assertEquals(sandbox.getUserName(), user.getName()); 
	        assertEquals(sandbox.getUserScreenName(), user.getScreenName()); 

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
     
	}

}