
package org.mule.twitter.processors;

import java.lang.reflect.Type;
import javax.annotation.Generated;
import org.mule.streaming.processor.AbstractDevkitBasedPageableMessageProcessor;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-21T01:59:11-05:00", comments = "Build UNKNOWN_BUILDNUMBER")
public abstract class AbstractPagedConnectedProcessor
    extends AbstractDevkitBasedPageableMessageProcessor
    implements ConnectivityProcessor
{

    protected Object accessKey;
    protected String _accessKeyType;
    protected Object accessSecret;
    protected String _accessSecretType;

    public AbstractPagedConnectedProcessor(String operationName) {
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
        return AbstractPagedConnectedProcessor.class.getDeclaredField(fieldName).getGenericType();
    }

}
