Twitter Connector Release Notes
=================================
 
Date: 12-MAY-2014
 
Version: 3.0.4
 
Supported API versions
------------------------
Twitter 1.1 API. Please visit this link for detail https://dev.twitter.com/docs/api/1.1.
 
Supported Mule Runtime Versions
--------------------------------
3.5.0
 
New Features and Functionality
------------------------------
- Migrated to DevKit 3.5.0
- Fixed automation tests.
- The list of operation supported by this connector version are
    - search
    - getHomeTimeline
    - getUserTimelineByScreenName
    - getUserTimelineByUserId
    - getMentionsTimeline
    - getRetweetsOfMe
    - showStatus
    - showUser
    - getFollowers
    - updateStatus
    - destroyStatus
    - retweetStatus
    - getRetweets
    - setOauthVerifier
    - requestAuthorization
    - reverseGeoCode
    - searchPlaces
    - getGeoDetails
    - createPlace
    - getSimilarPlaces
    - getAvailableTrends
    - sendDirectMessageByScreenName
    - sendDirectMessageByUserId
    - getPlaceTrends
    - getClosestTrends
- Removed "Get public time line" operation without user id or screen name from the connector. This service is no longer supported.

Closed Issues in this release
------------------------------
N/A
 
Known Issues in this release
------------------------------
N/A

http://github.com/mulesoft/twitter-connector/issues

2.9
---
* Implemented connection manager
  
2.8
---
- Added connectivity testing
- Migrated to DevKit 3.4.0-RC1
- Fixed Automation tests
