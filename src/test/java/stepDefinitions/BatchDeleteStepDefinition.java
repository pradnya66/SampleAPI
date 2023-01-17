package stepDefinitions;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.simple.JSONObject;
import org.junit.Assert;

import com.google.gson.JsonObject;

import base.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.Config;

public class BatchDeleteStepDefinition extends BaseClass {
	
	String uri;
	public RequestSpecification request;
	int status;
	JsonObject jsonObject;
	String basePathDelByBatchId = "/batches";
	Response response;
	String jsonAsString;
	String batchId;
	String programId;
	
	@Given("User sets delete request for Batch module")
	public void user_sets_delete_request_for_batch_module() throws InvalidFormatException, IOException {
		createBatchPostRequest("PostValidDataBatch", 0);
		this.uri = Config.BASE_URL + this.basePathDelByBatchId;
		this.request = RestAssured.given().header("Content-Type", "application/json");
	}
	
	@SuppressWarnings("unchecked")
	public void createBatchPostRequest(String SheetName, int Rownumber) throws InvalidFormatException, IOException {
		createPostRequest("postvaliddataprogram", 2);
		
//		if(response == null) {
//			programId = "509";
//		} else {
//			programId = response.getBody().jsonPath().get("programId").toString();
//		}
		
		programId = "9062";
		
		this.uri = Config.PostBatch_URL;
		this.request = RestAssured.given().header("Content-Type", "application/json");
		
		String batchName = getDataFromExcel(SheetName,Rownumber).get("batchName")+BaseClass.randomestring();
		String batchDescription = getDataFromExcel(SheetName,Rownumber).get("batchDescription");
		String batchStatus = getDataFromExcel(SheetName,Rownumber).get("batchStatus");
		String batchNoOfClasses = "12";
	
		JSONObject body = new JSONObject();
		body.put("batchName", batchName);
		body.put("batchDescription", batchDescription);
		body.put("batchStatus", batchStatus);
		body.put("batchNoOfClasses", batchNoOfClasses);
		body.put("programId", programId);
		
		response = this.request
				.body(body.toJSONString())
				.when()
				.post(this.uri)
				.then()
				.log().all().extract().response();	
		
		int statusCd = response.getStatusCode();
		if (statusCd == 201) {
			response.then().statusCode(Integer.parseInt("201"));
			logger.info("Post batch request status "+ 201);
		}
		else { 
			logger.info("Delete Request unsuccessful");
		}
		
//		if (batchId != null){ 
//			logger.info("batchId to test delete batch is created successfully");
//		}else {
//			logger.info("batchId to test delete batch failed");
//		}
	}
	
	@SuppressWarnings("unchecked")
	public void createPostRequest(String SheetName, int Rownumber) throws InvalidFormatException, IOException {
		this.uri = Config.PostProgram_URL;
		this.request = RestAssured.given().header("Content-Type", "application/json");
		
		String programName = getDataFromExcel(SheetName,Rownumber).get("programName")+BaseClass.randomestring();
		String programDescription = getDataFromExcel(SheetName,Rownumber).get("programDescription");
		String programStatus = getDataFromExcel(SheetName,Rownumber).get("programStatus");
		String creationTime = Timestamp();
		String lastModTime = Timestamp();
	
		JSONObject body = new JSONObject();
		body.put("programName", programName);
		body.put("programDescription", programDescription);
		body.put("programStatus", programStatus);
		body.put("creationTime", creationTime);
		body.put("lastModTime", lastModTime);
		
		response = this.request
				.body(body.toJSONString())
				.when()
				.post(this.uri)
				.then()
				.log().all().extract().response();	
		
	}
	
	@When("User sends DELETE request with valid batchId from {string} and {int}")
	public void user_sends_delete_request_with_valid_batch_id_from_and(String SheetName, int Rownumber) throws InvalidFormatException, IOException {
		String batchId = getDataFromExcel(SheetName, Rownumber).get("batchId");
		if(response !=null) {
			batchId = response.getBody().jsonPath().get("batchId").toString(); 
		}
		this.batchId = batchId;
		sendDeleteBatchId(batchId);
	}
	
	@Then("Batch delete should be successfull with status code {string} and get message {string}")
	public void batch_delete_should_be_successfull_with_status_code_and_get_message(String statusCode, String message) {
		statusCode200Validation(statusCode, 
				"Delete Request to delete single program by id is successful", 
				"Delete program by id request unsuccessful");	
		jsonAsString = response.asString();
		message = message.replace("{batchId}", batchId);
		Assert.assertEquals(true, jsonAsString.contains(message));
		logger.info("Batch delete is successfull with status code 200 and get message");

		// deleting program created 
//		this.uri = Config.BASE_URL + "/deletebyprogid"; 
//		this.request = RestAssured.given().header("Content-Type", "application/json");
//		
//		response = this.request
//				.when()
//				.delete(this.uri + "/" + programId)
//				.then()
//				.log().all().extract().response();
		
	}
		
