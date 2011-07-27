Twitter Streaming  Demo
======================

INTRODUCTION
   This Demo Shows how to consume a Twitter Stream

HOW TO DEMO:
  1. Set the following environment variables:
  	* twitterAccessKey 
  	* twitterAccessSecret
  	* twitterConsumerKey
  	* twitterConsumerSecret
  	
  2. Run the TwitterFunctionalTestDriver, or deploy this demo an a Mule Container. 
  	a. Run the whole test, 
  		or alternatively, hit http://localhost:9092/twitter-demo-group-statuses-by-language
  	
HOW IT WORKS:
  1. The StreamStatuses flow will continuously consume a filtered twitter stream, about topic soccer, and store each result in a mongo database  
  2. The GroupStatusesByLanguage flow will count statuses by language, using Mongo map-reduce capabilities 
