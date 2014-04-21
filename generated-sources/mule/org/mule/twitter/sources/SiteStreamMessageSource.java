
package org.mule.twitter.sources;

import java.util.List;
import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.callback.SourceCallback;
import org.mule.api.construct.FlowConstructAware;
import org.mule.api.context.MuleContextAware;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.api.source.MessageSource;
import org.mule.security.oauth.callback.ProcessCallback;
import org.mule.security.oauth.processor.AbstractListeningMessageProcessor;
import org.mule.twitter.TwitterConnector;
import org.mule.twitter.connectivity.TwitterConnectorConnectionManager;


/**
 * SiteStreamMessageSource wraps {@link org.mule.twitter.TwitterConnector#siteStream(java.util.List, boolean, org.mule.api.callback.SourceCallback)} method in {@link TwitterConnector } as a message source capable of generating Mule events.  The POJO's method is invoked in its own thread.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-21T01:59:11-05:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class SiteStreamMessageSource
    extends AbstractListeningMessageProcessor
    implements Runnable, FlowConstructAware, MuleContextAware, Startable, Stoppable, MessageSource
{

    protected Object userIds;
    protected List<String> _userIdsType;
    protected Object withFollowings;
    protected boolean _withFollowingsType;
    /**
     * Thread under which this message source will execute
     * 
     */
    private Thread thread;

    public SiteStreamMessageSource(String operationName) {
        super(operationName);
    }

    /**
     * Obtains the expression manager from the Mule context and initialises the connector. If a target object  has not been set already it will search the Mule registry for a default one.
     * 
     * @throws InitialisationException
     */
    public void initialise()
        throws InitialisationException
    {
    }

    /**
     * Sets userIds
     * 
     * @param value Value to set
     */
    public void setUserIds(Object value) {
        this.userIds = value;
    }

    /**
     * Sets withFollowings
     * 
     * @param value Value to set
     */
    public void setWithFollowings(Object value) {
        this.withFollowings = value;
    }

    /**
     * Method to be called when Mule instance gets started.
     * 
     */
    public void start()
        throws MuleException
    {
        if (thread == null) {
            thread = new Thread(this, "Receiving Thread");
        }
        thread.start();
    }

    /**
     * Method to be called when Mule instance gets stopped.
     * 
     */
    public void stop()
        throws MuleException
    {
        thread.interrupt();
    }

    /**
     * Implementation {@link Runnable#run()} that will invoke the method on the pojo that this message source wraps.
     * 
     */
    public void run() {
        Object moduleObject = null;
        try {
            moduleObject = findOrCreate(TwitterConnectorConnectionManager.class, false, null);
            ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
            final SourceCallback sourceCallback = this;
            final List<String> transformedUserIds = ((List<String> ) transform(getMuleContext(), ((MuleEvent) null), getClass().getDeclaredField("_userIdsType").getGenericType(), null, userIds));
            final Boolean transformedWithFollowings = ((Boolean) transform(getMuleContext(), ((MuleEvent) null), getClass().getDeclaredField("_withFollowingsType").getGenericType(), null, withFollowings));
            processTemplate.execute(new ProcessCallback<Object,Object>() {


                public List<Class<? extends Exception>> getManagedExceptions() {
                    return null;
                }

                public boolean isProtected() {
                    return false;
                }

                public Object process(Object object)
                    throws Exception
                {
                    ((TwitterConnector) object).siteStream(transformedUserIds, transformedWithFollowings, sourceCallback);
                    return null;
                }

            }
            , null, ((MuleEvent) null));
        } catch (Exception e) {
            getMuleContext().getExceptionListener().handleException(e);
        }
    }

}
