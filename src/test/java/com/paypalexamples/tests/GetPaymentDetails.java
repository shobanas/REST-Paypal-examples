package com.paypalexamples.tests;
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
import com.paypalexamples.base.BaseClass;


// run the postAsobj test first to get payment id before you do a GET request on it

public class GetPaymentDetails extends PostAsObject{

	// GET on resource payments/payment/payment id 
	// payment id will be in the Post test response when the payment obj is created
	
	@Test
	public void getInfo() {
		given()
		.auth()
		.oauth2(paypalAccessToken)
		.when()
		.get("payments/payment/"+payment_id)
		.then()
		.log()
		.all();
		
	}
}
