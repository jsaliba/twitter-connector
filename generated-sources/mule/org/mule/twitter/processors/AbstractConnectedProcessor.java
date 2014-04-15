
package org.mule.twitter.processors;

import javax.annotation.Generated;
import org.mule.devkit.processor.DevkitBasedMessageProcessor;

@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-04-15T10:53:47-05:00", comments = "Build M4.1875.17b58a3")
public abstract class AbstractConnectedProcessor
    extends DevkitBasedMessageProcessor
{

    protected Object accessKey;
    protected String _accessKeyType;
    protected Object accessSecret;
    protected String _accessSecretType;

    public AbstractConnectedProcessor(String operationName) {
        super(operationName);
    }

    /**
     * Sets accessKey
     * 
     * @param value Value to set
     */
    public void setAccessKey(Object value) {
        this.accessKey = value;
    }

    /**
     * Retrieves accessKey
     * 
     */
    public Object getAccessKey() {
        return this.accessKey;
    }

    /**
     * Sets accessSecret
     * 
     * @param value Value to set
     */
    public void setAccessSecret(Object value) {
        this.accessSecret = value;
    }

    /**
     * Retrieves accessSecret
     * 
     */
    public Object getAccessSecret() {
        return this.accessSecret;
    }

}
