package getRequest;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;


public class GetValuesHamcrestMatch {
	//Setting base URL before tests executions
	@BeforeClass
	public void setURL() {
		RestAssured.baseURI = AssurityURL.URL;
	}

	//With this method I am testing only the response content type and status code
	@Test
	public void testOnlyResponse() {
		try {
			given().
			when().get(RestAssured.baseURI).
			then().contentType(ContentType.JSON).
			log().ifError().assertThat().statusCode(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		        
		        
	}
	//With this method I am testing only root Name value with Hamcrest Matchers
	@Test
	public void testOnlyName() {
		given().get(RestAssured.baseURI).
	    then().body("Name",equalTo("Carbon credits"));
	}
	
	//With this method I am testing only root CanRelist value with Hamcrest Matchers
	@Test
	public void testOnlyCanRelist() {
		given().get(RestAssured.baseURI).
	    then().body("CanRelist",equalTo(true));

	}
	
	/*With this method I am testing only Promotions element with Name = "Gallery" 
	with Description that contains the text "2x larger image" with Hamcrest Matchers*/
	@Test
	public void testOnlyGalleryDescription() {
		given().get(RestAssured.baseURI).
        then().body("Promotions.find{it.Name=='Gallery'}.Description",containsString("2x larger image"));
		
	}
	
	//With this method I am testing all values in one test with Hamcrest Matchers
	@Test
	public void testAllValuesInOneTest() {
		given().get(RestAssured.baseURI).
        then().contentType(ContentType.JSON).
        assertThat().statusCode(200).
        body("Name",equalTo("Carbon credits")).
        body("CanRelist",equalTo(true)).
        body("Promotions.find{it.Name=='Gallery'}.Description",containsString("2x larger image"));
	}
}
