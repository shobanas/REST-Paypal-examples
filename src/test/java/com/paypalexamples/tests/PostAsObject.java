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
import com.paypalexamples.payment.pojo.Amount;
import com.paypalexamples.payment.pojo.Details;
import com.paypalexamples.payment.pojo.Item_list;
import com.paypalexamples.payment.pojo.Items;
import com.paypalexamples.payment.pojo.Payer;
import com.paypalexamples.payment.pojo.Payment_Options;
import com.paypalexamples.payment.pojo.PostPaymentObj;
import com.paypalexamples.payment.pojo.Redirect_Urls;
import com.paypalexamples.payment.pojo.Shipping_Address;
import com.paypalexamples.payment.pojo.Transactions;


public class PostAsObject extends BaseClass{

	protected static String payment_id;
	
@Test
public void createAPayment () {
	
	// String msgBody = "{\"intent\":\"sale\",\"payer\":{\"payment_method\":\"paypal\"},\"transactions\":[{\"amount\":{\"total\":\"30.11\",\"currency\":\"USD\",\"details\":{\"subtotal\":\"30.00\",\"tax\":\"0.07\",\"shipping\":\"0.03\",\"handling_fee\":\"1.00\",\"shipping_discount\":\"-1.00\",\"insurance\":\"0.01\"}},\"description\":\"The payment transaction description.\",\"custom\":\"EBAY_EMS_90048630024435\",\"invoice_number\":\"48787589673\",\"payment_options\":{\"allowed_payment_method\":\"INSTANT_FUNDING_SOURCE\"},\"soft_descriptor\":\"ECHI5786786\",\"item_list\":{\"items\":[{\"name\":\"hat\",\"description\":\"Brown hat.\",\"quantity\":\"5\",\"price\":\"3\",\"tax\":\"0.01\",\"sku\":\"1\",\"currency\":\"USD\"},{\"name\":\"handbag\",\"description\":\"Black handbag.\",\"quantity\":\"1\",\"price\":\"15\",\"tax\":\"0.02\",\"sku\":\"product34\",\"currency\":\"USD\"}],\"shipping_address\":{\"recipient_name\":\"Brian Robinson\",\"line1\":\"4th Floor\",\"line2\":\"Unit #34\",\"city\":\"San Jose\",\"country_code\":\"US\",\"postal_code\":\"95131\",\"phone\":\"011862212345678\",\"state\":\"CA\"}}}],\"note_to_payer\":\"Contact us for any questions on your order.\",\"redirect_urls\":{\"return_url\":\"http://www.paypal.com/return\",\"cancel_url\":\"http://www.paypal.com/cancel\"}}";
	
	// create object instances (using Pojos) and provide values to member vars
	// Construct the overall JSON object by building the individual elements
	
	String cancel_url = "http://www.paypal.com/cancel";
	String return_url = "http://www.paypal.com/return";
	Redirect_Urls redirect_urls = new Redirect_Urls();
	redirect_urls.setCancel_url(cancel_url);
	redirect_urls.setReturn_url(return_url);
	
	
	Details details = new Details();
	details.setHandling_fee("1.00");
	details.setInsurance("0.01");
	details.setShipping("0.03");
	details.setShipping_discount("-1.00");
	details.setSubtotal("30.00");
	details.setTax("0.07");
	
	// create Amount object
	Amount amount = new Amount();
	amount.setCurrency("USD");
	amount.setTotal("30.11");
	amount.setDetails(details);
	
	
	// create and init shipping_address
	Shipping_Address shipping_address = new Shipping_Address();
	shipping_address.setCity("San Jose");
	shipping_address.setCountry_code("US");
	shipping_address.setLine1("4th Floor");
	shipping_address.setLine2("Unit #34");
	shipping_address.setPhone("011862212345678");
	shipping_address.setPostal_code("95131");
	shipping_address.setRecipient_name("Brian Robinson");
	shipping_address.setState("CA");
	
	// create and init items 
	
	Items item1 = new Items();
	item1.setCurrency("USD");
	item1.setDescription("Brown Hat");
	item1.setName("hat");
	item1.setPrice("3");
	item1.setQuantity("5");
	item1.setSku("1");
	item1.setTax("0.01");
	
	
	Items item2 = new Items();
	item2.setCurrency("USD");
	item2.setDescription("Black Handbag");
	item2.setName("handbag");
	item2.setPrice("15");
	item2.setQuantity("1");
	item2.setSku("product34");
	item2.setTax("0.02");
	
	// build item_list object with items array and shipping_address
	
	List<Items> items  = new ArrayList<Items> ();
	items.add(item1);
	items.add(item2);
	
	Item_list item_list = new Item_list();
	item_list.setShipping_address(shipping_address);
	item_list.setItems(items);
		
	// set payment_options
	
	Payment_Options payment_options = new Payment_Options();
	payment_options.setAllowed_payment_method("INSTANT_FUNDING_SOURCE");
	
	// create Transactions object, higher level container
	
	Transactions transaction = new Transactions();
	transaction.setAmount(amount);
	transaction.setCustom("EBAY_EMS_90048630024435");
	transaction.setDescription("The payment transaction description.");
	transaction.setInvoice_number("48787589673");
	transaction.setItem_list(item_list);
	transaction.setPayment_options(payment_options);
	transaction.setSoft_descriptor("ECHI5786786");
	
	
	// create and init payer
	
	Payer payer = new Payer();
	payer.setPayment_method("paypal");
	
	// construct the final body object to post
	
	List<Transactions> transList = new ArrayList<Transactions>();
	transList.add(transaction);
	
	PostPaymentObj postPaymentObj = new PostPaymentObj();
	postPaymentObj.setIntent("sale");
	postPaymentObj.setNote_to_payer("Contact Us");
	postPaymentObj.setPayer(payer);
	postPaymentObj.setRedirect_urls(redirect_urls);
	postPaymentObj.setTransactions(transList);
	
	// pass the postObj in the body 
	
	payment_id = given()
	.contentType(ContentType.JSON)
	.auth()
	.oauth2(paypalAccessToken)
	.when()
	.body(postPaymentObj)
	.post("/payments/payment")
	.then()
	.log()
	.all()
	.extract()
	.path("id");
	
	
	
}
	
}
