/*
 * $Id: TwitterAccountIBean.java 297 2011-04-12 00:12:01Z dzapata $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.ibeans.twitter;

import org.ibeans.api.channel.HTTP;

import java.io.File;
import java.net.URL;

import org.ibeans.annotation.Call;
import org.ibeans.annotation.param.Attachment;
import org.ibeans.annotation.param.BodyParam;
import org.ibeans.annotation.param.Optional;
import org.ibeans.annotation.param.UriParam;
import org.ibeans.api.CallException;

/**
 * Access to the Twitter account APIs
 */
public interface TwitterAccountIBean extends TwitterBase
{
    /**
     * Valid Delivery Device
     */
    public enum DEVICE
    {
        NONE("none"),
        SMS("sms");

        private String value;

        private DEVICE(String value)
        {
            this.value = value;
        }

        public String toString()
        {
            return value;
        }
    }

    /**
     * Returns an HTTP 200 OK response code and a representation of the requesting user if authentication was successful;
     * returns a 401 status code and an error message if not, which results in a CallException.  Use this method to test
     * if supplied user credentials are valid. This method can only be used is {@link #setCredentials(String, String)} has
     * already been called.
     * <p/>
     * Formats: xml, json
     * Requires Authentication: true
     * API rate limited: false
     *
     * @param <T> The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *            {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *            {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML and JSON, valid values are {@link String},
     *            {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}.
     * @return The result of the search in the format defined by param <T>.
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/account/verify_credentials.{format}?include_entities={include_entities}", properties = {HTTP.GET, HTTP.FOLLOW_REDIRECTS})
    public <T> T verifyCredentials() throws CallException;

    /**
     * Ends the session of the authenticating user, returning a null cookie.  Use this method to sign users out of
     * client-facing applications like widgets.
     * <p/>
     * Formats: xml, json
     * Requires Authentication: true
     * API rate limited: false
     *
     * @param <T> The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *            {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *            {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML and JSON, valid values are {@link String},
     *            {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/account/end_session.{format}", properties = {HTTP.POST})
    public <T> T endSession() throws CallException;

    /**
     * Returns the remaining number of API requests available to the requesting user before the API limit is reached for
     * the current hour. Calls to rate_limit_status do not count against the rate limit.  If authentication credentials are
     * provided, the rate limit status for the authenticating user is returned.  Otherwise, the rate limit status for the
     * requester's IP address is returned.
     * <p/>
     * Formats: xml, json
     * Requires Authentication: 'true', to determine a user's rate limit status. 'false', to determine the requesting IP's rate limit status
     * API rate limited: false
     *
     * @param <T> The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *            {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *            {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML and JSON, valid values are {@link String},
     *            {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/account/rate_limit_status.{format}", properties = {HTTP.GET, HTTP.FOLLOW_REDIRECTS})
    public <T> T getRateLimitStatus() throws CallException;

    /**
     * Sets which device Twitter delivers updates to for the authenticating user.  Sending none as the device parameter
     * will disable IM or SMS updates.
     * <p/>
     * Formats: xml, json
     * Requires Authentication: true
     * API rate limited: false
     *
     * @param device Must be a valid device defined in {@link org.mule.ibeans.twitter.TwitterAccountIBean.DEVICE}.
     * @param <T>    The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *               {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *               {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML and JSON, valid values are {@link String},
     *               {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/account/update_delivery_device.{format}?device={device}?include_entities={include_entities}", properties = {HTTP.POST})
    public <T> T updateDeliveryDevice(@UriParam("device") DEVICE device) throws CallException;

    /**
     * Sets one or more hex values that control the color scheme of the authenticating user's profile page on twitter.com.
     * One or more of the following parameters must be present. Each parameter's value must be a valid hexidecimal value,
     * and may be either three or six characters (ex: #fff or #ffffff).
     * <p/>
     * Formats: xml, json
     * Requires Authentication: true
     * API rate limited: false
     *
     * @param backgroundColor    home page background color. Optional.
     * @param textColor          home page text color. Optional.
     * @param linkColor          home page link color. Optional.
     * @param sidebarFillColor   home page sidebar color. Optional.
     * @param sidebarBorderColor home page sidebar border color. Optional.
     * @param <T>                The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *                           {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *                           {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML and JSON, valid values are {@link String},
     *                           {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/account/update_profile_colors.{format}?include_entities={include_entities}", properties = {HTTP.POST})
    public <T> T updateProfileColors(@Optional @BodyParam("profile_background_color") String backgroundColor, @Optional @BodyParam("profile_text_color") String textColor, 
    		@Optional @BodyParam("profile_link_color") String linkColor, @Optional @BodyParam("profile_sidebar_fill_color") String sidebarFillColor, 
    		@Optional @BodyParam("profile_sidebar_border_color") String sidebarBorderColor) throws CallException;

    /**
     * Updates the authenticating user's profile image. Note that this method expects raw multipart data, not a URL to an image.
     * <p/>
     * Formats: xml, json
     * Requires Authentication: true
     * API rate limited: false
     *
     * @param image Must be a valid GIF, JPG, or PNG image of less than 700 kilobytes in size.  Images with width larger
     *              than 500 pixels will be scaled down.
     * @param <T>   The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *              {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *              {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML and JSON, valid values are {@link String},
     *              {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/account/update_profile_image.{format}?include_entities={include_entities}", properties = {HTTP.POST})
    public <T> T updateProfileImage(@Attachment("image") URL image) throws CallException;

    /**
     * Updates the authenticating user's profile image. Note that this method expects raw multipart data, not a URL to an image.
     * <p/>
     * Formats: xml, json
     * Requires Authentication: true
     * API rate limited: false
     *
     * @param image Must be a valid GIF, JPG, or PNG image of less than 700 kilobytes in size.  Images with width larger
     *              than 500 pixels will be scaled down.
     * @param <T>   The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *              {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *              {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML and JSON, valid values are {@link String},
     *              {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/account/update_profile_image.{format}?include_entities={include_entities}", properties = {HTTP.POST})
    public <T> T updateProfileImage(@Attachment("image") File image) throws CallException;

    /**
     * Updates the authenticating user's profile background image.  Note that this method expects raw multipart data, not a URL to an image.
     * <p/>
     * Formats: xml, json
     * Requires Authentication: true
     * API rate limited: false
     *
     * @param image Must be a valid GIF, JPG, or PNG image of less than 800 kilobytes in size.  Images with width larger than 2048 pixels will be forceably scaled down.
     * @param tile  If set to true the background image will be displayed tiled. The image will not be tiled otherwise. Optional.
     * @param <T>   The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *              {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *              {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML and JSON, valid values are {@link String},
     *              {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/account/update_profile_background_image.{format}?include_entities={include_entities}", properties = {HTTP.POST})
    public <T> T updateProfileBackgroundImage(@Attachment("image") File image, @Optional @Attachment("tile") Boolean tile) throws CallException;

    /**
     * Updates the authenticating user's profile background image.  Note that this method expects raw multipart data, not a URL to an image.
     * <p/>
     * Formats: xml, json
     * Requires Authentication: true
     * API rate limited: false
     *
     * @param image Must be a valid GIF, JPG, or PNG image of less than 800 kilobytes in size.  Images with width larger than 2048 pixels will be forceably scaled down.
     * @param tile  If set to true the background image will be displayed tiled. The image will not be tiled otherwise. Optional.
     * @param <T>   The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *              {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *              {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML and JSON, valid values are {@link String},
     *              {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/account/update_profile_background_image.{format}?include_entities={include_entities}", properties = {HTTP.POST})
    public <T> T updateProfileBackgroundImage(@Attachment("image") URL image, @Optional @BodyParam("tile") Boolean tile) throws CallException;

    /**
     * Sets values that users are able to set under the "Account" tab of their settings page. Only the parameters specified will be updated.
     * Note that all of these parameters are optional but at least one must be set.
     *
     * @param name        Maximum of 20 characters. Optional.
     * @param url         Maximum of 100 characters. Will be prepended with "http://" if not present. Optional.
     * @param location    Maximum of 30 characters. The contents are not normalized or geocoded in any way. Optional.
     * @param description Maximum of 160 characters. Optional.
     * @param <T>         The return type class defined in the {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT, Class)} or
     *                    {@link TwitterBase#setFormat(org.mule.ibeans.twitter.TwitterBase.FORMAT)} methods or uses the default return type
     *                    {@link TwitterBase#DEFAULT_RETURN_TYPE}. The Twitter API supports XML and JSON, valid values are {@link String},
     *                    {@link org.mule.module.json.JsonData}, {@link org.w3c.dom.Document}.
     * @return The result of the search in the format defined by param <T>
     * @throws CallException if there is an error making the request or the request returns an error
     */
    @Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/account/update_profile.{format}?include_entities={include_entities}", properties = {HTTP.POST})
    public <T> T updateProfile(@Optional @BodyParam("name") String name, @Optional @BodyParam("url") String url, @Optional @BodyParam("location") String location, 
    		@Optional @BodyParam("description") String description) throws CallException;

}