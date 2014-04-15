
package org.mule.twitter.connectivity;

import javax.annotation.Generated;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.twitter.adapters.TwitterConnectorConnectionIdentifierAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-04-15T10:53:47-05:00", comments = "Build M4.1875.17b58a3")
public class TwitterConnectorConnectionFactory implements KeyedPoolableObjectFactory
{

    private static Logger logger = LoggerFactory.getLogger(TwitterConnectorConnectionFactory.class);
    private TwitterConnectorConnectionManager connectionManager;

    public TwitterConnectorConnectionFactory(TwitterConnectorConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public Object makeObject(Object key)
        throws Exception
    {
        if (!(key instanceof TwitterConnectorConnectionKey)) {
            if (key == null) {
                logger.warn("Connection key is null");
            } else {
                logger.warn("Cannot cast key of type ".concat(key.getClass().getName().concat(" to ").concat("org.mule.twitter.connectivity.TwitterConnectorConnectionKey")));
            }
            throw new RuntimeException("Invalid key type ".concat(key.getClass().getName()));
        }
        TwitterConnectorConnectionIdentifierAdapter connector = new TwitterConnectorConnectionIdentifierAdapter();
        connector.setConsumerKey(connectionManager.getConsumerKey());
        connector.setConsumerSecret(connectionManager.getConsumerSecret());
        connector.setUseSSL(connectionManager.getUseSSL());
        connector.setProxyHost(connectionManager.getProxyHost());
        connector.setProxyPort(connectionManager.getProxyPort());
        connector.setProxyUsername(connectionManager.getProxyUsername());
        connector.setProxyPassword(connectionManager.getProxyPassword());
        connector.setStreamBaseUrl(connectionManager.getStreamBaseUrl());
        connector.setSiteStreamBaseUrl(connectionManager.getSiteStreamBaseUrl());
        if (connector instanceof MuleContextAware) {
            ((MuleContextAware) connector).setMuleContext(connectionManager.getMuleContext());
        }
        if (connector instanceof Initialisable) {
            ((Initialisable) connector).initialise();
        }
        if (connector instanceof Startable) {
            ((Startable) connector).start();
        }
        if (!connector.validateConnection()) {
            connector.connect(((TwitterConnectorConnectionKey) key).getAccessKey(), ((TwitterConnectorConnectionKey) key).getAccessSecret());
        }
        return connector;
    }

    public void destroyObject(Object key, Object obj)
        throws Exception
    {
        if (!(key instanceof TwitterConnectorConnectionKey)) {
            if (key == null) {
                logger.warn("Connection key is null");
            } else {
                logger.warn("Cannot cast key of type ".concat(key.getClass().getName().concat(" to ").concat("org.mule.twitter.connectivity.TwitterConnectorConnectionKey")));
            }
            throw new RuntimeException("Invalid key type ".concat(key.getClass().getName()));
        }
        if (!(obj instanceof TwitterConnectorConnectionIdentifierAdapter)) {
            if (obj == null) {
                logger.warn("Connector is null");
            } else {
                logger.warn("Cannot cast connector of type ".concat(obj.getClass().getName().concat(" to ").concat("org.mule.twitter.adapters.TwitterConnectorConnectionIdentifierAdapter")));
            }
            throw new RuntimeException("Invalid connector type ".concat(obj.getClass().getName()));
        }
        try {
            ((TwitterConnectorConnectionIdentifierAdapter) obj).disconnect();
        } catch (Exception e) {
            throw e;
        } finally {
            if (((TwitterConnectorConnectionIdentifierAdapter) obj) instanceof Stoppable) {
                ((Stoppable) obj).stop();
            }
            if (((TwitterConnectorConnectionIdentifierAdapter) obj) instanceof Disposable) {
                ((Disposable) obj).dispose();
            }
        }
    }

    public boolean validateObject(Object key, Object obj) {
        if (!(obj instanceof TwitterConnectorConnectionIdentifierAdapter)) {
            if (obj == null) {
                logger.warn("Connector is null");
            } else {
                logger.warn("Cannot cast connector of type ".concat(obj.getClass().getName().concat(" to ").concat("org.mule.twitter.adapters.TwitterConnectorConnectionIdentifierAdapter")));
            }
            throw new RuntimeException("Invalid connector type ".concat(obj.getClass().getName()));
        }
        try {
            return ((TwitterConnectorConnectionIdentifierAdapter) obj).validateConnection();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public void activateObject(Object key, Object obj)
        throws Exception
    {
        if (!(key instanceof TwitterConnectorConnectionKey)) {
            throw new RuntimeException("Invalid key type");
        }
        if (!(obj instanceof TwitterConnectorConnectionIdentifierAdapter)) {
            throw new RuntimeException("Invalid connector type");
        }
        try {
            if (!((TwitterConnectorConnectionIdentifierAdapter) obj).validateConnection()) {
                ((TwitterConnectorConnectionIdentifierAdapter) obj).connect(((TwitterConnectorConnectionKey) key).getAccessKey(), ((TwitterConnectorConnectionKey) key).getAccessSecret());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void passivateObject(Object key, Object obj)
        throws Exception
    {
    }

}
