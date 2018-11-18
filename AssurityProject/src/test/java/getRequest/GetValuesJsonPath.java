package getRequest;

import static org.testng.Assert.assertTrue;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class GetValuesJsonPath {
    //getting the response from the baseURL
	Response response = get(AssurityURL.URL);
  
	//Before executing all tests,I am validating response code and content type
	@BeforeTest
	public void testResponse() {
		try {
			Assert.assertEquals(200, response.getStatusCode());
			Assert.assertEquals("application/json", response.getContentType());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
  
	//With this test I am validating all values with JsonPath
	@Test
	public void testValues() {
		//get JSON response values
		String ResponseValues = response.getBody().asString();
		System.out.println(ResponseValues);
        
		//creating new JsonPath object
		JsonPath jp = new JsonPath(ResponseValues);
        
		//Validating the Name value
		String Name = jp.getString("Name");
		System.out.println(Name);
		Assert.assertEquals(Name, "Carbon credits");
        
		//Validating the CanRelist value
		String CanRelist = jp.getString("CanRelist");
		System.out.println(CanRelist);
		Assert.assertEquals(CanRelist, "true");
        
		//Validating the Description of Gallery Promotion
		Map<String, String> promotionsWithGallery = jp.getMap("Promotions.find{it.Name == 'Gallery'}");
		System.out.println(promotionsWithGallery);
		assertTrue(promotionsWithGallery.get("Description").contains("2x larger image"));

	}

}
