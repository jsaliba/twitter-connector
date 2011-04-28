/*
 * $Id: TwitterStatusIBean.java 297 2011-04-12 00:12:01Z dzapata $
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
import org.ibeans.annotation.param.BodyParam;
import org.ibeans.annotation.param.Optional;
import org.ibeans.annotation.param.PropertyParam;
import org.ibeans.annotation.param.UriParam;
import org.ibeans.api.CallException;

/**
 * Methods for creating, viewing and destroying status updates on Twitter
 */
public interface TwitterStatusIBean extends TwitterBase
{
    /**
     * Updates the authenticating user's status.  Requires the status parameter specified below.  Request must be a POST.
     * A status update with text identical to the authenticating user's current status will be ignored to prevent duplicates.
     * Formats: xml, json
     * Requires Authentication: true
     * API rate limited: false
     *
     * @param status The text of your status update. URL encode as necessary. Statuses over 140 characters will be forceably truncated.
     * @param accessToken the oauth user access key.           
     * @param accessSecret the oauth user access secret.  
     * @param <T>    The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *               {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *               {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML and JSON, valid values are {@link String},
     *               {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */    
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/statuses/update.{format}", properties = {HTTP.POST})
    public <T> T statusesUpdate(@BodyParam("status") String status,                               
                                @Optional @PropertyParam("oauth.access.token") String accessToken,
                                @Optional @PropertyParam("oauth.access.secret") String accessSecret)
        throws CallException;      

    /**
     * Updates the authenticating user's status.  Requires the status parameter specified below.  Request must be a POST.
     * A status update with text identical to the authenticating user's current status will be ignored to prevent duplicates.
     * Formats: xml, json
     * Requires Authentication: true
     * API rate limited: false
     *
     * @param status The text of your status update. URL encode as necessary. Statuses over 140 characters will be forceably truncated.
     * @param <T>    The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *               {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *               {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML and JSON, valid values are {@link String},
     *               {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/statuses/update.{format}?include_entities={include_entities}", properties = {HTTP.POST})
    public <T> T statusesUpdate(@BodyParam("status") String status) throws CallException;

    /**
     * Updates the authenticating user's status.  Requires the status parameter specified below.  Request must be a POST.
     * A status update with text identical to the authenticating user's current status will be ignored to prevent duplicates.
     * Formats: xml, json
     * Requires Authentication: true
     * API rate limited: false
     *
     * @param status  The text of your status update. URL encode as necessary. Statuses over 140 characters will be forceably truncated.
     * @param replyId The ID of an existing status that the update is in reply to. Optional. Note: This parameter will be
     *                ignored unless the author of the tweet this parameter references is mentioned within the status text. Therefore,
     *                you must include @username, where username is the author of the referenced tweet, within the update.
     * @param <T>     The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *                {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *                {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML and JSON, valid values are {@link String},
     *                {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/statuses/update.{format}?include_entities={include_entities}", properties = {HTTP.POST})
    public <T> T statusesUpdate(@BodyParam("status") String status, @Optional @BodyParam("in_reply_to_status_id") String replyId) throws CallException;

    /**
     * Updates the authenticating user's status.  Requires the status parameter specified below.  Request must be a POST.
     * A status update with text identical to the authenticating user's current status will be ignored to prevent duplicates.
     * Formats: xml, json
     * Requires Authentication: true
     * API rate limited: false
     *
     * @param status  The text of your status update. URL encode as necessary. Statuses over 140 characters will be forceably truncated.
     * @param replyId The ID of an existing status that the update is in reply to. Optional. Note: This parameter will be
     *                ignored unless the author of the tweet this parameter references is mentioned within the status text. Therefore,
     *                you must include @username, where username is the author of the referenced tweet, within the update.
     * @param <T>     The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *                {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *                {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML and JSON, valid values are {@link String},
     *                {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Operation
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/statuses/update.{format}?include_entities={include_entities}&lat={lat}&long={long}&place_id={place_id}&display_coordinates={display_coordinates}&trim_user={trim_user}", properties = {HTTP.POST})
    public <T> T statusesUpdate(@BodyParam("status") String status, @Optional @BodyParam("in_reply_to_status_id") String replyId,
    		@Optional @UriParam("lat") double latituide, @Optional @UriParam("long") double  longitude,
    		@Optional @UriParam("place_id") String placeId, @Optional @UriParam("display_coordinates") Boolean displayCoordinates,
    		@Optional @UriParam("trim_user") Boolean trimUser) throws CallException;
    
    /**
     * Updates the authenticating user's status.  Requires the status parameter specified below.  Request must be a POST.
     * A status update with text identical to the authenticating user's current status will be ignored to prevent duplicates.
     * Formats: xml, json
     * Requires Authentication: true
     * API rate limited: false
     *
     * @param status  The text of your status update. URL encode as necessary. Statuses over 140 characters will be forceably truncated.
     * @param replyId The ID of an existing status that the update is in reply to. Optional. Note: This parameter will be
     *                ignored unless the author of the tweet this parameter references is mentioned within the status text. Therefore,
     *                you must include @username, where username is the author of the referenced tweet, within the update.
     * @param <T>     The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *                {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *                {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML and JSON, valid values are {@link String},
     *                {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}.
     * @param accessToken the oauth user access key.           
     * @param accessSecret the oauth user access secret.           
     * 
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/statuses/update.{format}?include_entities={include_entities}&lat={lat}&long={long}&place_id={place_id}&display_coordinates={display_coordinates}&trim_user={trim_user}", properties = {HTTP.POST})
    public <T> T statusesUpdate(@BodyParam("status") String status,
                                @Optional @BodyParam("in_reply_to_status_id") String replyId,
                                @Optional @UriParam("lat") double latituide,
                                @Optional @UriParam("long") double longitude,
                                @Optional @UriParam("place_id") String placeId,
                                @Optional @UriParam("display_coordinates") Boolean displayCoordinates,
                                @Optional @UriParam("trim_user") Boolean trimUser,
                                @Optional @PropertyParam("oauth.access.token") String accessToken,
                                @Optional @PropertyParam("oauth.access.secret") String accessSecret)
        throws CallException;

    /**
     * Returns a single status, specified by the id parameter below. The status's
     * author will be returned inline. Formats: xml, json Requires Authentication:
     * false, unless the author of the status is protected API rate limited: false
     * 
     * @param id The numerical ID of the status to retrieve
     * @param <T> The return type class defined in the
     *            {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)}
     *            or
     *            {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)}
     *            methods or uses the default return type
     *            {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports
     *            XML and JSON, valid values are {@link String},
     *            {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}
     *            .
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request
     *             returns an error
     */
    @Operation
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/statuses/show/{id}.{format}", properties = {HTTP.GET})
    public <T> T statusesShow(@UriParam("id") String id) throws CallException;

