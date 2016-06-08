package org.mule.modules.twitter;

import twitter4j.GeoLocation;

public class GeoLoc extends GeoLocation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 112277759577397787L;

	public GeoLoc(double latitude, double longitude) {
		super(latitude, longitude);
	}

	protected double[] toDoubleArray() {
    	return new double[] { this.getLongitude(), this.getLatitude() };
    }

}
