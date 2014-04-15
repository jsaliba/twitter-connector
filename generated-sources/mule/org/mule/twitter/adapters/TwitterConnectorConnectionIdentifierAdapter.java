
package org.mule.twitter.adapters;

import javax.annotation.Generated;
import org.mule.twitter.TwitterConnector;
import org.mule.twitter.connection.Connection;


/**
 * A <code>TwitterConnectorConnectionIdentifierAdapter</code> is a wrapper around {@link TwitterConnector } that implements {@link org.mule.devkit.dynamic.api.helper.Connection} interface.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-04-15T10:53:47-05:00", comments = "Build M4.1875.17b58a3")
public class TwitterConnectorConnectionIdentifierAdapter
    extends TwitterConnectorProcessAdapter
    implements Connection
{


    public String getConnectionIdentifier() {
        return super.getConnectionIdentifier();
    }

}
