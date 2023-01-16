package stepDefinitions;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.google.gson.JsonObject;

import base.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.Config;

public class BatchGetStepdefinitions extends BaseClass {
	String uri;
	public RequestSpecification request;
	int status;
	JsonObject jsonObject;
	Response response;
	String jsonAsString;

	@Given("User sets request for Batch module with valid base URL")
	public void user_sets_request_for_batch_module_with_valid_base_url() {
		this.uri = Config.GetAllBatch_URL;
		this.request = RestAssured.given().header("Content-Type", "application/json");
	}
	
	@When("User sends GET request to batch module")
	public void user_sends_get_request_to_batch_module() {
		response = this.request.get(this.uri);	
		response.then().log().all();
	}

	@Then("Request should be successfull with status code {string} for GET All batches")
	public void request_should_be_successfull_with_status_code_for_get_all_batches(String statuscode) {
		int GetAllstatuscode = response.getStatusCode();
		if (GetAllstatuscode == 200) {
		response.then().statusCode(Integer.parseInt(statuscode));
		response.then().assertThat().header("Connection", "keep-alive");
		logger.info("Get Request to fetch all program data is successfull");
	}
	
	else if (GetAllstatuscode == 404) {
		logger.info("Get Request unsuccessful");
	
	}
	}
	@Given("User sets request for Batch module with invalid base URL")
	public void user_sets_request_for_batch_module_with_invalid_base_url() {
		this.uri = Config.GetAllBatch_URL;
		this.request = RestAssured.given().header("Content-Type", "application/json");
	}

	@When("User sends GET request to batch invalid URL")
	public void user_sends_get_request_to_batch_invalid_url() {
		response = this.request.get(this.uri + "/" + "*" );	
		response.then().log().all();
	}

