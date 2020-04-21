package stepDefinations;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefination extends Utils {

	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	Response response;
	static String place_id;
	// This comment is for Git Hub upload
	// This is comment1 is Git Stuff
	// This is comment2 is Git Stuff
	// This is comment3 is Git Stuff
	// This is Comment4 from Git Demo - Develop branch
	// This is comment5 from Git Stuff - Develop branch
	TestDataBuild data = new TestDataBuild();
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_Place_Payload(String name, String language, String address) throws IOException {

		reqSpec = given().spec(requestSpecification()).body(data.addPlacePayLoad(name,language,address));
		//res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	}

	//@When("user calls {string} with POST Http request")
	//public void user_calls_with_POST_Http_request(String resource) 
	
	@When("user calls {string} with {string} Http request")
	public void user_calls_with_Http_request(String resource, String method) {
		
		APIResources resourceAPI= APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResources());
		if(method.equalsIgnoreCase("POST"))
		{
			response = reqSpec.when().post(resourceAPI.getResources());
					
		}else if(method.equalsIgnoreCase("GET"))
		{
			response = reqSpec.when().get(resourceAPI.getResources());
					
		}
		else if(method.equalsIgnoreCase("DELETE"))
		{
			response = reqSpec.when().delete(resourceAPI.getResources());
					
		}
		else if(method.equalsIgnoreCase("PUT"))
		{
			response = reqSpec.when().put(resourceAPI.getResources());
					
		}
		else 
		{
			System.out.println("Method did not match !");
		}
	}

	@Then("the API call is successful with status code {int}")
	public void the_API_call_is_successful_with_status_code(Integer int1) {
		assertEquals(response.getStatusCode(),200);
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {
		assertEquals(getJsonPath(response,keyValue),expectedValue);
	}
	
	@Then("verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
	   
		place_id = getJsonPath(response, "place_id").toString();
		System.out.println(place_id);
		reqSpec = given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_Http_request(resource, "GET");
		String actualName = getJsonPath(response, "name").toString();
		assertEquals(actualName, expectedName);
	}
	
	@Given("Delete Place Payload")
	public void delete_Place_Payload() throws IOException {
		reqSpec = given().spec(requestSpecification()).body(data.deletePlacePayLoad(place_id));
	}


}
