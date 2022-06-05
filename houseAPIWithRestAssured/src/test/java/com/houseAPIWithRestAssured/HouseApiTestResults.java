package com.houseAPIWithRestAssured;

import static org.testng.Assert.assertFalse;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.houseapi.configurations.Properties;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class HouseApiTestResults {
	public static RestAssured restAssured;

	@BeforeClass
	public static void setUp() {
		//Configuring the API URL from properties file
		RestAssured.baseURI = Properties.getValue("ApiURL");
	}

	@Test
	public void houseApi() throws Exception {
		//Request Specification with all logs
		RequestSpecification request = RestAssured.given().log().all();
		//Making the request
		Response response = request.queryParam("price_gte", "450000").queryParam("price_lte", "666000")
				.queryParam("city", "Austin").get();
		//The response as string
		String list = response.getBody().asString();
		System.out.println(list);
		JsonPath JP = response.jsonPath();
		//In case there is no results
		if (list.equalsIgnoreCase("[]")) {
			throw new Exception("There are no houses available with that range in this city");
		//In case the result is OK	
		}else{
			List<String> allPrices = JP.getList("findAll{it.price <= 666000 & it.price >= 450000}.zip");
			int numberOfResults = JP.getInt("size()");
			//Comparing the number of the results with zip's that are related with the right price range
			if (numberOfResults == allPrices.size()) {
				System.out.println("The API response is OK");
				//Validating all elements in case of successful result
				assertFalse(JP.getJsonObject("id").toString().isEmpty());
				assertFalse(JP.getJsonObject("mls_id").toString().isEmpty());
				assertFalse(JP.getJsonObject("mls_listing_id").toString().isEmpty());
				assertFalse(JP.getJsonObject("property_type").toString().isEmpty());
				assertFalse(JP.getJsonObject("formatted_address").toString().isEmpty());
				assertFalse(JP.getJsonObject("zip").toString().isEmpty());
				assertFalse(JP.getJsonObject("city").toString().isEmpty());
				assertFalse(JP.getJsonObject("state").toString().isEmpty());
				assertFalse(JP.getJsonObject("location").toString().isEmpty());
				assertFalse(JP.getJsonObject("bedrooms").toString().isEmpty());
				assertFalse(JP.getJsonObject("bathrooms").toString().isEmpty());
				assertFalse(JP.getJsonObject("list_date").toString().isEmpty());
				assertFalse(JP.getJsonObject("mls_update_date").toString().isEmpty());
				assertFalse(JP.getJsonObject("price_display").toString().isEmpty());
				assertFalse(JP.getJsonObject("price").toString().isEmpty());
				assertFalse(JP.getJsonObject("square_feet").toString().isEmpty());
			}else {
				//if the results are not ok
				throw new Exception("The API response is NOT OK");
				
			}

		}
	}
}