	@Then("Not found error message should be displayed with status code {string} for GET All batches")
	public void not_found_error_message_should_be_displayed_with_status_code_for_get_all_batches(String statuscode) {
		int GetAllstatuscode = response.getStatusCode();
		if (GetAllstatuscode == 404) {
		response.then().statusCode(Integer.parseInt(statuscode));
		//response.then().assertThat().header("Vary", "Access-Control-Request-Method");
		logger.info("Status code 404 received for GET all program with invalid URL");
	}
	
	else 
		logger.info("Get Request unsuccessful");
	
	}
	

@Given("User sets request for Batch module with valid base URL and valid path")
public void user_sets_request_for_batch_module_with_valid_base_url_and_valid_path() {
	this.uri = Config.GetAllBatch_URL;
	this.request = RestAssured.given().header("Content-Type", "application/json");
}

@When("User sends GET request with valid batch ID from {string} and {int}")
public void user_sends_get_request_with_valid_batch_id_from_and(String SheetName, Integer Rownumber) throws InvalidFormatException, IOException {
	String batchId = getDataFromExcel(SheetName, Rownumber).get("batchId");
	
	
	response = this.request
			.when()
			.get(Config.GetSingleBatch_URL + "/" + batchId)
			.then()
			.log().all().extract().response();
}

@Then("Request should be successfull with status code {string} for GET single batch")
public void request_should_be_successfull_with_status_code_for_get_single_batch(String statuscode) {
	statusCodeValidation(statuscode, "Get Request to fetch single batch data is successful", 
			"Get Request is unsuccessful");	
}
public void statusCodeValidation (String statusCode, String logSuccess, String logFail) {
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
@Given("User sets request for Batch module with valid base URL and invalid path")
public void user_sets_request_for_batch_module_with_valid_base_url_and_invalid_path() {
	this.uri = Config.GetAllBatch_URL;
	this.request = RestAssured.given().header("Content-Type", "application/json");
}

@When("User sends GET request with invalid path")
public void user_sends_get_request_with_invalid_path() {
	response = this.request
			.when()
			.get(Config.GetSingleBatch_URL + "/" + randomestring())
			.then()
			.log().all().extract().response();
}

@Then("Bad request error message should be displayed with status code {string} for invalid path")
public void bad_request_error_message_should_be_displayed_with_status_code_for_invalid_path(String statuscode) {
		statusCodeValidation(statuscode, "Status code 400 received for batch module invalid path", 
				"Get Request is unsuccessful");	
	}
	public void statusCodeValidation1 (String statusCode, String logSuccess, String logFail) {
		int statusCd = response.getStatusCode();
		if (statusCd == 400) {
			response.then().statusCode(Integer.parseInt(statusCode));
			response.then().assertThat().header("Connection", "keep-alive");
			logger.info(logSuccess);
		}
		else {
			logger.info(logFail);
		}
}
	@When("User sends GET request with invalid batch ID from {string} and {int}")
	public void user_sends_get_request_with_invalid_batch_id_from_and(String SheetName, Integer Rownumber) throws InvalidFormatException, IOException {
		String batchId = getDataFromExcel(SheetName, Rownumber).get("batchId");
		
		
		response = this.request
				.when()
				.get(Config.GetSingleBatch_URL + "/" + batchId)
				.then()
				.log().all().extract().response();
	}
	

	@Then("Request should be unsuccessfull with status code {string} for GET single batch invalid ID")
	public void request_should_be_unsuccessfull_with_status_code_for_get_single_batch_invalid_id(String statuscode) {
		statusCodeValidation(statuscode, "Get Request to fetch single batch data is successful", 
				"Get Request is unsuccessful");	
	}
	public void statusCodeValidation2(String statusCode, String logSuccess, String logFail) {
		int statusCd = response.getStatusCode();
		if (statusCd == 400) {
			response.then().statusCode(Integer.parseInt(statusCode));
			response.then().assertThat().header("Connection", "keep-alive");
			logger.info(logSuccess);
		}
		else {
			logger.info(logFail);
		}
	}
	
	@When("User sends GET request with valid batch Name from {string} and {int}")
	public void user_sends_get_request_with_valid_batch_name_from_and(String SheetName, Integer Rownumber) throws InvalidFormatException, IOException {
		String batchName = getDataFromExcel(SheetName, Rownumber).get("batchName");
		
		
		response = this.request
				.when()
				.get(Config.GetSingleBatchName_URL + "/" + batchName)
				.then()
				.log().all().extract().response();
	}

	@Then("Request should be successfull with status code {string} for GET single batch Name")
	public void request_should_be_successfull_with_status_code_for_get_single_batch_name(String statuscode) {
		statusCodeValidation(statuscode, "Get Request to fetch single batch name is successful", 
				"Get Request is unsuccessful");	
	}
	public void statusCodeValidation3(String statusCode, String logSuccess, String logFail) {
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
	
	@When("User sends GET request with invalid batch Name from {string} and {int}")
	public void user_sends_get_request_with_invalid_batch_name_from_and(String SheetName, Integer Rownumber) throws InvalidFormatException, IOException {
String batchName = getDataFromExcel(SheetName, Rownumber).get("batchName");
		
		
		response = this.request
				.when()
				.get(Config.GetSingleBatchName_URL + "/" + batchName)
				.then()
				.log().all().extract().response();
	}

	@Then("Request should be unsuccessfull with status code {string} for GET single batch invalid Name")
	public void request_should_be_unsuccessfull_with_status_code_for_get_single_batch_invalid_name(String statuscode) {
		statusCodeValidation(statuscode, "Get Request to fetch single batch invalid name is successful", 
				"Get Request is unsuccessful");	
	}
	public void statusCodeValidation4(String statusCode, String logSuccess, String logFail) {
		int statusCd = response.getStatusCode();
		if (statusCd == 400) {
			response.then().statusCode(Integer.parseInt(statusCode));
			response.then().assertThat().header("Connection", "keep-alive");
			logger.info(logSuccess);
		}
		else {
			logger.info(logFail);
		}
	}
	
	@When("User sends GET request with valid batch program ID from {string} and {int}")
	public void user_sends_get_request_with_valid_batch_program_id_from_and(String SheetName, Integer Rownumber) throws InvalidFormatException, IOException {
String programId = getDataFromExcel(SheetName, Rownumber).get("programId");
		
		
		response = this.request
				.when()
				.get(Config.GetBatchByProgramId_URL + "/" + programId)
				.then()
				.log().all().extract().response();
	}

	@Then("Request should be successfull with status code {string} for GET single batch program ID")
	public void request_should_be_successfull_with_status_code_for_get_single_batch_program_id(String statuscode) {
		statusCodeValidation(statuscode, "Get Request to fetch single batch program ID is successful", 
				"Get Request is unsuccessful");	
	}
	public void statusCodeValidation5(String statusCode, String logSuccess, String logFail) {
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
	
	@When("User sends GET request with invalid batch program ID from {string} and {int}")
	public void user_sends_get_request_with_invalid_batch_program_id_from_and(String SheetName, Integer Rownumber) throws InvalidFormatException, IOException {
String programId = getDataFromExcel(SheetName,Rownumber).get("programId");
		
		
		response = this.request
				.when()
				.get(Config.GetBatchByProgramId_URL + "/" + programId)
				.then()
				.log().all().extract().response();
	}

	@Then("Request should be successfull with status code {string} for GET single batch program invalidID")
	public void request_should_be_successfull_with_status_code_for_get_single_batch_program_invalid_id(String statuscode) {
		statusCodeValidation(statuscode, "Get Request to fetch single batch program ID is successful", 
				"Get Request is unsuccessful");	
	}
	public void statusCodeValidation6(String statusCode, String logSuccess, String logFail) {
		int statusCd = response.getStatusCode();
		if (statusCd == 400) {
			response.then().statusCode(Integer.parseInt(statusCode));
			response.then().assertThat().header("Connection", "keep-alive");
			logger.info(logSuccess);
		}
		else {
			logger.info(logFail);
		}
	}
}
	