
package org.mule.twitter.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.mule.security.oauth.config.AbstractDevkitBasedDefinitionParser;
import org.mule.security.oauth.config.AbstractDevkitBasedDefinitionParser.ParseDelegate;
import org.mule.twitter.sources.SiteStreamMessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanDefinitionParsingException;
import org.springframework.beans.factory.parsing.Location;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

@Generated(value = "Mule DevKit Version 3.5.0-M4", date = "2014-04-15T10:53:47-05:00", comments = "Build M4.1875.17b58a3")
public class SiteStreamDefinitionParser
    extends AbstractDevkitBasedDefinitionParser
{

    private static Logger logger = LoggerFactory.getLogger(SiteStreamDefinitionParser.class);

    private BeanDefinitionBuilder getBeanDefinitionBuilder(ParserContext parserContext) {
        try {
            return BeanDefinitionBuilder.rootBeanDefinition(SiteStreamMessageSource.class.getName());
        } catch (NoClassDefFoundError noClassDefFoundError) {
            String muleVersion = "";
            try {
                muleVersion = MuleManifest.getProductVersion();
            } catch (Exception _x) {
                logger.error("Problem while reading mule version");
            }
            logger.error(("Cannot launch the mule app, the @Source [site-stream] within the connector [twitter] is not supported in mule "+ muleVersion));
            throw new BeanDefinitionParsingException(new Problem(("Cannot launch the mule app, the @Source [site-stream] within the connector [twitter] is not supported in mule "+ muleVersion), new Location(parserContext.getReaderContext().getResource()), null, noClassDefFoundError));
        }
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = getBeanDefinitionBuilder(parserContext);
        builder.addConstructorArgValue("siteStream");
        builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        parseConfigRef(element, builder);
        parseListAndSetProperty(element, builder, "userIds", "user-ids", "user-id", new ParseDelegate<String>() {


            public String parse(Element element) {
                return element.getTextContent();
            }

        }
        );
        parseProperty(builder, element, "withFollowings", "withFollowings");
        BeanDefinition definition = builder.getBeanDefinition();
        setNoRecurseOnDefinition(definition);
        attachSourceDefinition(parserContext, definition);
        return definition;
    }

}
