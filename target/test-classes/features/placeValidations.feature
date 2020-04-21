Feature: Validating Place APIs
@AddPlace @Regression
Scenario Outline: Verify if place is being successfully added using AddPlaceAPI
	Given Add Place Payload with "<name>" "<langauge>" "<address>"
	When user calls "addPlaceAPI" with "POST" Http request
	Then the API call is successful with status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP" 
	And verify place_id created maps to "<name>" using "getPlaceAPI"
	
Examples:
 |name    |langauge| address|
 |Gurpreet|English | Kharar |
# |Rajdeep |French  | Greater Kharar|
@DeletePlace @Regression
Scenario: Verify if delete Place functionality is working
	Given Delete Place Payload
	When user calls "deletePlaceAPI" with "POST" Http request
	Then the API call is successful with status code 200
	And "status" in response body is "OK"
	 