    /**
     * Returns a single status, specified by the id parameter below.  The status's author will be returned inline.
     * Formats: xml, json
     * Requires Authentication: false, unless the author of the status is protected
     * API rate limited: false
     *
     * @param id  The numerical ID of the status to retrieve
     * @param <T> The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *            {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *            {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML and JSON, valid values are {@link String},
     *            {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}.
     * @param accessToken the oauth user access key.           
     * @param accessSecret the oauth user access secret.            
     * 
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/statuses/show/{id}.{format}", properties = {HTTP.GET})
    public <T> T statusesShow(@UriParam("id") String id,  @Optional @PropertyParam("oauth.access.token") String accessToken,
                              @Optional @PropertyParam("oauth.access.secret") String accessSecret) throws CallException;
    
    /**
     * Destroys the status specified by the required ID parameter.  The authenticating user must be the author of the specified status.
     * Formats: xml, json
     * Requires Authentication: false, unless the author of the status is protected
     * API rate limited: false
     *
     * @param id  The numerical ID of the status to destroy
     * @param <T> The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *            {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *            {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML and JSON, valid values are {@link String},
     *            {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Operation
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/statuses/destroy/{id}.{format}", properties = {HTTP.DELETE})
    public <T> T statusesDestroy(@UriParam("id") String id) throws CallException;
    
    /**
     * Destroys the status specified by the required ID parameter.  The authenticating user must be the author of the specified status.
     * Formats: xml, json
     * Requires Authentication: false, unless the author of the status is protected
     * API rate limited: false
     *
     * @param id  The numerical ID of the status to destroy
     * @param <T> The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *            {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *            {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML and JSON, valid values are {@link String},
     *            {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}.
     * @param accessToken the oauth user access key.           
     * @param accessSecret the oauth user access secret.             
     * 
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/statuses/destroy/{id}.{format}", properties = {HTTP.DELETE})
    public <T> T statusesDestroy(@UriParam("id") String id,  @Optional @PropertyParam("oauth.access.token") String accessToken,
                                 @Optional @PropertyParam("oauth.access.secret") String accessSecret) throws CallException;

}