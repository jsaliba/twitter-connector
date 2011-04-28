/*
 * $Id: TwitterTimelineIBean.java 297 2011-04-12 00:12:01Z dzapata $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.ibeans.twitter;

import org.mule.tools.cloudconnect.annotations.Operation;

import org.ibeans.api.channel.HTTP;

import org.ibeans.annotation.Call;
import org.ibeans.annotation.param.Optional;
import org.ibeans.annotation.param.PropertyParam;
import org.ibeans.annotation.param.UriParam;
import org.ibeans.api.CallException;

/**
 * Methods for retrieving Twitter timelines
 */
public interface TwitterTimelineIBean extends TwitterBase
{
    /**
     * Returns the 20 most recent statuses from non-protected users who have set a custom user icon. The public timeline
     * is cached for 60 seconds so requesting it more often than that is a waste of resources.
     * Formats: xml, json, rss, atom
     * Requires Authentication: false
     * API rate limited: true
     *
     * @param <T> The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *            {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *            {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML, JSON, ATOM and RSS, valid values are {@link String},
     *            {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}, {@link org.apache.abdera.model.Feed}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/statuses/public_timeline.{format}", properties = {HTTP.GET, HTTP.FOLLOW_REDIRECTS})
    public <T> T getPublicTimeline() throws CallException;

    /**
     * Returns the 20 most recent statuses posted by the authenticating user and that user's friends. This is the equivalent of
     * /timeline/home on the Web.
     * Formats: xml, json, rss, atom
     * Requires Authentication: true
     * API rate limited: true
     *
     * @param <T> The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *            {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *            {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML, JSON, ATOM and RSS, valid values are {@link String},
     *            {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}, {@link org.apache.abdera.model.Feed}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/statuses/friends_timeline.{format}", properties = {HTTP.GET, HTTP.FOLLOW_REDIRECTS})
    public <T> T getFriendsTimeline() throws CallException;

    /**
     * Returns the 20 most recent statuses posted by the authenticating user and that user's friends. This is the equivalent of
     * /timeline/home on the Web.
     * Formats: xml, json, rss, atom
     * Requires Authentication: true
     * API rate limited: true
     *
     * @param <T> The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *            {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *            {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML, JSON, ATOM and RSS, valid values are {@link String},
     *            {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}, {@link org.apache.abdera.model.Feed}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/statuses/friends_timeline.{format}", properties = {HTTP.GET, HTTP.FOLLOW_REDIRECTS})
    public <T> T getFriendsTimeline(@Optional @PropertyParam("oauth.access.token") String accessToken,
                                    @Optional @PropertyParam("oauth.access.secret") String accessSecret) throws CallException;   
   
    /**
     * Returns the most recent statuses posted by the authenticating user and that user's friends. This is the equivalent of
     * /timeline/home on the Web.
     * Formats: xml, json, rss, atom
     * Requires Authentication: true
     * API rate limited: true
     *
     * @param count   Specifies the number of statuses to retrieve. May not be greater than 200. Optional
     * @param sinceId Returns only statuses with an ID greater than (that is, more recent than) the specified ID. Optional
     * @param maxId   Returns only statuses with an ID less than (that is, older than) or equal to the specified ID.
     * @param page    Specifies the page of results to retrieve. Optional
     * @param <T>     The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *                {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *                {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML, JSON, ATOM and RSS, valid values are {@link String},
     *                {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}, {@link org.apache.abdera.model.Feed}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Operation
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/statuses/friends_timeline.{format}?count={count}&since_id={since_id}&max_id={max_id}&page={page}", properties = {HTTP.GET, HTTP.FOLLOW_REDIRECTS})
    public <T> T getFriendsTimeline(@Optional @UriParam("count") Integer count, @Optional @UriParam("since_id") String sinceId, @Optional @UriParam("max_id") String maxId, @Optional @UriParam("page") Integer page) throws CallException;

    /**
     * Returns the most recent statuses posted by the authenticating user and that user's friends. This is the equivalent of
     * /timeline/home on the Web.
     * Formats: xml, json, rss, atom
     * Requires Authentication: true
     * API rate limited: true
     *
     * @param count   Specifies the number of statuses to retrieve. May not be greater than 200. Optional
     * @param sinceId Returns only statuses with an ID greater than (that is, more recent than) the specified ID. Optional
     * @param maxId   Returns only statuses with an ID less than (that is, older than) or equal to the specified ID.
     * @param page    Specifies the page of results to retrieve. Optional
     * @param <T>     The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *                {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *                {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML, JSON, ATOM and RSS, valid values are {@link String},
     *                {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}, {@link org.apache.abdera.model.Feed}.
     * @param accessToken the oauth user access key.           
     * @param accessSecret the oauth user access secret.           
     *                
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/statuses/friends_timeline.{format}?count={count}&since_id={since_id}&max_id={max_id}&page={page}", properties = {HTTP.GET, HTTP.FOLLOW_REDIRECTS})
    public <T> T getFriendsTimeline(@Optional @UriParam("count") Integer count, @Optional @UriParam("since_id") String sinceId, @Optional @UriParam("max_id") String maxId, @Optional @UriParam("page") Integer page, @Optional @PropertyParam("oauth.access.token") String accessToken,
                                    @Optional @PropertyParam("oauth.access.secret") String accessSecret) throws CallException;

    
    /**
     * Returns the 20 most recent statuses posted from the authenticating user. It's also possible to request another
     * user's timeline via the id parameter. This is the equivalent of the Web /<user> page for your own user, or the
     * profile page for a third party.
     * Formats: xml, json, rss, atom
     * Requires Authentication: true
     * API rate limited: true
     *
     * @param userId    Specifies the ID of the user for whom to return the user_timeline. Optional
     * @param screenName Specifies the screen name of the user for whom to return the user_timeline. Optional
     * @param count Specifies the number of statuses to retrieve. May not be greater than 200.
     * @param <T>   The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *              {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *              {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML, JSON, ATOM and RSS, valid values are {@link String},
     *              {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}, {@link org.apache.abdera.model.Feed}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/1/statuses/user_timeline.{format}?user_id={user_id}&screen_name={screen_name}&count={count}", properties = {HTTP.GET})
    public <T> T getUserTimeline(@Optional @UriParam("user_id") String userId, @Optional @UriParam("screen_name") String screenName, @Optional @UriParam("count") Integer count) throws CallException;

    /**
     * Returns the 20 most recent statuses posted from the authenticating user. It's also possible to request another
     * user's timeline via the id parameter. This is the equivalent of the Web /<user> page for your own user, or the
     * profile page for a third party.
     * Formats: xml, json, rss, atom
     * Requires Authentication: true
     * API rate limited: true
     *
     * @param userId    Specifies the ID of the user for whom to return the user_timeline. Optional
     * @param screenName Specifies the screen name of the user for whom to return the user_timeline. Optional
     * @param count   Specifies the number of statuses to retrieve. May not be greater than 200. Optional
     * @param sinceId Returns only statuses with an ID greater than (that is, more recent than) the specified ID. Optional
     * @param maxId   Returns only statuses with an ID less than (that is, older than) or equal to the specified ID.
     * @param page    Specifies the page of results to retrieve. Optional
     * @param <T>     The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *                {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *                {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML, JSON, ATOM and RSS, valid values are {@link String},
     *                {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}, {@link org.apache.abdera.model.Feed}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Operation
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/statuses/user_timeline.{format}?user_id={user_id}&screen_name={screen_name}&count={count}&since_id={since_id}&max_id={max_id}&page={page}", properties = {HTTP.GET})
    public <T> T getUserTimeline(@Optional @UriParam("user_id") String userId, @Optional @UriParam("screen_name") String screenName, @Optional @UriParam("count") Integer count, @Optional @UriParam("since_id") String sinceId, @Optional @UriParam("max_id") String maxId, @Optional @UriParam("page") Integer page) throws Exception;

    /**
     * Returns the 20 most recent statuses posted from the authenticating user. It's also possible to request another
     * user's timeline via the id parameter. This is the equivalent of the Web /<user> page for your own user, or the
     * profile page for a third party.
     * Formats: xml, json, rss, atom
     * Requires Authentication: true
     * API rate limited: true
     *
     * @param userId    Specifies the ID of the user for whom to return the user_timeline. Optional
     * @param screenName Specifies the screen name of the user for whom to return the user_timeline. Optional
     * @param count   Specifies the number of statuses to retrieve. May not be greater than 200. Optional
     * @param sinceId Returns only statuses with an ID greater than (that is, more recent than) the specified ID. Optional
     * @param maxId   Returns only statuses with an ID less than (that is, older than) or equal to the specified ID.
     * @param page    Specifies the page of results to retrieve. Optional
     * @param <T>     The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *                {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *                {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML, JSON, ATOM and RSS, valid values are {@link String},
     *                {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}, {@link org.apache.abdera.model.Feed}.
     * @param accessToken the oauth user access key.           
     * @param accessSecret the oauth user access secret.          
     *                
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/statuses/user_timeline.{format}?user_id={user_id}&screen_name={screen_name}&count={count}&since_id={since_id}&max_id={max_id}&page={page}", properties = {HTTP.GET})
    public <T> T getUserTimeline(@Optional @UriParam("user_id") String userId, @Optional @UriParam("screen_name") String screenName, @Optional @UriParam("count") Integer count, @Optional @UriParam("since_id") String sinceId, @Optional @UriParam("max_id") String maxId, @Optional @UriParam("page") Integer page, @Optional @PropertyParam("oauth.access.token") String accessToken,
                                 @Optional @PropertyParam("oauth.access.secret") String accessSecret) throws Exception;

    /**
     * Returns the 20 most recent mentions (status containing @username) for the authenticating user.
     * Formats: xml, json, rss, atom
     * Requires Authentication: true
     * API rate limited: true
     *
     * @param <T> The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *            {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *            {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML, JSON, ATOM and RSS, valid values are {@link String},
     *            {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}, {@link org.apache.abdera.model.Feed}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/statuses/mentions.{format}", properties = {HTTP.GET, HTTP.FOLLOW_REDIRECTS})
    public <T> T getMentions() throws CallException;

    /**
     * Returns the most recent mentions (status containing @username) for the authenticating user.
     * Formats: xml, json, rss, atom
     * Requires Authentication: true
     * API rate limited: true
     *
     * @param count   Specifies the number of statuses to retrieve. May not be greater than 200. Optional
     * @param sinceId Returns only statuses with an ID greater than (that is, more recent than) the specified ID. Optional
     * @param maxId   Returns only statuses with an ID less than (that is, older than) or equal to the specified ID.
     * @param page    Specifies the page of results to retrieve. Optional
     * @param <T>     The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *                {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *                {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML, JSON, ATOM and RSS, valid values are {@link String},
     *                {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}, {@link org.apache.abdera.model.Feed}.
     *                
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Operation
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/statuses/mentions.{format}?count={count}&since_id={since_id}&max_id={max_id}&page={page}", properties = {HTTP.GET, HTTP.FOLLOW_REDIRECTS})
    public <T> T getMentions(@Optional @UriParam("count") Integer count, @Optional @UriParam("since_id") String sinceId, @Optional @UriParam("max_id") String maxId, @Optional @UriParam("page") Integer page) throws CallException;

    /**
     * Returns the most recent mentions (status containing @username) for the authenticating user.
     * Formats: xml, json, rss, atom
     * Requires Authentication: true
     * API rate limited: true
     *
     * @param count   Specifies the number of statuses to retrieve. May not be greater than 200. Optional
     * @param sinceId Returns only statuses with an ID greater than (that is, more recent than) the specified ID. Optional
     * @param maxId   Returns only statuses with an ID less than (that is, older than) or equal to the specified ID.
     * @param page    Specifies the page of results to retrieve. Optional
     * @param <T>     The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *                {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *                {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML, JSON, ATOM and RSS, valid values are {@link String},
     *                {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}, {@link org.apache.abdera.model.Feed}.
     * @param accessToken the oauth user access key.           
     * @param accessSecret the oauth user access secret.           
     * 
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/statuses/mentions.{format}?count={count}&since_id={since_id}&max_id={max_id}&page={page}", properties = {HTTP.GET, HTTP.FOLLOW_REDIRECTS})
    public <T> T getMentions(@Optional @UriParam("count") Integer count, @Optional @UriParam("since_id") String sinceId, @Optional @UriParam("max_id") String maxId, @Optional @UriParam("page") Integer page, @Optional @PropertyParam("oauth.access.token") String accessToken,
                             @Optional @PropertyParam("oauth.access.secret") String accessSecret) throws CallException;
   
    
}
