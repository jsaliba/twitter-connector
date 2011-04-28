/*
 * $Id: TwitterIBean.java 267 2010-11-26 19:32:17Z dfeist $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.ibeans.twitter;

import org.mule.ibeans.twitter.config.TwitterIBeanFactoryBean;
import org.mule.tools.cloudconnect.annotations.Connector;

import org.ibeans.annotation.IBeanGroup;

/**
 * An iBean interface that contains all Twitter methods
 */
@IBeanGroup
@Connector(namespacePrefix = "twitter", 
           namespaceUri = "http://www.mulesoft.org/schema/mule/twitter", 
           factory = TwitterIBeanFactoryBean.class)
public interface TwitterIBean extends TwitterGeoIBean, TwitterStatusIBean, TwitterTimelineIBean, TwitterAccountIBean, TwitterSearchIBean, TwitterUserIBean, TwitterBase
{

}
