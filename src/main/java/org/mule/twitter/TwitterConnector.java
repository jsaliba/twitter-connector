/**
 * Mule Twitter Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.twitter;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleContext;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Module;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.Source;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.api.callback.SourceCallback;
import org.mule.api.context.MuleContextAware;
import org.mule.twitter.UserEvent.EventType;
import twitter4j.DirectMessage;
import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.GeoQuery;
import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.Place;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.SiteStreamsAdapter;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.StatusUpdate;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamAdapter;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.internal.http.alternative.HttpClientHiddenConstructionArgument;
import twitter4j.internal.http.alternative.MuleHttpClient;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * A Connector for Twitter which uses twitter4j.
 */
@Module(name = "twitter",
        namespace = "http://repository.mulesoft.org/releases/org/mule/modules/mule-module-twitter",
        schemaLocation = "http://repository.mulesoft.org/releases/org/mule/modules/mule-module-twitter/2.3/mule-twitter.xsd",
        version = "2.3")
public class TwitterConnector implements MuleContextAware
{
    protected transient Log logger = LogFactory.getLog(getClass());

    private Twitter twitter;

    private TwitterStream stream;

    @Configurable
    private String consumerKey;

    @Configurable
    private String consumerSecret;

    @Optional
    @Configurable
    private String accessKey;

    @Optional
    @Configurable
    private String accessSecret;

    @Optional
    @Configurable
    @Default("true")
    private boolean useSSL;

    @PostConstruct
    public void initialise()
    {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setUseSSL(useSSL);

        HttpClientHiddenConstructionArgument.setUseMule(true);
        twitter = new TwitterFactory(cb.build()).getInstance();

        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        if (accessKey != null)
        {
            twitter.setOAuthAccessToken(new AccessToken(accessKey, accessSecret));
        }
    }

    /**
     * Returns tweets that match a specified query.
     * <p>
     * This method calls http://search.twitter.com/search.json
     *
     * @param query The search query.
     * @return
     * @throws TwitterException
     */
    @Processor
    public QueryResult search(String query) throws TwitterException
    {
        return twitter.search(new Query(query));
    }

    /**
     * Returns the 20 most recent statuses from non-protected users who have set a
     * custom user icon. The public timeline is cached for 60 seconds and requesting
     * it more often than that is unproductive and a waste of resources. <br>
     * This method calls http://api.twitter.com/1/statuses/public_timeline
     *
     * @return list of statuses of the Public Timeline
     * @throws twitter4j.TwitterException when Twitter service or network is
     *             unavailable
     * @see <a href="http://dev.twitter.com/doc/get/statuses/public_timeline">GET
     *      statuses/public_timeline | dev.twitter.com</a>
     */
    @Processor
    public ResponseList<Status> getPublicTimeline() throws TwitterException
    {
        return twitter.getPublicTimeline();
    }

    /**
     * Returns the 20 most recent statuses, including retweets, posted by the
     * authenticating user and that user's friends. This is the equivalent of
     * /timeline/home on the Web.<br>
     * Usage note: This home_timeline call is identical to statuses/friends_timeline,
     * except that home_timeline also contains retweets, while
     * statuses/friends_timeline does not for backwards compatibility reasons. In a
     * future version of the API, statuses/friends_timeline will be deprected and
     * replaced by home_timeline. <br>
     * This method calls http://api.twitter.com/1/statuses/home_timeline
     *
     * @param page controls pagination. Supports since_id, max_id, count and page
     *            parameters.
     * @return list of the home Timeline
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a href="http://dev.twitter.com/doc/get/statuses/home_timeline">GET
     *      statuses/home_timeline | dev.twitter.com</a>
     */
    @Processor
    public ResponseList<Status> getHomeTimeline(@Default(value = "1") @Optional int page,
                                                @Default(value = "100") @Optional int count,
                                                @Default(value = "-1") @Optional int sinceId)
        throws TwitterException
    {
        return twitter.getHomeTimeline(getPaging(page, count, sinceId));
    }

    /**
     * Returns the 20 most recent statuses posted from the authenticating user. It's
     * also possible to request another user's timeline via the id parameter.<br>
     * This is the equivalent of the Web / page for your own user, or the profile
     * page for a third party.<br>
     * For backwards compatibility reasons, retweets are stripped out of the
     * user_timeline when calling in XML or JSON (they appear with 'RT' in RSS and
     * Atom). If you'd like them included, you can merge them in from statuses
     * retweeted_by_me.<br>
     * <br>
     * This method calls http://api.twitter.com/1/statuses/user_timeline.json
     *
     * @param screenName specifies the screen name of the user for whom to return the
     *            user_timeline
     * @return list of the user Timeline
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a href="http://dev.twitter.com/doc/get/statuses/user_timeline">GET
     *      statuses/user_timeline | dev.twitter.com</a>
     */
    @Processor
    public ResponseList<Status> getUserTimelineByScreenName(String screenName,
                                                            @Default(value = "1") @Optional int page,
                                                            @Default(value = "100") @Optional int count,
                                                            @Default(value = "-1") @Optional int sinceId)
        throws TwitterException
    {
        return twitter.getUserTimeline(screenName, getPaging(page, count, sinceId));
    }

