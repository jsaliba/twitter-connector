Twitter Connector Release Notes
=================================
 
Date: 13-FEB-2015
 
Version: 4.0.0
 
Supported API versions
------------------------
Twitter 1.1 API. Please visit this link for detail https://dev.twitter.com/docs/api/1.1.
 
Supported Mule Runtime Versions
--------------------------------
3.6.0 or higher.
 
New Features and Functionality
------------------------------
- Migrated to DevKit 3.6.0
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