	@When("User sends GET request for batchId from {string} and {int}")
	public void user_sends_get_request_for_batch_id_from_and(String SheetName, int Rownumber) throws InvalidFormatException, IOException {
		this.uri = Config.BASE_URL + "/batches/batchId"; 
		this.request = RestAssured.given().header("Content-Type", "application/json");
		
		response = this.request
				.when()
				.get(this.uri + "/" + batchId)
				.then()
				.log().all().extract().response();
	}
	@Then("Batch Bad request error message should be displayed with status code {string} for GET single program for Delete")
	public void batch_bad_request_error_message_should_be_displayed_with_status_code_for_get_single_program_for_delete(String statusCd) {
		int GetBatchstatuscode = response.getStatusCode();
		if (GetBatchstatuscode == 400) {
			response.then().statusCode(Integer.parseInt(statusCd));
			logger.info("Status code "+GetBatchstatuscode+ "received for GET single program with invalid program ID");
		}
	}
	
	@When("User sends DELETE request with nonexisting valid batchId from {string} and {int}")
	public void user_sends_delete_request_with_nonexisting_valid_batch_id_from_and(String SheetName, int Rownumber) throws InvalidFormatException, IOException {
	    batchId = getDataFromExcel(SheetName, Rownumber).get("batchId");
	    sendDeleteBatchId(batchId);
	}
	
	@Then("Batch errorCode {string} and errorMessage {string} should be displayed with {string} bad request status code")
	public void batch_error_code_and_error_message_should_be_displayed_with_bad_request_status_code(String errorCode, String errorMessage, String statusCode) {
		int statusCd = response.getStatusCode();
		if (statusCd == 400) {
			response.then().statusCode(Integer.parseInt(statusCode));
			logger.info("Status code 400 received for DELETE by programName with invalid program ID");
			System.out.println("works");
			System.out.println("errorCode right here: "+ response.getBody().jsonPath().get("errorCode"));
			// 404 schema same for 400 schema
			response.then().assertThat()
			.body(JsonSchemaValidator.matchesJsonSchema
					(new File("src/test/resources/JsonSchemaProgramDelete/deleteprogramByName400schema.json")));
			if(batchId != null) {
				errorMessage = errorMessage.replace("{batchId}", batchId);
			}
			System.out.println("errorMessage here is : " + errorMessage);
			Assert.assertEquals(true,response.getBody().asString().contains(errorCode));
			Assert.assertEquals(true,response.getBody().asString().contains(errorMessage));
			logger.info("Delete batch request status 400 with message batch not found with id "+ batchId);
		}
		else { 
			logger.info("Delete Request unsuccessful");
		}
	}
	
	@When("User sends DELETE request with invalid batchId from {string} and {int}")
	public void user_sends_delete_request_with_invalid_batch_id_from_and(String SheetName, int Rownumber) throws InvalidFormatException, IOException {
	    String batchId = getDataFromExcel(SheetName, Rownumber).get("batchId");
		sendDeleteBatchId(batchId + " *@@");
	}
	
	@Then("Batch Bad request error message should be displayed with status code {string}")
	public void batch_bad_request_error_message_should_be_displayed_with_status_code(String statusCode) {
		int statusCd = response.getStatusCode();
		if (statusCd == 400) {
			response.then().statusCode(Integer.parseInt(statusCode));
			

			// 404 schema same for 400 schema
			response.then().assertThat()
			.body(JsonSchemaValidator.matchesJsonSchema
					(new File("src/test/resources/JsonSchemaProgramDelete/deleteprogram404schema.json")));
			logger.info("Status code 400 received for DELETE single program with invalid program ID");
		}
		else { 
			logger.info("Delete Request unsuccessful");
		}
	}
	
	@When("User sends DELETE request with no batchId")
	public void user_sends_delete_request_with_no_batch_id() {
	    sendDeleteBatchId(null);
	}
	
	@Then("Batch Not found error message should be displayed with status code {string}")
	public void batch_not_found_error_message_should_be_displayed_with_status_code(String statusCode) {
		int statusCd = response.getStatusCode();
		if (statusCd == 404) {
			response.then().statusCode(Integer.parseInt(statusCode));
//			response.then().assertThat().headers("Vary", "Access-Control-Request-Method");
			response.then().assertThat()
			.body(JsonSchemaValidator.matchesJsonSchema
					(new File("src/test/resources/JsonSchemaProgramDelete/deleteprogram404schema.json")));
			logger.info("Status code 404 received for DELETE single program with invalid URL");
		}
	}

	@When("User sends DELETE request with empty string in batchId")
	public void user_sends_delete_request_with_empty_string_in_batch_id() {
	    sendDeleteBatchId("");
	}


	@When("User sends DELETE request with {string}")
	public void user_sends_delete_request_with(String batchId) {
		this.batchId = batchId;
	    sendDeleteBatchId(batchId);
	}
	
	
	public void statusCode200Validation (String statusCode, String logSuccess, String logFail) {
		int statusCd = response.getStatusCode();
		if (statusCd == 200) {
			response.then().statusCode(Integer.parseInt(statusCode));
			response.then().assertThat().header("Connection", "keep-alive");
			logger.info(logSuccess);
		}
		else {
			logger.info(logFail);
		}
	}
		
	public void sendDeleteBatchId(String batchId) {
		response = this.request
				.when()
				.delete(this.uri + "/" + batchId)
				.then()
				.log().all().extract().response();
	}
	
}
