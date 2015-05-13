/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.twitter.automation;

import org.junit.Rule;
import org.junit.rules.Timeout;
import org.mule.modules.tests.ConnectorTestCase;


public class TwitterTestParent extends ConnectorTestCase {

    @Rule
    public Timeout globalTimeout = new Timeout(1500000);
    public static int SETUP_DELAY = 30000;


    protected <T> T runFlowAndGetPayload(String flowName) throws Exception {
        T messagePayload = super.runFlowAndGetPayload(flowName);
        // Slow down the calls to api to avoid rate limit exceeded.
        Thread.sleep(SETUP_DELAY);
        return messagePayload;
    }

    protected <T> T runFlowAndGetPayload(String flowName, String beanId) throws Exception {
        T messagePayload = super.runFlowAndGetPayload(flowName, beanId);
        // Slow down the calls to api to avoid rate limit exceeded.
        Thread.sleep(SETUP_DELAY);
        return messagePayload;
    }

}