    /**
     * Returns the 20 most recent statuses posted from the authenticating user. It's
     * also possible to request another user's timeline via the id parameter.<br>
     * This is the equivalent of the Web / page for your own user, or the profile
     * page for a third party.<br>
     * For backwards compatibility reasons, retweets are stripped out of the
     * user_timeline when calling in XML or JSON (they appear with 'RT' in RSS and
     * Atom). If you'd like them included, you can merge them in from statuses
     * retweeted_by_me.<br>
     * <br>
     * This method calls http://api.twitter.com/1/statuses/user_timeline.json
     *
     * @param userId specifies the ID of the user for whom to return the
     *            user_timeline
     * @param paging controls pagination. Supports since_id, max_id, count and page
     *            parameters.
     * @return list of the user Timeline
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a href="http://dev.twitter.com/doc/get/statuses/user_timeline">GET
     *      statuses/user_timeline | dev.twitter.com</a>
     */
    @Processor
    public ResponseList<Status> getUserTimelineByUserId(long userId,
                                                        @Default(value = "1") @Optional int page,
                                                        @Default(value = "100") @Optional int count,
                                                        @Default(value = "-1") @Optional int sinceId)
        throws TwitterException
    {
        return twitter.getUserTimeline(userId, getPaging(page, count, sinceId));
    }

    protected Paging getPaging(int page, int count, long sinceId)
    {
        Paging paging = new Paging(page, count);
        if (sinceId > 0)
        {
            paging.setSinceId(sinceId);
        }
        return paging;
    }

    /**
     * Returns the 20 most recent statuses posted from the authenticating user. It's
     * also possible to request another user's timeline via the id parameter.<br>
     * This is the equivalent of the Web / page for your own user, or the profile
     * page for a third party.<br>
     * For backwards compatibility reasons, retweets are stripped out of the
     * user_timeline when calling in XML or JSON (they appear with 'RT' in RSS and
     * Atom). If you'd like them included, you can merge them in from statuses
     * retweeted_by_me.<br>
     * <br>
     * This method calls http://api.twitter.com/1/statuses/user_timeline.json
     *
     * @param page
     * @param count
     * @param sinceId
     * @return list of the user Timeline
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a href="http://dev.twitter.com/doc/get/statuses/user_timeline">GET
     *      statuses/user_timeline | dev.twitter.com</a>
     */
    @Processor
    public ResponseList<Status> getUserTimeline(@Default(value = "1") @Optional int page,
                                                @Default(value = "100") @Optional int count,
                                                @Default(value = "-1") @Optional int sinceId)
        throws TwitterException
    {
        return twitter.getUserTimeline(getPaging(page, count, sinceId));
    }

    /**
     * Returns the 20 most recent mentions (status containing @username) for the
     * authenticating user. <br>
     * This method calls http://api.twitter.com/1/statuses/mentions
     *
     * @param page
     * @param count
     * @param sinceId
     * @param paging controls pagination. Supports since_id, max_id, count and page
     *            parameters.
     * @return the 20 most recent replies
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a href="http://dev.twitter.com/doc/get/statuses/mentions">GET
     *      statuses/mentions | dev.twitter.com</a>
     */
    @Processor
    public ResponseList<Status> getMentions(@Default(value = "1") @Optional int page,
                                            @Default(value = "100") @Optional int count,
                                            @Default(value = "-1") @Optional int sinceId)
        throws TwitterException
    {
        return twitter.getMentions(getPaging(page, count, sinceId));
    }

    /**
     * Returns the 20 most recent retweets posted by the authenticating user. <br>
     * This method calls http://api.twitter.com/1/statuses/retweeted_by_me
     *
     * @return the 20 most recent retweets posted by the authenticating user
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a href="http://dev.twitter.com/doc/get/statuses/retweeted_by_me">GET
     *      statuses/retweeted_by_me | dev.twitter.com</a>
     */
    @Processor
    public ResponseList<Status> getRetweetedByMe(@Default(value = "1") @Optional int page,
                                                 @Default(value = "100") @Optional int count,
                                                 @Default(value = "-1") @Optional int sinceId)
        throws TwitterException
    {
        return twitter.getRetweetedByMe(getPaging(page, count, sinceId));
    }

    /**
     * Returns the 20 most recent retweets posted by the authenticating user's
     * friends. <br>
     * This method calls http://api.twitter.com/1/statuses/retweeted_to_me
     *
     * @param paging controls pagination. Supports since_id, max_id, count and page
     *            parameters.
     * @return the 20 most recent retweets posted by the authenticating user's
     *         friends.
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a href="http://dev.twitter.com/doc/get/statuses/retweeted_to_me">GET
     *      statuses/retweeted_to_me | dev.twitter.com</a>
     */
    @Processor
    public ResponseList<Status> getRetweetedToMe(@Default(value = "1") @Optional int page,
                                                 @Default(value = "100") @Optional int count,
                                                 @Default(value = "-1") @Optional int sinceId)
        throws TwitterException
    {
        return twitter.getRetweetedToMe(getPaging(page, count, sinceId));
    }

