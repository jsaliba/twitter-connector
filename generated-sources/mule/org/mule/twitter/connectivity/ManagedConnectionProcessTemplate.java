
package org.mule.twitter.connectivity;

import javax.annotation.Generated;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessInterceptor;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.security.oauth.callback.ProcessCallback;
import org.mule.security.oauth.process.ProcessCallbackProcessInterceptor;
import org.mule.security.oauth.process.RetryProcessInterceptor;
import org.mule.twitter.adapters.TwitterConnectorConnectionIdentifierAdapter;
import org.mule.twitter.connection.ConnectionManager;
import org.mule.twitter.process.ManagedConnectionProcessInterceptor;

@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-04-15T10:53:47-05:00", comments = "Build M4.1875.17b58a3")
public class ManagedConnectionProcessTemplate<P >implements ProcessTemplate<P, TwitterConnectorConnectionIdentifierAdapter>
{

    private final ProcessInterceptor<P, TwitterConnectorConnectionIdentifierAdapter> processInterceptor;

    public ManagedConnectionProcessTemplate(ConnectionManager<TwitterConnectorConnectionKey, TwitterConnectorConnectionIdentifierAdapter> connectionManager, MuleContext muleContext) {
        ProcessInterceptor<P, TwitterConnectorConnectionIdentifierAdapter> processCallbackProcessInterceptor = new ProcessCallbackProcessInterceptor<P, TwitterConnectorConnectionIdentifierAdapter>();
        ProcessInterceptor<P, TwitterConnectorConnectionIdentifierAdapter> managedConnectionProcessInterceptor = new ManagedConnectionProcessInterceptor<P>(processCallbackProcessInterceptor, connectionManager, muleContext);
        ProcessInterceptor<P, TwitterConnectorConnectionIdentifierAdapter> retryProcessInterceptor = new RetryProcessInterceptor<P, TwitterConnectorConnectionIdentifierAdapter>(managedConnectionProcessInterceptor, muleContext, connectionManager.getRetryPolicyTemplate());
        processInterceptor = retryProcessInterceptor;
    }

    public P execute(ProcessCallback<P, TwitterConnectorConnectionIdentifierAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
        throws Exception
    {
        return processInterceptor.execute(processCallback, null, messageProcessor, event);
    }

    public P execute(ProcessCallback<P, TwitterConnectorConnectionIdentifierAdapter> processCallback, Filter filter, MuleMessage message)
        throws Exception
    {
        return processInterceptor.execute(processCallback, null, filter, message);
    }

}
