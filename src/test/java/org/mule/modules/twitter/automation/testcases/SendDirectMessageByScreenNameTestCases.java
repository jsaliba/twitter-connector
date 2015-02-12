/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.twitter.automation.testcases;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;
import org.mule.modules.twitter.automation.RegressionTests;
import org.mule.modules.twitter.automation.TwitterTestParent;
import twitter4j.DirectMessage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SendDirectMessageByScreenNameTestCases extends TwitterTestParent {

    @Category({RegressionTests.class})
    @Test
    public void testSendDirectMessageByScreenName() {
        initializeTestRunMessage("sendDirectMessageByScreenNameTestData");
        try {
            DirectMessage directMessage = runFlowAndGetPayload("send-direct-message-by-screen-name");

            assertEquals(getTestRunMessageValue("message").toString(), directMessage.getText());
            assertEquals(getTestRunMessageValue("senderSandboxUserName").toString(), directMessage.getSenderScreenName());
            assertEquals(getTestRunMessageValue("recipientSandboxUserName").toString(), directMessage.getRecipientScreenName());

        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

}