    /**
     * Returns the 20 most recent tweets of the authenticated user that have been
     * retweeted by others. <br>
     * This method calls http://api.twitter.com/1/statuses/retweets_of_me
     *
     * @param paging controls pagination. Supports since_id, max_id, count and page
     *            parameters.
     * @return the 20 most recent tweets of the authenticated user that have been
     *         retweeted by others.
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a href="http://dev.twitter.com/doc/get/statuses/retweets_of_me">GET
     *      statuses/retweets_of_me | dev.twitter.com</a>
     */
    @Processor
    public ResponseList<Status> getRetweetsOfMe(@Default(value = "1") @Optional int page,
                                                @Default(value = "100") @Optional int count,
                                                @Default(value = "-1") @Optional int sinceId)
        throws TwitterException
    {
        return twitter.getRetweetsOfMe(getPaging(page, count, sinceId));
    }

    /**
     * Returns the 20 most recent retweets posted by users the specified user
     * follows. This method is identical to statuses/retweeted_to_me except you can
     * choose the user to view. <br>
     * This method has not been finalized and the interface is subject to change in
     * incompatible ways. <br>
     * This method calls http://api.twitter.com/1/statuses/retweeted_to_user
     *
     * @param screenName the user to view
     * @param paging controls pagination. Supports since_id, max_id, count and page
     *            parameters.
     * @return the 20 most recent retweets posted by the authenticating user's
     *         friends.
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a
     *      href="http://groups.google.com/group/twitter-api-announce/msg/34909da7c399169e">#newtwitter
     *      and the API - Twitter API Announcements | Google Group</a>
     */
    @Processor
    public ResponseList<Status> getRetweetedToUserByScreenName(String screenName,
                                                               @Default(value = "1") @Optional int page,
                                                               @Default(value = "100") @Optional int count,
                                                               @Default(value = "-1") @Optional int sinceId)
        throws TwitterException
    {
        return twitter.getRetweetedToUser(screenName, getPaging(page, count, sinceId));
    }

    /**
     * Returns the 20 most recent retweets posted by users the specified user
     * follows. This method is identical to statuses/retweeted_to_me except you can
     * choose the user to view. <br>
     * This method has not been finalized and the interface is subject to change in
     * incompatible ways. <br>
     * This method calls http://api.twitter.com/1/statuses/retweeted_to_user
     *
     * @param userId the user to view
     * @param paging controls pagination. Supports since_id, max_id, count and page
     *            parameters.
     * @return the 20 most recent retweets posted by the authenticating user's
     *         friends.
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a
     *      href="http://groups.google.com/group/twitter-api-announce/msg/34909da7c399169e">#newtwitter
     *      and the API - Twitter API Announcements | Google Group</a>
     */
    @Processor
    public ResponseList<Status> getRetweetedToUserByUserId(long userId,
                                                           @Default(value = "1") @Optional int page,
                                                           @Default(value = "100") @Optional int count,
                                                           @Default(value = "-1") @Optional int sinceId)
        throws TwitterException
    {
        return twitter.getRetweetedToUser(userId, getPaging(page, count, sinceId));
    }

    /**
     * Returns the 20 most recent retweets posted by the specified user. This method
     * is identical to statuses/retweeted_by_me except you can choose the user to
     * view. <br>
     * This method has not been finalized and the interface is subject to change in
     * incompatible ways. <br>
     * This method calls http://api.twitter.com/1/statuses/retweeted_by_user
     *
     * @param screenName the user to view
     * @param paging controls pagination. Supports since_id, max_id, count and page
     *            parameters.
     * @return the 20 most recent retweets posted by the authenticating user
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a
     *      href="http://groups.google.com/group/twitter-api-announce/msg/34909da7c399169e">#newtwitter
     *      and the API - Twitter API Announcements | Google Group</a>
     */
    @Processor
    public ResponseList<Status> getRetweetedByUserByScreenName(String screenName,
                                                               @Default(value = "1") @Optional int page,
                                                               @Default(value = "100") @Optional int count,
                                                               @Default(value = "-1") @Optional int sinceId)
        throws TwitterException
    {
        return twitter.getRetweetedByUser(screenName, getPaging(page, count, sinceId));
    }

