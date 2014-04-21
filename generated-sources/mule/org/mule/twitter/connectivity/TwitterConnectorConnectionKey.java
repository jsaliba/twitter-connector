
package org.mule.twitter.connectivity;

import javax.annotation.Generated;


/**
 * A tuple of connection parameters
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-21T01:59:11-05:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class TwitterConnectorConnectionKey {

    /**
     * 
     */
    private String accessKey;
    /**
     * 
     */
    private String accessSecret;

    public TwitterConnectorConnectionKey(String accessKey, String accessSecret) {
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
    }

    /**
     * Sets accessKey
     * 
     * @param value Value to set
     */
    public void setAccessKey(String value) {
        this.accessKey = value;
    }

    /**
     * Retrieves accessKey
     * 
     */
    public String getAccessKey() {
        return this.accessKey;
    }

    /**
     * Sets accessSecret
     * 
     * @param value Value to set
     */
    public void setAccessSecret(String value) {
        this.accessSecret = value;
    }

    /**
     * Retrieves accessSecret
     * 
     */
    public String getAccessSecret() {
        return this.accessSecret;
    }

    @Override
    public int hashCode() {
        int result = ((this.accessKey!= null)?this.accessKey.hashCode(): 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TwitterConnectorConnectionKey)) {
            return false;
        }
        TwitterConnectorConnectionKey that = ((TwitterConnectorConnectionKey) o);
        if (((this.accessKey!= null)?(!this.accessKey.equals(that.accessKey)):(that.accessKey!= null))) {
            return false;
        }
        return true;
    }

}
