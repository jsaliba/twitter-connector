Twitter Status  Demo
======================

INTRODUCTION
   TODO

HOW TO DEMO:
  1. Set the following environment variables:
  	* twitterAccessKey 
  	* twitterAccessSecret
  	* twitterConsumerKey
  	* twitterConsumerSecret
  	
  2. Run the TwitterFunctionalTestDriver, or deploy this demo an a Mule Container. 
  	a. Update the status of the account 
  		Run the testCreateAccount test or alternatively hit 
  		http://localhost:9090/twitter-demo-update-status, passing as query params the status text.
  		Example: http://localhost:9090/twitter-demo-update-status?status=...
 	
  