    /**
     * Returns the 20 most recent retweets posted by the specified user. This method
     * is identical to statuses/retweeted_by_me except you can choose the user to
     * view. <br>
     * This method has not been finalized and the interface is subject to change in
     * incompatible ways. <br>
     * This method calls http://api.twitter.com/1/statuses/retweeted_by_user
     *
     * @param userId the user to view
     * @param paging controls pagination. Supports since_id, max_id, count and page
     *            parameters.
     * @return the 20 most recent retweets posted by the authenticating user
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a
     *      href="http://groups.google.com/group/twitter-api-announce/msg/34909da7c399169e">#newtwitter
     *      and the API - Twitter API Announcements | Google Group</a>
     */
    @Processor
    public ResponseList<Status> getRetweetedByUserByUserId(long userId,
                                                           @Default(value = "1") @Optional int page,
                                                           @Default(value = "100") @Optional int count,
                                                           @Default(value = "-1") @Optional int sinceId)
        throws TwitterException
    {
        return twitter.getRetweetedByUser(userId, getPaging(page, count, sinceId));
    }

    /**
     * Returns a single status, specified by the id parameter below. The status's
     * author will be returned inline. <br>
     * This method calls http://api.twitter.com/1/statuses/show
     *
     * @param id the numerical ID of the status you're trying to retrieve
     * @return a single status
     * @throws twitter4j.TwitterException when Twitter service or network is
     *             unavailable
     * @see <a href="http://dev.twitter.com/doc/get/statuses/show/:id">GET
     *      statuses/show/:id | dev.twitter.com</a>
     */
    @Processor
    public Status showStatus(long id) throws TwitterException
    {
        return twitter.showStatus(id);
    }

    /**
     * Answers user information for the authenticated user
     *
     * @return a User object
     * @throws TwitterException
     */
    @Processor
    public User showUser() throws TwitterException {
        return twitter.showUser(twitter.getId());
    }

    /**
     * Updates the authenticating user's status. A status update with text identical
     * to the authenticating user's text identical to the authenticating user's
     * current status will be ignored to prevent duplicates. <br>
     * This method calls http://api.twitter.com/1/statuses/update
     *
     * @param status the text of your status update
     * @return the latest status
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a href="http://dev.twitter.com/doc/post/statuses/update">POST
     *      statuses/update | dev.twitter.com</a>
     */
    @Processor
    public Status updateStatus(String status,
                               @Optional @Default(value = "-1") long inReplyTo,
                               @Optional GeoLocation geoLocation) throws TwitterException
    {
        StatusUpdate update = new StatusUpdate(status);
        if (inReplyTo > 0)
        {
            update.setInReplyToStatusId(inReplyTo);
        }
        if (geoLocation != null)
        {
            update.setLocation(geoLocation);
        }

        return twitter.updateStatus(status);
    }

    /**
     * Destroys the status specified by the required ID parameter.<br>
     * Usage note: The authenticating user must be the author of the specified
     * status. <br>
     * This method calls http://api.twitter.com/1/statuses/destroy
     *
     * @param statusId The ID of the status to destroy.
     * @return the deleted status
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a href="http://dev.twitter.com/doc/post/statuses/destroy/:id">POST
     *      statuses/destroy/:id | dev.twitter.com</a>
     */
    @Processor
    public Status destroyStatus(long statusId) throws TwitterException
    {
        return twitter.destroyStatus(statusId);
    }

    /**
     * Retweets a tweet. Returns the original tweet with retweet details embedded. <br>
     * This method calls http://api.twitter.com/1/statuses/retweet
     *
     * @param statusId The ID of the status to retweet.
     * @return the retweeted status
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a href="http://dev.twitter.com/doc/post/statuses/retweet/:id">POST
     *      statuses/retweet/:id | dev.twitter.com</a>
     */
    @Processor
    public Status retweetStatus(long statusId) throws TwitterException
    {
        return twitter.retweetStatus(statusId);
    }

    /**
     * Returns up to 100 of the first retweets of a given tweet. <br>
     * This method calls http://api.twitter.com/1/statuses/retweets
     *
     * @param statusId The numerical ID of the tweet you want the retweets of.
     * @return the retweets of a given tweet
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a href="http://dev.twitter.com/doc/get/statuses/retweets/:id">Tweets
     *      Resources > statuses/retweets/:id</a>
     * @since Twitter4J 2.0.10
     */
    @Processor
    public ResponseList<Status> getRetweets(long statusId) throws TwitterException
    {
        return twitter.getRetweets(statusId);
    }

    /**
     * Show user objects of up to 100 members who retweeted the status. <br>
     * This method calls http://api.twitter.com/1/statuses/:id/retweeted_by
     *
     * @param statusId The ID of the status you want to get retweeters of
     * @param paging controls pagination. Supports count and page parameters.
     * @return the list of users who retweeted your status
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a href="http://dev.twitter.com/doc/get/statuses/:id/retweeted_by">GET
     *      statuses/:id/retweeted_by | dev.twitter.com</a>
     */
    @Processor
    public ResponseList<User> getRetweetedBy(long statusId,

                                             @Default(value = "1") @Optional int page,
                                             @Default(value = "100") @Optional int count,
                                             @Default(value = "-1") @Optional int sinceId)
        throws TwitterException
    {
        return twitter.getRetweetedBy(statusId, getPaging(page, count, sinceId));
    }

