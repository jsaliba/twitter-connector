/*
 * $Id: TwitterBase.java 297 2011-04-12 00:12:01Z dzapata $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.ibeans.twitter;

import org.ibeans.annotation.State;
import org.ibeans.annotation.Usage;
import org.ibeans.annotation.filter.JsonErrorFilter;
import org.ibeans.annotation.filter.XmlErrorFilter;
import org.ibeans.annotation.param.ReturnType;
import org.ibeans.annotation.param.UriParam;
import org.ibeans.api.ExceptionListenerAware;
import org.ibeans.impl.auth.OAuthAuthentication;

/**
 * TODO
 */
@Usage("The Twitter iBean provides a simple bean interface to the Twitter REST API.")
@JsonErrorFilter(expr = "error!=0", errorCode = "error")
@XmlErrorFilter(expr = "/status/error > 0", errorCode = "/status/error")
public interface TwitterBase extends OAuthAuthentication, ExceptionListenerAware
{
    public static final String TWITTER_REST_URL = "https://api.twitter.com";
    public static final String TWITTER_SEARCH_URL = "https://search.twitter.com";
    
    /**
     * Valid Twitter formats
     */
    public enum FORMAT
    {
        JSON("json"),
        XML("xml"),
        ATOM("atom"),
        RSS("rss");

        private String value;

        private FORMAT(String value)
        {
            this.value = value;
        }

        public String toString()
        {
            return value;
        }
    }

    /**
     * Default format if the Format is not set.
     */
    @UriParam("format")
    public static final FORMAT DEFAULT_TWITTER_FORMAT = FORMAT.JSON;
    
    /**
     * Version iBean supports
     */
    @UriParam("version")
    public static final int TWITTER_API_VERSION = 1;
    
    /**
     * Default value for include_entities
     */
    @UriParam("include_entities")
    public static final Boolean INCLUDE_ENTITIES = false;

    /**
     * The default return type if the return type is not set
     */
    @ReturnType
    public static final Class DEFAULT_RETURN_TYPE = String.class;

    /**
     * Sets the format of the response from the Twitter service, the return type will be the {@link #DEFAULT_RETURN_TYPE}
     * {@link String}.
     * This can be call by JavaScript clients where it doesn't make sense to specify the java return type.
     * @param format the format to use when receiving a response from Twitter
     */
    @State
    public void setFormat(@UriParam("format") FORMAT format);

    /**
     * Sets the format of the response from the Twitter service.  note that the 'returnType' should be compatible with the
     * the format. i.e. with JSon you can use {@link String} and {@link org.mule.module.json.JsonData} only. With Atom you
     * can use {@link String}, {@link org.w3c.dom.Document} or {@link org.apache.abdera.model.Feed}.
     *
     * @param format the format to use when receiving a response from Twitter
     * @param returnType The Java Class as the representation format for the response
     */
    @State
    public void setFormat(@UriParam("format") FORMAT format, @ReturnType Class returnType);
    
    /**
     * Sets the include_entities property used for a number of api calls to twitter. The description from twitter about this 
     * parameter is the following: 
     * 
     * When set to either true, t or 1, each tweet will include a node called "entities,". This node offers a variety of 
     * metadata about the tweet in a discreet structure, including: user_mentions, urls, and hashtags. While entities are 
     * opt-in on timelines at present, they will be made a default component of output in the future. See Tweet Entities for 
     * more detail on entities.
     */
    @State
    public void setIncludeEntities(@UriParam("include_entities") Boolean includeEntities);
}