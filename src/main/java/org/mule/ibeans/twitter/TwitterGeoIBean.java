/*
 * $Id: TwitterIBean.java 214 2010-09-08 23:56:43Z ross $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.ibeans.twitter;

import org.ibeans.api.channel.HTTP;

import org.ibeans.annotation.Call;
import org.ibeans.annotation.param.Optional;
import org.ibeans.annotation.param.UriParam;
import org.ibeans.api.CallException;

public interface TwitterGeoIBean extends TwitterBase {

	public enum GRANULARITY
	{
		POI("poi"),
		NEIGHBORHOOD("neighborhood"),
		CITY("city"),
		ADMIN("admin"),
		COUNTRY("country");
		
		private String value;
		
		private GRANULARITY(String value)
		{
			this.value = value;
		}
		
		public String toString()
		{
			return value;
		}
	}
	
	/**
	 * 
	 * @param <T>
	 * @param latitude
	 * @param longitude
	 * @param query
	 * @param ip
	 * @param granularity
	 * @param accuracy
	 * @param maxResults
	 * @return
	 * @throws CallException
	 */
	@Call(uri = TwitterBase.TWITTER_REST_URL + "/{version}/geo/nearby_places.{format}?lat={lat}&long={long}&query={query}&ip={ip}&granularity={granularity}&accuracy={accuracy}&max_results={max_results}", properties = {HTTP.GET})
	public <T> T getNearbyPlaces(@Optional @UriParam("lat") double latitude, @Optional @UriParam("long") double longitude, 
			@Optional @UriParam("query") String query, @Optional @UriParam("ip") String ip, @Optional @UriParam("granularity") GRANULARITY granularity,
			@Optional @UriParam("accuracy") String accuracy, @Optional @UriParam("max_results") int maxResults) throws CallException;
	
}
