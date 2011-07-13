Mule Twitter Connector
======================

The Twitter Connector currently makes it possible to query different timelines 
available to a user, such as the public, friends or private timeline. For example:

    <?xml version="1.0" encoding="UTF-8"?>
    <mule xmlns="http://www.mulesoft.org/schema/mule/core" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:spring="http://www.springframework.org/schema/beans"
        xmlns:twitter="http://www.mulesoft.org/schema/mule/twitter"
        xmlns:json="http://www.mulesoft.org/schema/mule/json"
        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
                              http://www.mulesoft.org/schema/mule/core http://www.mulesoft.org/schema/mule/core/3.1/mule.xsd
                              http://www.mulesoft.org/schema/mule/json http://www.mulesoft.org/schema/mule/json/3.1/mule-json.xsd
                              http://www.mulesoft.org/schema/mule/twitter http://www.mulesoft.org/schema/mule/twitter/2.0/mule-twitter.xsd">
                              
        <twitter:config consumerKey="${consumer.key}" consumerSecret="${consumer.secret}"/>
        
        <flow name="TwitterTest">
            <inbound-endpoint address="http://localhost:9002/get-statuses"/>
            <response>
                <json:object-to-json-transformer/>
            </response>
            <twitter:get-public-timeline/>
        </flow>
        ....
        
Authenticating with OAuth
-------------------------
With OAuth 1.0, you must do a two step process to authenticate the connector. 
First, request authorization from Twitter. Then, submit the OAuth verifier code back 
to the connector.

You'll need to set up the following two flows to do this:

    <flow name="request-authorization">
        <inbound-endpoint address="http://localhost:9002/twitter/request-authorization"/>
        <twitter:request-authorization/>
        <message-properties-transformer>
            <add-message-property key="http.status" value="302"/>
            <add-message-property key="Location" value="#[payload]"/>
        </message-properties-transformer>
    </flow>
    
    <flow name="set-oauth-verifier">
        <inbound-endpoint address="http://localhost:9002/twitter/set-oauth-verifier"/>
        <logger />
        <twitter:set-oauth-verifier oauthVerifier="#[header:inbound:verifier]"/>
    </flow>
    
The first flow will redirect you to the Twitter authorization page once the connector has
requested an authentication token. The second flow will allow you to set the OAuth verifier code.

To authenticate your connector, do the following:

- [Create a Twitter account](http://twitter.com)
- [Register your application](http://dev.twitter.com/login?redirect_after_login=%2Fapps%2Fnew) with Twitter  
- Set your OAuth consumer key and secret on your config element as shown above
- Go to http://localhost:9002/twitter/request-authorization in your browser
- You will be redirected to the Twitter authorization page. Click Authorize.
- Take the resulting OAuth verifier code and go to http://localhost:9002/twitter/set-oauth-verifier?verifier=OAUTH_VERIFIER in your browser.
- Your access token and secret will be logged to your application logs. You can use those to avoid future authentication setup by setting the accessToken and accessTokenSecret attributes on the connector.

Installation
------------

The connector can either be installed for all applications running within the Mule instance or can be setup to be used
for a single application.

*All Applications*

Download the connector from the link above and place the resulting jar file in
/lib/user directory of the Mule installation folder.

*Single Application*

To make the connector available only to single application then place it in the
lib directory of the application otherwise if using Maven to compile and deploy
your application the following can be done:

Add the connector's maven repo to your pom.xml:

    <repositories>
        <repository>
            <id>mulesoft.releases</id>
            <name>MuleSoft Release Repository</name>
            <url>https://repository.mulesoft.org/releases/</url>
            <layout>default</layout>
        </repsitory>
    </repositories>

Add the connector as a dependency to your project. This can be done by adding
the following under the dependencies element in the pom.xml file of the
application:

    <dependency>
        <groupId>org.mule.modules</groupId>
        <artifactId>mule-module-twitter</artifactId>
        <version>2.0</version>
    </dependency>

Configuration
-------------

You can configure the connector as follows:

    <twitter:config useSSL="value" accessToken="value" accessTokenSecret="value" consumerKey="value" consumerSecret="value"/>

Here is detailed list of all the configuration attributes:

| attribute | description | optional | default value |
|:-----------|:-----------|:---------|:--------------|
|name|Give a name to this configuration so it can be later referenced by config-ref.|yes||
|useSSL||yes|
|accessToken||yes|
|accessTokenSecret||yes|
|consumerKey||no|
|consumerSecret||no|




Search
------

Returns tweets that match a specified query. 
<p>
This method calls http://search.twitter.com/search.json

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|query|The search query.|no||



Get Public Timeline
-------------------

Returns the 20 most recent statuses from non-protected users who have set a custom user icon. The public timeline is cached for 60 seconds and requesting it more often than that is unproductive and a waste of resources.
<br>This method calls http://api.twitter.com/1/statuses/public_timeline

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||

Returns of statuses of the Public Timeline



Get Home Timeline
-----------------

Returns the 20 most recent statuses, including retweets, posted by the authenticating user and that user's friends. This is the equivalent of /timeline/home on the Web.<br>
Usage note: This home_timeline call is identical to statuses/friends_timeline, except that home_timeline also contains retweets, while statuses/friends_timeline does not for backwards compatibility reasons. In a future version of the API, statuses/friends_timeline will be deprected and replaced by home_timeline.
<br>This method calls http://api.twitter.com/1/statuses/home_timeline

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|page||yes|1|
|count||yes|100|
|sinceId||yes|-1|

Returns of the home Timeline



Get User Timeline By Screen Name
--------------------------------

Returns the 20 most recent statuses posted from the authenticating user. It's also possible to request another user's timeline via the id parameter.<br>
This is the equivalent of the Web / page for your own user, or the profile page for a third party.<br>
For backwards compatibility reasons, retweets are stripped out of the user_timeline when calling in XML or JSON (they appear with 'RT' in RSS and Atom). If you'd like them included, you can merge them in from statuses retweeted_by_me.<br>
<br>This method calls http://api.twitter.com/1/statuses/user_timeline.json

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|screenName|specifies the screen name of the user for whom to return the user_timeline|no||
|page||yes|1|
|count||yes|100|
|sinceId||yes|-1|

Returns of the user Timeline



Get User Timeline By User Id
----------------------------

Returns the 20 most recent statuses posted from the authenticating user. It's also possible to request another user's timeline via the id parameter.<br>
This is the equivalent of the Web / page for your own user, or the profile page for a third party.<br>
For backwards compatibility reasons, retweets are stripped out of the user_timeline when calling in XML or JSON (they appear with 'RT' in RSS and Atom). If you'd like them included, you can merge them in from statuses retweeted_by_me.<br>
<br>This method calls http://api.twitter.com/1/statuses/user_timeline.json

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|userId|specifies the ID of the user for whom to return the user_timeline|no||
|page||yes|1|
|count||yes|100|
|sinceId||yes|-1|

Returns of the user Timeline





Get User Timeline
-----------------

Returns the 20 most recent statuses posted from the authenticating user. It's also possible to request another user's timeline via the id parameter.<br>
This is the equivalent of the Web / page for your own user, or the profile page for a third party.<br>
For backwards compatibility reasons, retweets are stripped out of the user_timeline when calling in XML or JSON (they appear with 'RT' in RSS and Atom). If you'd like them included, you can merge them in from statuses retweeted_by_me.<br>
<br>This method calls http://api.twitter.com/1/statuses/user_timeline.json

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|page||yes|1|
|count||yes|100|
|sinceId||yes|-1|

Returns of the user Timeline



Get Mentions
------------

Returns the 20 most recent mentions (status containing @username) for the authenticating user.
<br>This method calls http://api.twitter.com/1/statuses/mentions

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|page||yes|1|
|count||yes|100|
|sinceId||yes|-1|

Returns 20 most recent replies



Get Retweeted By Me
-------------------

Returns the 20 most recent retweets posted by the authenticating user.
<br>This method calls http://api.twitter.com/1/statuses/retweeted_by_me

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|page||yes|1|
|count||yes|100|
|sinceId||yes|-1|

Returns 20 most recent retweets posted by the authenticating user



Get Retweeted To Me
-------------------

Returns the 20 most recent retweets posted by the authenticating user's friends.
<br>This method calls http://api.twitter.com/1/statuses/retweeted_to_me

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|page||yes|1|
|count||yes|100|
|sinceId||yes|-1|

Returns 20 most recent retweets posted by the authenticating user's friends.



Get Retweets Of Me
------------------

Returns the 20 most recent tweets of the authenticated user that have been retweeted by others.
<br>This method calls http://api.twitter.com/1/statuses/retweets_of_me

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|page||yes|1|
|count||yes|100|
|sinceId||yes|-1|

Returns 20 most recent tweets of the authenticated user that have been retweeted by others.



Get Retweeted To User By Screen Name
------------------------------------

Returns the 20 most recent retweets posted by users the specified user follows. This method is identical to statuses/retweeted_to_me except you can choose the user to view.
<br>This method has not been finalized and the interface is subject to change in incompatible ways.
<br>This method calls http://api.twitter.com/1/statuses/retweeted_to_user

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|screenName|the user to view|no||
|page||yes|1|
|count||yes|100|
|sinceId||yes|-1|

Returns 20 most recent retweets posted by the authenticating user's friends.



Get Retweeted To User By User Id
--------------------------------

Returns the 20 most recent retweets posted by users the specified user follows. This method is identical to statuses/retweeted_to_me except you can choose the user to view.
<br>This method has not been finalized and the interface is subject to change in incompatible ways.
<br>This method calls http://api.twitter.com/1/statuses/retweeted_to_user

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|userId|the user to view|no||
|page||yes|1|
|count||yes|100|
|sinceId||yes|-1|

Returns 20 most recent retweets posted by the authenticating user's friends.



Get Retweeted By User By Screen Name
------------------------------------

Returns the 20 most recent retweets posted by the specified user. This method is identical to statuses/retweeted_by_me except you can choose the user to view.
<br>This method has not been finalized and the interface is subject to change in incompatible ways.
<br>This method calls http://api.twitter.com/1/statuses/retweeted_by_user

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|screenName|the user to view|no||
|page||yes|1|
|count||yes|100|
|sinceId||yes|-1|

Returns 20 most recent retweets posted by the authenticating user



Get Retweeted By User By User Id
--------------------------------

Returns the 20 most recent retweets posted by the specified user. This method is identical to statuses/retweeted_by_me except you can choose the user to view.
<br>This method has not been finalized and the interface is subject to change in incompatible ways.
<br>This method calls http://api.twitter.com/1/statuses/retweeted_by_user

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|userId|the user to view|no||
|page||yes|1|
|count||yes|100|
|sinceId||yes|-1|

Returns 20 most recent retweets posted by the authenticating user



Show Status
-----------

Returns a single status, specified by the id parameter below. The status's author will be returned inline.
<br>This method calls http://api.twitter.com/1/statuses/show

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|id|the numerical ID of the status you're trying to retrieve|no||

Returns single status



Update Status
-------------

Updates the authenticating user's status. A status update with text identical to the authenticating user's text identical to the authenticating user's current status will be ignored to prevent duplicates.
<br>This method calls http://api.twitter.com/1/statuses/update

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|status|the text of your status update|no||
|inReplyTo||yes|-1|
|geoLocation||yes||

Returns latest status



Destroy Status
--------------

Destroys the status specified by the required ID parameter.<br>
Usage note: The authenticating user must be the author of the specified status.
<br>This method calls http://api.twitter.com/1/statuses/destroy

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|statusId|The ID of the status to destroy.|no||

Returns deleted status



Retweet Status
--------------

Retweets a tweet. Returns the original tweet with retweet details embedded.
<br>This method calls http://api.twitter.com/1/statuses/retweet

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|statusId|The ID of the status to retweet.|no||

Returns retweeted status



Get Retweets
------------

Returns up to 100 of the first retweets of a given tweet.
<br>This method calls http://api.twitter.com/1/statuses/retweets

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|statusId|The numerical ID of the tweet you want the retweets of.|no||

Returns retweets of a given tweet



Get Retweeted By
----------------

Show user objects of up to 100 members who retweeted the status.
<br>This method calls http://api.twitter.com/1/statuses/:id/retweeted_by

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|statusId|The ID of the status you want to get retweeters of|no||
|page||yes|1|
|count||yes|100|
|sinceId||yes|-1|

Returns list of users who retweeted your status



Get Retweeted By I Ds
---------------------

Show user ids of up to 100 users who retweeted the status represented by id
<br />This method calls http://api.twitter.com/1/statuses/:id/retweeted_by/ids.format

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|statusId|The ID of the status you want to get retweeters of|no||
|page||yes|1|
|count||yes|100|
|sinceId||yes|-1|

Returns of users who retweeted the stats



Set Oauth Verifier
------------------

Set the OAuth verifier after it has been retrieved via requestAuthorization. The resulting access tokens
will be logged to the INFO level so the user can reuse them as part of the configuration in the future
if desired.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|oauthVerifier|The OAuth verifier code from Twitter.|no||



Request Authorization
---------------------

Start the OAuth request authorization process. This will request a token from Yammer and return
a URL which the user can visit to authorize the connector for their account.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|callbackUrl||yes||

Returns user authorization URL.











































