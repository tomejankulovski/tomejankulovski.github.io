package com.houseAPIWithRestAssured;

import static org.testng.Assert.assertFalse;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.houseapi.configurations.Properties;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class HouseApiTestEmptyResults {
	public static RestAssured restAssured;

	@BeforeClass
	public static void setUp() {
		RestAssured.baseURI = Properties.getValue("ApiURL");
	}

	@Test
	public void houseApi() throws Exception {
		RequestSpecification request = RestAssured.given().log().all();

		Response response = request.queryParam("price_gte", "450000").queryParam("price_lte", "666000")
				.queryParam("city", "Los Angeles").get();
		String list = response.getBody().asString();
		System.out.println(list);
		JsonPath JP = response.jsonPath();		
		if (list.equalsIgnoreCase("[]")) {
			System.out.println("There are no houses available with that range in this city");			
		}else{
			List<String> allPrices = JP.getList("findAll{it.price <= 666000 & it.price >= 450000}.zip");
			int numberOfResults = JP.getInt("size()");
			if (numberOfResults == allPrices.size()) {
				System.out.println("The API response is OK");
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
				throw new Exception("The API response is NOT OK");
				
			}

		}
	}
}
