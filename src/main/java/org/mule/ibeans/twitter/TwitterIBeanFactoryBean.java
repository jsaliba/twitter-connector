/*
 * $Id: TwitterIBeanFactoryBean.java 297 2011-04-12 00:12:01Z dzapata $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.ibeans.twitter;

import org.mule.api.MuleContext;
import org.mule.api.context.MuleContextAware;
import org.mule.ibeans.twitter.TwitterIBean;
import org.mule.ibeans.twitter.TwitterBase.FORMAT;
import org.mule.module.ibeans.config.IBeanBinding;
import org.mule.module.ibeans.config.IBeanFlowConstruct;
import org.mule.module.ibeans.spi.MuleIBeansPlugin;

import org.mule.tools.cloudconnect.annotations.Property;
import org.springframework.beans.factory.FactoryBean;

public class TwitterIBeanFactoryBean implements FactoryBean<TwitterIBean>, MuleContextAware
{
    @Property
    private FORMAT format;
    @Property
    private String consumerKey;
    @Property
    private String consumerSecret;
    @Property
    private String oathToken;
    @Property
    private String oathTokenSecret;

    protected MuleContext muleContext;

    public TwitterIBean getObject() throws Exception
    {
        IBeanBinding binding = createBinding(TwitterIBean.class.getSimpleName());
        binding.setInterface(TwitterIBean.class);
        TwitterIBean ibean = (TwitterIBean) binding.createProxy(new Object());
        if (format != null)
        {
            ibean.setFormat(format);
        }
        if (consumerKey != null && consumerSecret != null)
        {
            ibean.initOAuth(consumerKey, consumerSecret);
            ibean.setAccessToken(oathToken, oathTokenSecret);            
        }
        return ibean;
    }

    public Class<TwitterIBean> getObjectType()
    {
        return TwitterIBean.class;
    }

    public boolean isSingleton()
    {
        return true;
    }

    public void setMuleContext(MuleContext context)
    {
        this.muleContext = context;

    }

    protected IBeanBinding createBinding(String name)
    {
        return new IBeanBinding(new IBeanFlowConstruct(name + ".ibean", muleContext), muleContext,
            new MuleIBeansPlugin(muleContext));
    }

    public void setFormat(String format)
    {
        this.format = FORMAT.valueOf(format);
    }

    public void setConsumerKey(String consumerKey)
    {
        this.consumerKey = consumerKey;
    }

    public void setConsumerSecret(String consumerSecret)
    {
        this.consumerSecret = consumerSecret;
    }

    public void setOauthToken(String oathToken)
    {
        this.oathToken = oathToken;
    }

    public void setOauthTokenSecret(String oathTokenSecret)
    {
        this.oathTokenSecret = oathTokenSecret;
    }
}
