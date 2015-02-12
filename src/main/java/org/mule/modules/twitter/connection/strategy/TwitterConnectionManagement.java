/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.twitter.connection.strategy;


import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.MuleContext;
import org.mule.api.annotations.*;
import org.mule.api.annotations.components.ConnectionManagement;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.api.context.MuleContextAware;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.internal.http.alternative.HttpClientHiddenConstructionArgument;
import twitter4j.internal.http.alternative.MuleHttpClient;

@ConnectionManagement(friendlyName = "Configuration")
public class TwitterConnectionManagement implements MuleContextAware {

    private Twitter twitter;

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

    /**
     * Whether to use SSL in API calls to Twitter
     */
    @Configurable
    @Default("true")
    @FriendlyName("Use SSL")
    private boolean useSSL;

    /**
     * Proxy host
     */
    @Configurable
    @Optional
    @Placement(group = "Proxy settings", tab = "Proxy")
    private String proxyHost;

    /**
     * Proxy port
     */
    @Configurable
    @Default("-1")
    @Placement(group = "Proxy settings", tab = "Proxy")
    private int proxyPort;

    /**
     * Proxy username
     */
    @Configurable
    @Optional
    @Placement(group = "Proxy settings", tab = "Proxy")
    private String proxyUsername;

    /**
     * Proxy password
     */
    @Configurable
    @Optional
    @Placement(group = "Proxy settings", tab = "Proxy")
    @Password
    private String proxyPassword;

    /**
     * Twitter Stream Base Url
     */
    @Configurable
    @Default("https://stream.twitter.com/1.1/")
    @Placement(group = "Streaming settings", tab = "Streaming")
    private String streamBaseUrl;

    /**
     * Twitter Site Stream Base Url
     */
    @Configurable

    @Default("https://sitestream.twitter.com/1.1/")
    @Placement(group = "Streaming settings", tab = "Streaming")
    private String siteStreamBaseUrl;

    private String accessToken;

    private String accessTokenSecret;

    /**
     * Connects to Twitter
     *
     * @param accessKey    The access key provided by Twitter
     * @param accessSecret The access secret provided by Twitter
     * @throws org.mule.api.ConnectionException when the connection fails
     */
    @Connect
    @TestConnectivity
    public void connect(@ConnectionKey String accessKey, String accessSecret) throws ConnectionException {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setUseSSL(useSSL);
        cb.setHttpProxyHost(proxyHost);
        cb.setHttpProxyPort(proxyPort);
        cb.setHttpProxyUser(proxyUsername);
        cb.setHttpProxyPassword(proxyPassword);

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
            if (accessKey != null) {
                twitter.getRateLimitStatus();
            }

        } catch (TwitterException te) {
            throw new ConnectionException(ConnectionExceptionCode.UNKNOWN, null, "Bad credentials", te);
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

    public TwitterStream newStream() {
        ConfigurationBuilder cb = new ConfigurationBuilder()
                .setUseSSL(useSSL)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setStreamBaseURL(getStreamBaseUrl())
                .setSiteStreamBaseURL(getSiteStreamBaseUrl())
                .setHttpProxyHost(proxyHost)
                .setHttpProxyPort(proxyPort)
                .setHttpProxyUser(proxyUsername)
                .setHttpProxyPassword(proxyPassword);

        if (getAccessToken() != null) {
            cb.setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessTokenSecret);
        }

        HttpClientHiddenConstructionArgument.setUseMule(false);
        return new TwitterStreamFactory(cb.build()).getInstance();
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

    public String getStreamBaseUrl() {
        return streamBaseUrl;
    }

    public void setStreamBaseUrl(String streamBaseUrl) {
        this.streamBaseUrl = streamBaseUrl;
    }

    public String getSiteStreamBaseUrl() {
        return siteStreamBaseUrl;
    }

    public void setSiteStreamBaseUrl(String siteStreamBaseUrl) {
        this.siteStreamBaseUrl = siteStreamBaseUrl;
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

    public boolean isUseSSL() {
        return useSSL;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public Twitter getTwitterClient() {
        return twitter;
    }

    public boolean getUseSSL() {
        return useSSL;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }

    public void setMuleContext(MuleContext context) {
        MuleHttpClient.setMuleContext(context);
    }
}