    /**
     * Show user ids of up to 100 users who retweeted the status represented by id <br />
     * This method calls
     * http://api.twitter.com/1/statuses/:id/retweeted_by/ids.format
     *
     * @param statusId The ID of the status you want to get retweeters of
     * @return IDs of users who retweeted the stats
     * @param paging controls pagination. Supports count and page parameters.
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a
     *      href="http://dev.twitter.com/doc/get/statuses/:id/retweeted_by/ids">GET
     *      statuses/:id/retweeted_by/ids | dev.twitter.com</a>
     */
    @Processor
    public IDs getRetweetedByIDs(long statusId,
                                 @Default(value = "1") @Optional int page,
                                 @Default(value = "100") @Optional int count,
                                 @Default(value = "-1") @Optional int sinceId) throws TwitterException
    {
        return twitter.getRetweetedByIDs(statusId, getPaging(page, count, sinceId));
    }

    /**
     * Set the OAuth verifier after it has been retrieved via requestAuthorization.
     * The resulting access tokens will be logged to the INFO level so the user can
     * reuse them as part of the configuration in the future if desired.
     *
     * @param oauthVerifier The OAuth verifier code from Twitter.
     * @throws TwitterException
     */
    @Processor
    public void setOauthVerifier(String oauthVerifier) throws TwitterException
    {
        AccessToken accessToken = twitter.getOAuthAccessToken(oauthVerifier);
        logger.info("Got OAuth access tokens. Access token:" + accessToken.getToken()
                    + " Access token secret:" + accessToken.getTokenSecret());
    }

    /**
     * Start the OAuth request authorization process.
     *
     * @param callbackUrl
     * @return The user authorization URL.
     * @throws TwitterException
     */
    @Processor
    public String requestAuthorization(@Optional String callbackUrl) throws TwitterException
    {
        RequestToken token = twitter.getOAuthRequestToken();

        return token.getAuthorizationURL();
    }

    /**
     * Search for places (cities and neighborhoods) that can be attached to a
     * statuses/update. Given a latitude and a longitude, return a list of all the
     * valid places that can be used as a place_id when updating a status.
     * Conceptually, a query can be made from the user's location, retrieve a list of
     * places, have the user validate the location he or she is at, and then send the
     * ID of this location up with a call to statuses/update.<br>
     * There are multiple granularities of places that can be returned --
     * "neighborhoods", "cities", etc. At this time, only United States data is
     * available through this method.
     *
     *
     * {@code <reverse-geo-code ip="#[header:ip]" />}
     *
     * @param latitude latitude coordinate. Mandatory if ip is not specified
     * @param longitude longitude coordinate.
     * @param ip the ip. Mandatory if coordinates are not specified
     * @return a ResponseList of Place
     * @throws TwitterException
     */
    @Processor
    public ResponseList<Place> reverseGeoCode(@Optional Double latitude,
                                              @Optional Double longitude,
                                              @Optional String ip) throws TwitterException
    {
        return twitter.reverseGeoCode(createQuery(latitude, longitude, ip));
    }

    /**
     * Search for places that can be attached to a statuses/update. Given a latitude
     * and a longitude pair, or and IP address, this request will return a list of
     * all the valid places that can be used as the place_id when updating a status.
     *
     * {@code <search-places latitude="#[header:latitude]" longitude="#[header:longitude]" />}
     *
     * @param latitude latitude coordinate. Mandatory if ip is not specified
     * @param longitude longitude coordinate.
     * @param ip the ip. Mandatory if coordinates are not specified
     * @return a ResponseList of Place
     * @throws TwitterException
     */
    @Processor
    public ResponseList<Place> searchPlaces(@Optional Double latitude,
                                            @Optional Double longitude,
                                            @Optional String ip) throws TwitterException
    {
        return twitter.searchPlaces(createQuery(latitude, longitude, ip));
    }

    private GeoQuery createQuery(Double latitude, Double longitude, String ip)
    {
        if (ip == null)
        {
            return new GeoQuery(new GeoLocation(latitude, longitude));
        }
        return new GeoQuery(ip);
    }

    /**
     * Find out more details of a place that was returned from the reverseGeoCode
     * operation.
     *
     * {@code <get-geo-details id="#[header:geocodeId]"/>}
     *
     * @param id The ID of the location to query about.
     * @return a Place
     * @throws TwitterException
     */
    @Processor
    public Place getGeoDetails(String id) throws TwitterException
    {
        return twitter.getGeoDetails(id);
    }

