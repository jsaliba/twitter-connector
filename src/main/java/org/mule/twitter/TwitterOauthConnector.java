/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.twitter;

import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.ConnectivityTesting;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.oauth.*;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.internal.http.alternative.HttpClientHiddenConstructionArgument;

@Connector(name = "twitter", schemaVersion = "2.4", description = "Twitter Integration", friendlyName = "Twitter (OAuth)",
        minMuleVersion = "3.5", connectivityTesting = ConnectivityTesting.DISABLED,
        configElementName = "config-with-oauth")
@OAuth(requestTokenUrl = "https://api.twitter.com/oauth/request_token",
        accessTokenUrl = "https://api.twitter.com/oauth/access_token",
        authorizationUrl = "https://api.twitter.com/oauth/authorize",
        signingStrategy = OAuthSigningStrategy.QUERY_STRING,
        authorizationParameters = {
                @OAuthAuthorizationParameter(name = "x_auth_access_type", type = AuthAccessType.class,
                        description = "Specifies the authorization access type.", optional = true)
        })
public class TwitterOauthConnector extends BaseTwitterConnector {

    private Twitter twitter;

    private TwitterStream stream;

    @Configurable
    @OAuthConsumerKey
    private String consumerKey;

    @Configurable
    @OAuthConsumerSecret
    private String consumerSecret;

    @OAuthAccessToken
    private String accessToken;

    @OAuthAccessTokenSecret
    private String accessTokenSecret;

    @Override
    public Twitter getTwitter() {
        if(twitter == null) {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setUseSSL(this.getUseSSL());
            cb.setHttpProxyHost(this.getProxyHost());
            cb.setHttpProxyPort(this.getProxyPort());
            cb.setHttpProxyUser(this.getProxyUsername());
            cb.setHttpProxyPassword(this.getProxyPassword());

            cb.setOAuthAccessToken(this.accessToken);
            cb.setOAuthAccessTokenSecret(this.accessTokenSecret);

            HttpClientHiddenConstructionArgument.setUseMule(true);
            twitter = new TwitterFactory(cb.build()).getInstance();

            twitter.setOAuthConsumer(consumerKey, consumerSecret);
        }

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
                .setHttpProxyPassword(this.getProxyPassword())


                .setOAuthAccessToken(this.accessToken)
                .setOAuthAccessTokenSecret(this.accessTokenSecret);

        HttpClientHiddenConstructionArgument.setUseMule(false);
        return new TwitterStreamFactory(cb.build()).getInstance();
    }

    private void initStream() {
        if (stream != null) {
            throw new IllegalStateException("Only one stream can be consumed per twitter account");
        }
        this.stream = newStream();
    }


    /**
     * Set accessToken
     *
     * @param accessToken The accessToken
     */
    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    /**
     * Get ClientSecret
     */
    public String getAccessToken()
    {
        return this.accessToken;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }

}
