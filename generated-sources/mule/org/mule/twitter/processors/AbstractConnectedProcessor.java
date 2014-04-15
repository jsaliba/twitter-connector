
package org.mule.twitter.processors;

import java.lang.reflect.Type;
import javax.annotation.Generated;
import org.mule.devkit.processor.DevkitBasedMessageProcessor;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-15T03:24:28-05:00", comments = "Build master.1915.dd1962d")
public abstract class AbstractConnectedProcessor
    extends DevkitBasedMessageProcessor
    implements ConnectivityProcessor
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
    @Override
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
    @Override
    public Object getAccessSecret() {
        return this.accessSecret;
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Type typeFor(String fieldName)
        throws NoSuchFieldException
    {
        return AbstractConnectedProcessor.class.getDeclaredField(fieldName).getGenericType();
    }

}
