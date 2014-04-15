
package org.mule.twitter.processors;

import java.util.List;
import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.config.ConfigurationException;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.registry.RegistrationException;
import org.mule.common.DefaultResult;
import org.mule.common.FailureType;
import org.mule.common.Result;
import org.mule.common.metadata.ConnectorMetaDataEnabled;
import org.mule.common.metadata.DefaultMetaData;
import org.mule.common.metadata.DefaultPojoMetaDataModel;
import org.mule.common.metadata.DefaultSimpleMetaDataModel;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.OperationMetaDataEnabled;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.datatype.DataTypeFactory;
import org.mule.security.oauth.callback.ProcessCallback;
import org.mule.twitter.TwitterConnector;
import org.mule.twitter.connectivity.TwitterConnectorConnectionManager;
import twitter4j.QueryResult;


/**
 * SearchMessageProcessor invokes the {@link org.mule.twitter.TwitterConnector#search(java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)} method in {@link TwitterConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-15T03:24:28-05:00", comments = "Build master.1915.dd1962d")
public class SearchMessageProcessor
    extends AbstractConnectedProcessor
    implements MessageProcessor, OperationMetaDataEnabled
{

    protected Object query;
    protected String _queryType;
    protected Object lang;
    protected String _langType;
    protected Object locale;
    protected String _localeType;
    protected Object maxId;
    protected Long _maxIdType;
    protected Object since;
    protected String _sinceType;
    protected Object sinceId;
    protected Long _sinceIdType;
    protected Object geocode;
    protected String _geocodeType;
    protected Object radius;
    protected String _radiusType;
    protected Object unit;
    protected String _unitType;
    protected Object until;
    protected String _untilType;
    protected Object resultType;
    protected String _resultTypeType;

    public SearchMessageProcessor(String operationName) {
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

    @Override
    public void start()
        throws MuleException
    {
        super.start();
    }

    @Override
    public void stop()
        throws MuleException
    {
        super.stop();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    /**
     * Sets geocode
     * 
     * @param value Value to set
     */
    public void setGeocode(Object value) {
        this.geocode = value;
    }

    /**
     * Sets unit
     * 
     * @param value Value to set
     */
    public void setUnit(Object value) {
        this.unit = value;
    }

    /**
     * Sets sinceId
     * 
     * @param value Value to set
     */
    public void setSinceId(Object value) {
        this.sinceId = value;
    }

    /**
     * Sets query
     * 
     * @param value Value to set
     */
    public void setQuery(Object value) {
        this.query = value;
    }

    /**
     * Sets locale
     * 
     * @param value Value to set
     */
    public void setLocale(Object value) {
        this.locale = value;
    }

    /**
     * Sets resultType
     * 
     * @param value Value to set
     */
    public void setResultType(Object value) {
        this.resultType = value;
    }

    /**
     * Sets radius
     * 
     * @param value Value to set
     */
    public void setRadius(Object value) {
        this.radius = value;
    }

    /**
     * Sets since
     * 
     * @param value Value to set
     */
    public void setSince(Object value) {
        this.since = value;
    }

    /**
     * Sets lang
     * 
     * @param value Value to set
     */
    public void setLang(Object value) {
        this.lang = value;
    }

    /**
     * Sets until
     * 
     * @param value Value to set
     */
    public void setUntil(Object value) {
        this.until = value;
    }

    /**
     * Sets maxId
     * 
     * @param value Value to set
     */
    public void setMaxId(Object value) {
        this.maxId = value;
    }

    /**
     * Invokes the MessageProcessor.
     * 
     * @param event MuleEvent to be processed
     * @throws Exception
     */
    public MuleEvent doProcess(final MuleEvent event)
        throws Exception
    {
        Object moduleObject = null;
        try {
            moduleObject = findOrCreate(TwitterConnectorConnectionManager.class, false, event);
            final String _transformedQuery = ((String) evaluateAndTransform(getMuleContext(), event, SearchMessageProcessor.class.getDeclaredField("_queryType").getGenericType(), null, query));
            final String _transformedLang = ((String) evaluateAndTransform(getMuleContext(), event, SearchMessageProcessor.class.getDeclaredField("_langType").getGenericType(), null, lang));
            final String _transformedLocale = ((String) evaluateAndTransform(getMuleContext(), event, SearchMessageProcessor.class.getDeclaredField("_localeType").getGenericType(), null, locale));
            final Long _transformedMaxId = ((Long) evaluateAndTransform(getMuleContext(), event, SearchMessageProcessor.class.getDeclaredField("_maxIdType").getGenericType(), null, maxId));
            final String _transformedSince = ((String) evaluateAndTransform(getMuleContext(), event, SearchMessageProcessor.class.getDeclaredField("_sinceType").getGenericType(), null, since));
            final Long _transformedSinceId = ((Long) evaluateAndTransform(getMuleContext(), event, SearchMessageProcessor.class.getDeclaredField("_sinceIdType").getGenericType(), null, sinceId));
            final String _transformedGeocode = ((String) evaluateAndTransform(getMuleContext(), event, SearchMessageProcessor.class.getDeclaredField("_geocodeType").getGenericType(), null, geocode));
            final String _transformedRadius = ((String) evaluateAndTransform(getMuleContext(), event, SearchMessageProcessor.class.getDeclaredField("_radiusType").getGenericType(), null, radius));
            final String _transformedUnit = ((String) evaluateAndTransform(getMuleContext(), event, SearchMessageProcessor.class.getDeclaredField("_unitType").getGenericType(), null, unit));
            final String _transformedUntil = ((String) evaluateAndTransform(getMuleContext(), event, SearchMessageProcessor.class.getDeclaredField("_untilType").getGenericType(), null, until));
            final String _transformedResultType = ((String) evaluateAndTransform(getMuleContext(), event, SearchMessageProcessor.class.getDeclaredField("_resultTypeType").getGenericType(), null, resultType));
            Object resultPayload;
            final ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
            resultPayload = processTemplate.execute(new ProcessCallback<Object,Object>() {


                public List<Class<? extends Exception>> getManagedExceptions() {
                    return null;
                }

                public boolean isProtected() {
                    return false;
                }

                public Object process(Object object)
                    throws Exception
                {
                    return ((TwitterConnector) object).search(_transformedQuery, _transformedLang, _transformedLocale, _transformedMaxId, _transformedSince, _transformedSinceId, _transformedGeocode, _transformedRadius, _transformedUnit, _transformedUntil, _transformedResultType);
                }

            }
            , this, event);
            event.getMessage().setPayload(resultPayload);
            return event;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Result<MetaData> getInputMetaData() {
        return new DefaultResult<MetaData>(null, (Result.Status.SUCCESS));
    }

    @Override
    public Result<MetaData> getOutputMetaData(MetaData inputMetadata) {
        return new DefaultResult<MetaData>(new DefaultMetaData(getPojoOrSimpleModel(QueryResult.class)));
    }

    private MetaDataModel getPojoOrSimpleModel(Class clazz) {
        DataType dataType = DataTypeFactory.getInstance().getDataType(clazz);
        if (DataType.POJO.equals(dataType)) {
            return new DefaultPojoMetaDataModel(clazz);
        } else {
            return new DefaultSimpleMetaDataModel(dataType);
        }
    }

    public Result<MetaData> getGenericMetaData(MetaDataKey metaDataKey) {
        ConnectorMetaDataEnabled connector;
        try {
            connector = ((ConnectorMetaDataEnabled) findOrCreate(TwitterConnectorConnectionManager.class, true, null));
            try {
                Result<MetaData> metadata = connector.getMetaData(metaDataKey);
                if ((Result.Status.FAILURE).equals(metadata.getStatus())) {
                    return metadata;
                }
                if (metadata.get() == null) {
                    return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error processing metadata at TwitterConnector at search retrieving was successful but result is null");
                }
                return metadata;
            } catch (Exception e) {
                return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
            }
        } catch (ClassCastException cast) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error getting metadata, there was no connection manager available. Maybe you're trying to use metadata from an Oauth connector");
        } catch (ConfigurationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (RegistrationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (IllegalAccessException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (InstantiationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (Exception e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        }
    }

}
