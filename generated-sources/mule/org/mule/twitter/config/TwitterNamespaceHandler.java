
package org.mule.twitter.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>http://www.mulesoft.org/schema/mule/twitter</code>.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-16T09:56:36-05:00", comments = "Build master.1915.dd1962d")
public class TwitterNamespaceHandler
    extends NamespaceHandlerSupport
{

    private static Logger logger = LoggerFactory.getLogger(TwitterNamespaceHandler.class);

    private void handleException(String beanName, String beanScope, NoClassDefFoundError noClassDefFoundError) {
        String muleVersion = "";
        try {
            muleVersion = MuleManifest.getProductVersion();
        } catch (Exception _x) {
            logger.error("Problem while reading mule version");
        }
        logger.error(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [twitter] is not supported in mule ")+ muleVersion));
        throw new FatalBeanException(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [twitter] is not supported in mule ")+ muleVersion), noClassDefFoundError);
    }

    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        try {
            this.registerBeanDefinitionParser("config", new TwitterConnectorConfigDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("config", "@Config", ex);
        }
        try {
            this.registerBeanDefinitionParser("search", new SearchDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("search", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-home-timeline", new GetHomeTimelineDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-home-timeline", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-timeline-by-screen-name", new GetUserTimelineByScreenNameDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-timeline-by-screen-name", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-timeline-by-user-id", new GetUserTimelineByUserIdDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-timeline-by-user-id", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-timeline", new GetUserTimelineDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-timeline", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-mentions-timeline", new GetMentionsTimelineDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-mentions-timeline", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-retweets-of-me", new GetRetweetsOfMeDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-retweets-of-me", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("show-status", new ShowStatusDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("show-status", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("show-user", new ShowUserDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("show-user", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-followers", new GetFollowersDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-followers", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("update-status", new UpdateStatusDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("update-status", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("destroy-status", new DestroyStatusDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("destroy-status", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("retweet-status", new RetweetStatusDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("retweet-status", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-retweets", new GetRetweetsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-retweets", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("set-oauth-verifier", new SetOauthVerifierDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("set-oauth-verifier", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("request-authorization", new RequestAuthorizationDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("request-authorization", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("reverse-geo-code", new ReverseGeoCodeDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("reverse-geo-code", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("search-places", new SearchPlacesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("search-places", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-geo-details", new GetGeoDetailsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-geo-details", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("create-place", new CreatePlaceDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("create-place", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-similar-places", new GetSimilarPlacesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-similar-places", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-available-trends", new GetAvailableTrendsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-available-trends", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("send-direct-message-by-screen-name", new SendDirectMessageByScreenNameDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("send-direct-message-by-screen-name", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("send-direct-message-by-user-id", new SendDirectMessageByUserIdDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("send-direct-message-by-user-id", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-place-trends", new GetPlaceTrendsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-place-trends", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-closest-trends", new GetClosestTrendsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-closest-trends", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("filtered-stream", new FilteredStreamDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("filtered-stream", "@Source", ex);
        }
        try {
            this.registerBeanDefinitionParser("sample-stream", new SampleStreamDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("sample-stream", "@Source", ex);
        }
        try {
            this.registerBeanDefinitionParser("firehose-stream", new FirehoseStreamDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("firehose-stream", "@Source", ex);
        }
        try {
            this.registerBeanDefinitionParser("link-stream", new LinkStreamDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("link-stream", "@Source", ex);
        }
        try {
            this.registerBeanDefinitionParser("user-stream", new UserStreamDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("user-stream", "@Source", ex);
        }
        try {
            this.registerBeanDefinitionParser("site-stream", new SiteStreamDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("site-stream", "@Source", ex);
        }
    }

}