    /**
     * Creates a new place at the given latitude and longitude.
     *
     * {@code <create-place
     *      token="#[header:token]"
     *      containedWithin="#[header:containedWithin]"
     *      name="#[header:placeName]"
     *      latitude="#[header:latitude]"
     *      longitude="#[header:longitude]"
     *      streetAddress="[header:address]"/>}
     *
     * @param name The name a place is known as.
     * @param containedWithin The place_id within which the new place can be found.
     *            Try and be as close as possible with the containing place. For
     *            example, for a room in a building, set the contained_within as the
     *            building place_id.
     * @param token The token found in the response from geo/similar_places.
     * @param latitude The latitude the place is located at.
     * @param longitude The longitude the place is located at.
     * @param streetAddress optional: This parameter searches for places which have
     *            this given street address. There are other well-known, and
     *            application specific attributes available. Custom attributes are
     *            also permitted. Learn more about Place Attributes.
     * @return a new Place
     * @throws TwitterException
     */
    @Processor
    public Place createPlace(String name,
                             String containedWithin,
                             String token,
                             Double latitude,
                             Double longitude,
                             @Optional String streetAddress) throws TwitterException
    {
        return twitter.createPlace(name, containedWithin, token, new GeoLocation(latitude, longitude),
            streetAddress);
    }

    /**
     * Returns the current top 10 trending topics on Twitter. The response includes
     * the time of the request, the name of each trending topic, and query used on
     * Twitter Search results page for that topic.
     *
     * {@code <get-current-trends excludeHashtags="true" />}
     *
     * @param excludeHashtags whether all hashtags shoudl be removed from the trends list.
     * @return a Trends object
     * @throws TwitterException
     */
    @Processor
    public Trends getCurrentTrends(@Optional @Default("false") boolean excludeHashtags)
        throws TwitterException
    {
        return twitter.getCurrentTrends(excludeHashtags);
    }

    /**
     * Returns the top 20 trending topics for each hour in a given day.
     *
     * {@code <get-daily-trends />}
     *
     * @param date starting date of daily trends. If no date is specified, current
     *            date is used
     * @param excludeHashTags whether hashtags should be excluded
     * @return a list of Trends objects
     * @throws TwitterException
     */
    @Processor
    public List<Trends> getDailyTrends(@Optional Date date,
                                       @Optional @Default("false") boolean excludeHashTags)
        throws TwitterException
    {
        return twitter.getDailyTrends(date, excludeHashTags);
    }

    /**
     * Returns the top ten topics that are currently trending on Twitter. The
     * response includes the time of the request, the name of each trend, and the url
     * to the Twitter Search results page for that topic.
     *
     * {@code <get-trends/>}
     *
     * @return a Trends object
     * @throws TwitterException
     */
    @Processor
    public Trends getTrends() throws TwitterException
    {
        return twitter.getTrends();
    }

    /**
     * Returns the top 30 trending topics for each day in a given week.
     *
     * {@code  <get-weekly-trends/>}
     *
     * @param date starting date of daily trends. If no date is specified, current
     *            date is used
     * @param excludeHashTags if all hashtags should be removed from the trends list.
     * @return a list of Trends objects
     * @throws TwitterException
     */
    @Processor
    public List<Trends> getWeeklyTrends(@Optional Date date,
                                        @Optional @Default("false") boolean excludeHashTags)
        throws TwitterException
    {
        return twitter.getWeeklyTrends(date, excludeHashTags);
    }



    /**
     * Asynchronously retrieves public statuses that match one or more filter predicates.
     *
     * At least a keyword or userId must be specified. Multiple parameters may be
     * specified.
     *
     * Placing long parameters in the URL may cause the request to be rejected for excessive URL length.
     *
     * The default access level allows up to 200 track keywords and 400 follow userids.
     *
     * Only one Twitter stream can be consumed using the same credentials. As a consequence,
     * only one twitter stream can be consumed per connector instance.
     *
     * {@code <filtered-stream count="5">
     *      <keywords>
     *          <keyword>enterprise</keyword>
     *          <keyword>integration</keyword>
     *      </keywords>
     *      </filtered-stream>}
     *
     * @param count the number of previous statuses to stream before transitioning to the live stream.
     * @param userIds the user ids to follow
     * @param keywords the keywords to track
     * @param callback
     */
    @Source
    public void filteredStream(@Optional @Default("0") int count,
                               @Optional List<Long> userIds,
                               @Optional List<String> keywords,
                               final SourceCallback callback)
    {
        listenToStatues(callback).filter(new FilterQuery(count, toLongArray(userIds), toStringArray(keywords)));
    }

    /**
     * Asynchronously retrieves a random sample of all public statuses. The sample
     * size and quality varies depending on the account permissions
     *
     * The default access level provides a small proportion of the Firehose. The "Gardenhose"
     * access level provides a proportion more suitable for data mining
     * and research applications that desire a larger proportion to be
     * statistically significant sample.
     *
     * Only one Twitter stream can be consumed using the same credentials. As a consequence,
     * only one twitter stream can be consumed per connector instance.
     *
     * {@code <sample-stream/>}
     *
     * @param callback
     */
    @Source
    public void sampleStream(final SourceCallback callback)
    {
        listenToStatues(callback).sample();
    }

