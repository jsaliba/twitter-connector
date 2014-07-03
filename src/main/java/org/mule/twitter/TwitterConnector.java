/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.twitter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.annotations.*;
import org.mule.api.annotations.param.ConnectionKey;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.internal.http.alternative.HttpClientHiddenConstructionArgument;

/**
 * Twitter is an online social networking service and microblogging service that enables its users to send and read
 * text-based posts of up to 140 characters, known as "tweets".
 *
 * @author MuleSoft, Inc.
 */
@Connector(name = "twitter", schemaVersion = "2.4", description = "Twitter Integration", friendlyName = "Twitter",
minMuleVersion = "3.5")
public class TwitterConnector extends  BaseTwitterConnector {

    protected transient Log logger = LogFactory.getLog(getClass());

    private Twitter twitter;

    private TwitterStream stream;

    /**
     * The consumer key used by this application
     */
    @Configurable
    private String consumerKey;

    /**
     * The consumer key secret by this application
     */
    @Configurable
    private String consumerSecret;

    private String accessToken;

    private String accessTokenSecret;

    /**
     * Connects to Twitter
     * @param accessKey The access key provided by Twitter
     * @param accessSecret The access secret provided by Twitter
     * @throws ConnectionException when the connection fails
     */
    @Connect
    public void connect(@ConnectionKey String accessKey, String accessSecret) throws ConnectionException{
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setUseSSL(this.getUseSSL());
        cb.setHttpProxyHost(this.getProxyHost());
        cb.setHttpProxyPort(this.getProxyPort());
        cb.setHttpProxyUser(this.getProxyUsername());
        cb.setHttpProxyPassword(this.getProxyPassword());
        
        HttpClientHiddenConstructionArgument.setUseMule(true);
        twitter = new TwitterFactory(cb.build()).getInstance();

        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        if (accessKey != null) {
            twitter.setOAuthAccessToken(new AccessToken(accessKey, accessSecret));
            setAccessToken(accessKey);
            setAccessTokenSecret(accessSecret);
        }

        //for connectivity testing

        try {
            if (accessKey != null)
            {
                getHomeTimeline(1, 1, -1);
            }

        } catch (TwitterException te) {
            throw new ConnectionException(ConnectionExceptionCode.UNKNOWN, null, "Bad credentials");
        }
    }

    @Disconnect
    public void disconnect() {
        twitter = null;
    }

    @ValidateConnection
    public boolean validateConnection() {
        return twitter != null;
    }

    @ConnectionIdentifier
    public String getConnectionIdentifier() {
        return getAccessToken() + "-" + getAccessTokenSecret();
    }

    @Override
    protected Twitter getTwitter() {
        return twitter;
    }

    @Override
    protected TwitterStream getTwitterStream() {
        initStream();
        return stream;
    }

    protected TwitterStream newStream() {
        ConfigurationBuilder cb = new ConfigurationBuilder()
                .setUseSSL(this.getUseSSL())
                .setOAuthConsumerKey(this.getConsumerKey())
                .setOAuthConsumerSecret(this.getConsumerSecret())
                .setStreamBaseURL(getStreamBaseUrl())
                .setSiteStreamBaseURL(getSiteStreamBaseUrl())
                .setHttpProxyHost(this.getProxyHost())
                .setHttpProxyPort(this.getProxyPort())
                .setHttpProxyUser(this.getProxyUsername())
                .setHttpProxyPassword(this.getProxyPassword());

        if (getAccessToken() != null) {
            cb.setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessTokenSecret);
        }

        HttpClientHiddenConstructionArgument.setUseMule(false);
        return new TwitterStreamFactory(cb.build()).getInstance();
    }

    private void initStream() {
        if (stream != null) {
            throw new IllegalStateException("Only one stream can be consumed per twitter account");
        }
        this.stream = newStream();
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }

}
