/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.twitter.automation;

import beans.OAuthV1Bean;
import org.mule.modules.tests.ConnectorTestCase;
import org.mule.twitter.TwitterOauthConnector;

/**
 * By default, the whole suite will run the test using the twitter:config mechanism (aka: connection management),
 * using the {@link #AUTOMATION_TEST_FLOWS_XML} xml file.
 * If the suite is executed parameterizing -Drun.environment.OAuthV1=true, it ran using the twitter:config-with-oauth
 * mechanism (aka: OAuth V1), using the {@link #OAUTHV1_AUTOMATION_TEST_FLOWS_XML} file
 */
public class TwitterTestParent extends ConnectorTestCase {

    private static final String AUTOMATION_TEST_FLOWS_XML = "automation-test-flows.xml";
    private static final String OAUTHV1_AUTOMATION_TEST_FLOWS_XML = "oauthv1-automation-test-flows.xml";

    protected String getConfigXmlFile() {
        //by default we use the twitter:config mechanism
        String configFile = AUTOMATION_TEST_FLOWS_XML;
        if (oauthRun()){
            configFile = OAUTHV1_AUTOMATION_TEST_FLOWS_XML;
        }
        return configFile;
    }

    /**
     * Hook to initialize the config-with-oauth elements, as a simulation of a dance with a web-flow.
     * @throws Exception
     */
    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();
        if (oauthRun()){     //run.environment.OAuthV1
            doOAuthV1SetUp();
        }
    }

    private void doOAuthV1SetUp() {
        //check oauthv1-automation-test-flows.xml and AutomationSpringBeans.xml to understand the beansId
        prepareTwitterOAuthConnector("Twitter", "twitter.sandbox");
        prepareTwitterOAuthConnector("auxSandbox", "twitter.auxSandbox");
        prepareTwitterOAuthConnector("auxAuxSandbox", "twitter.auxAuxSandbox");
    }

    /**
     * For a given twitter oauth config, it will override the {@link org.mule.twitter.TwitterOauthConnector#setAccessToken(String)}
     * and {@link org.mule.twitter.TwitterOauthConnector#setAccessTokenSecret(String)} as a simultion of a web-flow OAuth V1 dance.
     * Once done, any operation will work as the connector will appear as already authorized.
     *
     * @param connectorConfigName name of the global element to look for, it must be a twitter:config-with-oauth one
     * @param beanIdPlaceHolder name of the bean with the values to look up,and where the overrides are written
     */
    private void prepareTwitterOAuthConnector(String connectorConfigName, String beanIdPlaceHolder) {
        TwitterOauthConnector twitter = muleContext.getRegistry().lookupObject(connectorConfigName);
        OAuthV1Bean properties = getBeanFromContext(beanIdPlaceHolder);
        twitter.setAccessToken(properties.getAccessKey());
        twitter.setAccessTokenSecret(properties.getAccessSecret());
    }

    /**
     * @return True if the tests are being parametrized with -Drun.environment.OAuthV1=true. False otherwise
     */
    private static boolean oauthRun(){
        return Boolean.parseBoolean(System.getProperty("run.environment.OAuthV1"));
    }
}