    /**
     * Asynchronously retrieves all public statuses. This stream is not generally
     * available - it requires special permissions and its usage is discouraged by
     * Twitter
     *
     * Only one Twitter stream can be consumed using the same credentials. As a consequence,
     * only one twitter stream can be consumed per connector instance.
     *
     * @param count
     * @param callback
     */
    @Source
    public void fireshorseStream(int count, final SourceCallback callback)
    {
        listenToStatues(callback).firehose(count);
    }

    /**
     * Asynchronously retrieves all statuses containing 'http:' and 'https:'. Like
     * Firehorse, its is not a generally available stream
     *
     * Only one Twitter stream can be consumed using the same credentials. As a consequence,
     * only one twitter stream can be consumed per connector instance.
     *
     * @param count
     * @param callback
     */
    @Source
    public void linkStream(int count, final SourceCallback callback)
    {
        listenToStatues(callback).links(count);
    }

    /**
     * Retrieves the following user updates notifications:<br/>
     * - New Statuses <br/>
     * - Block/Unblock events <br/>
     * - Follow events <br/>
     * - User profile updates <br/>
     * - Retweets <br/>
     * - List creation/deletion <br/>
     * - List member addition/remotion <br/>
     * - List subscription/unsubscription <br/>
     * - List updates <br/>
     * - Profile updates <br/>
     *
     * Such notifications are represented as org.mule.twitter.UserEvent objects
     *
     * Only one Twitter stream can be consumed using the same credentials. As a consequence,
     * only one twitter stream can be consumed per connector instance.
     *
     * {@code <user-stream>
     *      <keywords>
     *          <keyword>enterprise</keyword>
     *          <keyword>integration</keyword>
     *      </keywords>
     *      </user-stream>}
     *
     * @param keywords the keywords to track for new statuses
     * @param callback
     */
    @Source
    public void userStream(List<String> keywords, final SourceCallback callback_)
    {
        initStream();
        final SoftCallback callback = new SoftCallback(callback_);
        stream.addListener(new UserStreamAdapter()
        {
            @Override
            public void onException(Exception ex)
            {
                logger.warn("An exception occured while processing user stream", ex);
            }

            @Override
            public void onStatus(Status status)
            {
                try {
                    callback.process(UserEvent.fromPayload(EventType.NEW_STATUS, status.getUser(), status));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }

            @Override
            public void onBlock(User source, User blockedUser)
            {
                try {
                    callback.process(UserEvent.fromTarget(EventType.BLOCK, source, blockedUser));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }

            @Override
            public void onFollow(User source, User followedUser)
            {
                try {
                    callback.process(UserEvent.fromTarget(EventType.FOLLOW, source, followedUser));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }

            @Override
            public void onRetweet(User source, User target, Status retweetedStatus)
            {
                try {
                    callback.process(UserEvent.from(EventType.RETWEET, source, target, retweetedStatus));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }

            @Override
            public void onUnblock(User source, User unblockedUser)
            {
                try {
                    callback.process(UserEvent.fromTarget(EventType.UNBLOCK, source, unblockedUser));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }

            @Override
            public void onUserListCreation(User listOwner, UserList list)
            {
                try {
                    callback.process(UserEvent.fromPayload(EventType.LIST_CREATION, listOwner, list));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }

            @Override
            public void onUserListDeletion(User listOwner, UserList list)
            {
                try {
                    callback.process(UserEvent.fromPayload(EventType.LIST_DELETION, listOwner, list));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }

            @Override
            public void onUserListMemberAddition(User addedMember, User listOwner, UserList list)
            {
                try {
                    callback.process(UserEvent.from(EventType.LIST_MEMBER_ADDITION, addedMember, listOwner, list));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }

            @Override
            public void onUserListMemberDeletion(User deletedMember, User listOwner, UserList list)
            {
                try {
                    callback.process(UserEvent.from(EventType.LIST_MEMBER_DELETION, deletedMember, listOwner,
                        list));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }

            @Override
            public void onUserListSubscription(User subscriber, User listOwner, UserList list)
            {
                try {
                    callback.process(UserEvent.from(EventType.LIST_SUBSCRIPTION, subscriber, listOwner, list));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }

            @Override
            public void onUserListUnsubscription(User subscriber, User listOwner, UserList list)
            {
                try {
                    callback.process(UserEvent.from(EventType.LIST_UNSUBSCRIPTION, subscriber, listOwner, list));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }

            @Override
            public void onUserListUpdate(User listOwner, UserList list)
            {
                try {
                    callback.process(UserEvent.fromPayload(EventType.LIST_UPDATE, listOwner, list));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }

            @Override
            public void onUserProfileUpdate(User updatedUser)
            {
                try {
                    callback.process(UserEvent.fromPayload(EventType.PROFILE_UPDATE, updatedUser, null));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
        stream.user(toStringArray(keywords));
    }


    /**
     * Asynchronously retrieves statutes for a set of supplied user's ids.
     * Site Streams are a beta service, so refer always to latest twitter documentation about them.
     *
     * Only one Twitter stream can be consumed using the same credentials. As a consequence,
     * only one twitter stream can be consumed per connector instance.
     *
     * @param userIds ids of users to include in the stream
     * @param withFollowings withFollowings whether to receive status updates from people following
     * @param callback
     */
    @Source
    public void siteStream(List<Long> userIds,
                           @Optional @Default("false") boolean withFollowings,
                           final SourceCallback callback_)
    {
        initStream();
        final SoftCallback callback = new SoftCallback(callback_);
        stream.addListener(new SiteStreamsAdapter()
        {

            @Override
            public void onException(Exception ex)
            {
                logger.warn("An exception occured while processing site stream", ex);
            }

            @Override
            public void onStatus(long forUser, Status status)
            {
                try {
                    callback.process(status);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }

        });
        stream.site(withFollowings, toLongArray(userIds));
    }
    /**
     * Sends a new direct message to the specified user from the authenticating user.
     * Requires both the user and text parameters below. The text will be trimmed if
     * the length of the text is exceeding 140 characters.
     * @param screenName The screen name of the user to whom send the direct message
     * @param message The text of your direct message
     * @return
     * @throws TwitterException
     */
    @Processor
    public DirectMessage sendDirectMessageByScreenName(String screenName, String message) throws TwitterException
    {
        return twitter.sendDirectMessage(screenName, message);
    }

    /**
     * Sends a new direct message to the specified user from the authenticating user.
     * Requires both the user and text parameters below. The text will be trimmed if
     * the length of the text is exceeding 140 characters.
     * @param userId The user ID of the user to whom send the direct message
     * @param message The text of your direct message
     * @return
     * @throws TwitterException
     */
    @Processor
    public DirectMessage sendDirectMessageByUserId(long userId, String message) throws TwitterException
    {
        return twitter.sendDirectMessage(userId, message);
    }

    private void initStream()
    {
        if (stream != null)
        {
            throw new IllegalStateException("Only one stream can be consumed per twitter account");
        }
        this.stream = newStream();
    }

    private String[] toStringArray(List<String> list)
    {
        if (list == null)
        {
            return null;
        }
        return list.toArray(new String[list.size()]);
    }

    private TwitterStream listenToStatues(final SourceCallback callback_)
    {
        initStream();
        final SoftCallback callback = new SoftCallback(callback_);
        stream.addListener(new StatusAdapter()
        {
            @Override
            public void onException(Exception ex)
            {
                logger.warn("An exception occured while processing status stream", ex);
            }

            @Override
            public void onStatus(Status status)
            {
                try {
                    callback.process(status);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
        return stream;
    }

    private TwitterStream newStream()
    {
        ConfigurationBuilder cb = new ConfigurationBuilder()
            .setUseSSL(useSSL)
            .setOAuthConsumerKey(consumerKey)
            .setOAuthConsumerSecret(consumerSecret)
            .setStreamBaseURL("https://stream.twitter.com/1/")
            .setSiteStreamBaseURL("https://sitestream.twitter.com/2b/");

        if (accessKey != null)
        {
            cb.setOAuthAccessToken(accessKey).setOAuthAccessTokenSecret(accessSecret);
        }

        HttpClientHiddenConstructionArgument.setUseMule(false);
        TwitterStream stream = new TwitterStreamFactory(cb.build()).getInstance();
        return stream;
    }

    private long[] toLongArray(List<Long> longList)
    {
        if (longList == null)
        {
            return null;
        }
        long[] ls = new long[longList.size()];
        for (int i = 0; i < longList.size(); i++)
        {
            ls[i] = longList.get(i);
        }
        return ls;
    }

    public Twitter getTwitterClient()
    {
        return twitter;
    }

    public boolean getUseSSL()
    {
        return useSSL;
    }

    public void setUseSSL(boolean useSSL)
    {
        this.useSSL = useSSL;
    }

    public void setAccessKey(String accessToken)
    {
        this.accessKey = accessToken;
    }

    public void setAccessSecret(String accessTokenSecret)
    {
        this.accessSecret = accessTokenSecret;
    }

    public void setConsumerKey(String consumerKey)
    {
        this.consumerKey = consumerKey;
    }

    public void setConsumerSecret(String consumerSecret)
    {
        this.consumerSecret = consumerSecret;
    }

    @Override
    public void setMuleContext(MuleContext context)
    {
        MuleHttpClient.setMuleContext(context);
    }

    static final class SoftCallback implements SourceCallback {
        private final SourceCallback callback;

        public SoftCallback(SourceCallback callback)
        {
            this.callback = callback;
        }

        @Override
        public Object process(Object payload)
        {
            try
            {
                return callback.process(payload);
            }
            catch (Exception e)
            {
                throw new UnhandledException(e);
            }
        }

        public Object process(Object payload, Map<String, Object> properties) throws Exception {
            try
            {
                return callback.process(payload);
            }
            catch (Exception e)
            {
                throw new UnhandledException(e);
            }
        }


    }

}
