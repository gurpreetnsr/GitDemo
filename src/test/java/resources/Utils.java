package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Utils {

	public static RequestSpecification req;
	public static ResponseSpecification res;

	public RequestSpecification requestSpecification() throws IOException 
	{
		if(req==null)
		{
			PrintStream log = new PrintStream(new FileOutputStream("Logging.txt"));
			req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl")).addQueryParam("key", "qaclick123")
					.setContentType(ContentType.JSON)
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).build();		
			return req;
		}
		return req;
	}
	
	public ResponseSpecification responseSpecification()
	{
		if(res==null)
		{
			res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
			return res;
		}
		return res;
	}
	
	public static String getGlobalValue(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\Users\\rajdeep\\eclipse-workspace\\APIFramework\\src\\test\\java\\resources\\global.properties");
		prop.load(fis);
		String propertyValue = prop.getProperty(key);
		return propertyValue;
	}
	
	public Object getJsonPath(Response response, String key)
	{
		String responseString = response.asString();
		JsonPath js = new JsonPath(responseString);
		return js.get(key);

	}

}
