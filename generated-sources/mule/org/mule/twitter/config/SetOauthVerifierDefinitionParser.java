
package org.mule.twitter.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.mule.security.oauth.config.AbstractDevkitBasedDefinitionParser;
import org.mule.twitter.processors.SetOauthVerifierMessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanDefinitionParsingException;
import org.springframework.beans.factory.parsing.Location;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-21T01:59:11-05:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class SetOauthVerifierDefinitionParser
    extends AbstractDevkitBasedDefinitionParser
{

    private static Logger logger = LoggerFactory.getLogger(SetOauthVerifierDefinitionParser.class);

    private BeanDefinitionBuilder getBeanDefinitionBuilder(ParserContext parserContext) {
        try {
            return BeanDefinitionBuilder.rootBeanDefinition(SetOauthVerifierMessageProcessor.class.getName());
        } catch (NoClassDefFoundError noClassDefFoundError) {
            String muleVersion = "";
            try {
                muleVersion = MuleManifest.getProductVersion();
            } catch (Exception _x) {
                logger.error("Problem while reading mule version");
            }
            logger.error(("Cannot launch the mule app, the @Processor [set-oauth-verifier] within the connector [twitter] is not supported in mule "+ muleVersion));
            throw new BeanDefinitionParsingException(new Problem(("Cannot launch the mule app, the @Processor [set-oauth-verifier] within the connector [twitter] is not supported in mule "+ muleVersion), new Location(parserContext.getReaderContext().getResource()), null, noClassDefFoundError));
        }
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = getBeanDefinitionBuilder(parserContext);
        builder.addConstructorArgValue("setOauthVerifier");
        builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        parseConfigRef(element, builder);
        if (hasAttribute(element, "requestToken-ref")) {
            if (element.getAttribute("requestToken-ref").startsWith("#")) {
                builder.addPropertyValue("requestToken", element.getAttribute("requestToken-ref"));
            } else {
                builder.addPropertyValue("requestToken", (("#[registry:"+ element.getAttribute("requestToken-ref"))+"]"));
            }
        }
        parseProperty(builder, element, "oauthVerifier", "oauthVerifier");
        parseProperty(builder, element, "accessKey", "accessKey");
        parseProperty(builder, element, "accessSecret", "accessSecret");
        BeanDefinition definition = builder.getBeanDefinition();
        setNoRecurseOnDefinition(definition);
        attachProcessorDefinition(parserContext, definition);
        return definition;
    }

}
