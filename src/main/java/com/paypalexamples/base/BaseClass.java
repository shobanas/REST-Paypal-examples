package com.paypalexamples.base;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class BaseClass {

	public static String paypalAccessToken;
	public static final String clientId = "AapJm1sgDYTBGTBLQfp00UjwoEH5VIONf7565dGYKNZksoUPyFw8-vG3f0JO4vc8kPSmpJS0csxaSQ5m";
	public static final String clientSecret 
	= "EKb_FlTUnI2Uvs_i8z4v3V5IwHGfFqhF9KnLY1GRjX0LjbeBG2kDrxgDDKsVnCEy7XZyYeR_HGPv_zVa";
	
	// Get the access token from Paypal OAuth server
	// use this in other REST API requests
	
	@BeforeClass
	public static void getToken () {
		
		RestAssured.baseURI = "https://api.sandbox.paypal.com";
		RestAssured.basePath = "/v1";
		
		
		paypalAccessToken = given()
		.parameters("grant_type", "client_credentials")
		.auth()
		.preemptive()
		.basic(clientId, clientSecret)
		.when()
		.post("/oauth2/token")
		.then()
		.log()
		.all()
		.extract()
		.path("access_token");
		
		System.out.println("Paypal access token is : " + paypalAccessToken);
	}
	
	// @Test
	// public void testToken () {
		
	// }
	
	
}
