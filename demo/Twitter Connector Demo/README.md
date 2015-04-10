Mule Twitter Connector Demo
===========================

INTRODUCTION
------------
The Twitter connector demo project consists of the two flows:

* Get_Followers_Flow - Provides an example on how to get all user objects for users following the specified user.
* Group_Statuses_By_Language_Flow - Example to count, grouped statuses by language of a filtered Twitter stream using Mongo DB Map-Reduce Capabilities.

HOW TO RUN DEMO
---------------

### Prerequisites
In order to build run this demo project you'll need;

* Anypoint Studio with Mule ESB 3.6 Runtime.
* Mule Twitter Connector v4.0.0 or higher.
* Twitter OAuth & Mongo DB Credentials.

### Test the flows

With Anypoint Studio up and running, open the Import wizard from the File menu. A pop-up wizard will offer you the chance to pick Anypoint Studio Project from External Location. On the next wizard window point Project Root to the location of the demo project and select the Server Runtime as Mule Server 3.6.0 CE or EE. Once successfully imported the studio will automatically present the Mule Flows.

From the Package Explorer view, expand the demo project and open the mule-app.properties file. Fill in the credentials of Twitter and Mongo DB instances.

**Note:** If you do not want to test the Group_Statuses_By_Language_Flow due to lack of Mongo DB credentials set Streaming_Statuses_Flow Initial State to stopped. In this way when you run the demo, studio would not complain of authentication failure.

Get_Followers_Flow : Run the demo project and with in the browser hit - **http://localhost:8081/getfollowers**, The result would be a list of all the users following the specified user in XML format.

Group_Statuses_by_Language_Flow : The filtered twitter stream will continuously consume topics about football, soccer & foosball and store the results in the Mongo Database. To view the count of the statuses grouped by language, in browser hit - **http://localhost:8081/groupstatuses**.


SUMMARY
-------

Congratulations! You have imported the Mule Twitter Demo project and used the Mule Twitter Connector to obtain users following the specified user and also count the grouped statuses by language. 