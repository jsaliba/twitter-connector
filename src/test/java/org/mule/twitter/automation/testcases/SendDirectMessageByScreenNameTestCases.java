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

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.twitter.automation.TwitterTestStatus;
import org.mule.twitter.automation.TwitterSandbox;

import twitter4j.DirectMessage;

public class SendDirectMessageByScreenNameTestCases extends TwitterTestParent {
	
    @Category({SanityTests.class, RegressionTests.class})
	@Test
	public void testSendDirectMessageByScreenName() {
    	
    	TwitterSandbox senderSandbox = (TwitterSandbox) context.getBean("sandbox");
    	TwitterSandbox recipientSandbox = (TwitterSandbox) context.getBean("auxAuxSandbox");
		TwitterTestStatus aDirectMessage = (TwitterTestStatus) context.getBean("directMessageByScreenName");
		
		Map<String,Object> operationParams = new HashMap<String,Object>();
		operationParams.put("screenName", recipientSandbox.getUserScreenName());
		operationParams.put("message", aDirectMessage.getText());
		
		try {
			
			flow = lookupFlowConstruct("send-direct-message-by-screen-name");
			response = flow.process(getTestEvent(operationParams));

			DirectMessage directMessage = (DirectMessage) response.getMessage().getPayload();
			
			assertEquals(aDirectMessage.getText(), directMessage.getText());
			assertEquals(senderSandbox.getUserId(), directMessage.getSenderId());
			assertEquals(recipientSandbox.getUserId(), directMessage.getRecipientId());
	      
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
        
	} 
	
}
