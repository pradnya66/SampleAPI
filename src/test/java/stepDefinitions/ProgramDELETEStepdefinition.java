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

public class ProgramDELETEStepdefinition extends BaseClass{
	
	String uri;
	public RequestSpecification request;
	int status;
	JsonObject jsonObject;
	String basePathDelByProgramId = "/deletebyprogid";
	String basePathDelByProgramName = "/deletebyprogname";
	Response response;
	String jsonAsString;
	String programId;
	
	@Given("User sets request for Program module with valid base URL and path")
	public void user_sets_request_for_program_module_with_valid_base_url_and_path() throws InvalidFormatException, IOException {
		createPostRequest("postvaliddataprogram", 2);
		setDeleteProgramId();
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

	@When("User sends DELETE request with valid programId from {string} and {int}")
	public void user_sends_delete_request_with_valid_program_id_from_and(String SheetName, int Rownumber) throws InvalidFormatException, IOException {
		String programId = getDataFromExcel(SheetName, Rownumber).get("programId");
		
		if (response != null) {
			if (response.getStatusCode() == 201) {
				programId = response.getBody().jsonPath().get("programId").toString();
			}
		}
		System.out.println(" programId created for delete is: "+programId);
		sendDeleteProgramId(programId);
		this.programId = programId;
	}
	
	@Then("Request should be successfull with status code {string} and get message {string}")
	public void request_should_be_successfull_with_status_code_and_get_message(String statusCode, String message) {
		statusCode200Validation(statusCode, 
				"Delete Request to delete single program by id is successful", 
				"Delete program by id request unsuccessful");	
		jsonAsString = response.asString();
		message = message.replace("{programId}", programId);
		Assert.assertEquals(true, jsonAsString.contains(message));
		logger.info("Delete Request to delete program by id is successful");
	}
	
	@When("User sends GET request with programId from {string} and {int}")
	public void user_sends_get_request_with_program_id_from_and(String SheetName, int Rownumber) throws InvalidFormatException, IOException {
		this.uri = Config.BASE_URL + "/Programs"; 
		this.request = RestAssured.given().header("Content-Type", "application/json");
		
		String programId = getDataFromExcel(SheetName, Rownumber).get("programId");
		response = this.request
				.when()
				.get(this.uri + "/" + programId)
				.then()
				.log().all().extract().response();
	}
	
	@Then("Program Bad request error message should be displayed with status code {string} for GET single program for delete")
	public void program_bad_request_error_message_should_be_displayed_with_status_code_for_get_single_program_for_delete(String statuscode) {
		int GetPrgstatuscode = response.getStatusCode();
		if (GetPrgstatuscode == 400) {
		response.then().statusCode(Integer.parseInt(statuscode));
		logger.info("Status code 400 received for GET single program with invalid program ID");
	}
	else 
		logger.info("Get Request unsuccessful");
	}
	
	@Given("User sets request for Program module with nonexisting valid <programId>")
	public void user_sets_request_for_program_module_with_nonexisting_valid_program_id() {
	    setDeleteProgramId();
	}
	
	@Then("Program {string} error message should be displayed with {string} bad request status code")
	public void program_error_message_should_be_displayed_with_bad_request_status_code(String message, String statusCode) {
		int statusCodeActual = response.getStatusCode();
		if (statusCodeActual == 400) {
			response.then().statusCode(Integer.parseInt(statusCode));
			logger.info("Status code 400 received for Delete single program with invalid program ID");
		}
		else { 
			logger.info("Get Request successful");
		}
	}
	
	@Given("User sets request for Program module with no programId")
	public void user_sets_request_for_program_module_with_no_program_id() {
		setDeleteProgramId();
	}
	
	@When("User sends DELETE request with no programId")
	public void user_sends_delete_request_with_no_program_id() {
		response = this.request
				.when()
				.delete(this.uri + "/")
				.then()
				.log().all().extract().response();
	}
	
	@Then("Program Not found error message should be displayed with status code {string}")
	public void program_not_found_error_message_should_be_displayed_with_status_code(String statusCode) {
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
	
	@Given("User sets request for Program module with invalid <programId>")
	public void user_sets_request_for_program_module_with_invalid_program_id() {
	    setDeleteProgramId();
	}
	@When("User sends DELETE request with invalid programId from {string} and {int}")
	public void user_sends_delete_request_with_invalid_program_id_from_and(String SheetName, Integer Rownumber) throws InvalidFormatException, IOException {
		String programId = getDataFromExcel(SheetName, Rownumber).get("programId");
	    sendDeleteProgramId(programId+BaseClass.randomestring()+"@");
	}
	@Then("Program Bad request error message should be displayed with status code {string}")
	public void program_bad_request_error_message_should_be_displayed_with_status_code(String statusCode) {
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
	
	@Given("User sets request for Program module with valid <programName>")
	public void user_sets_request_for_program_module_with_valid_program_name() throws InvalidFormatException, IOException {
		createPostRequest("postvaliddataprogram", 2);
		setDeleteProgramName();
	}
	
	@When("User sends DELETE request with programName from {string} and {int}")
	public void user_sends_delete_request_with_program_name_from_and(String SheetName, int Rownumber) throws InvalidFormatException, IOException {
		String programName = getDataFromExcel(SheetName, Rownumber).get("programName");
		
		if (response != null) {
			if (response.getStatusCode() == 201) {
				programName = response.getBody().jsonPath().get("programName");
			}
		}
	    sendDeleteProgramName(programName);
	}
	
	@Then("Request should be successfull with status code {string} and receive message {string}")
	public void request_should_be_successfull_with_status_code_and_receive_message(String statusCode, String message) {
		statusCode200Validation(statusCode, 
				"Delete Request to delete single program by Name is successful", 
				"Delete program by Name request unsuccessful");	
		jsonAsString = response.asString();
		Assert.assertEquals(true, jsonAsString.contains(message));
		logger.info("Delete Request to delete single program by Name is successful with message");
	}
	
	@Given("User sets request for Program module with nonexisting valid <programName>")
	public void user_sets_request_for_program_module_with_nonexisting_valid_program_name() {
		setDeleteProgramName();
	}
	
	@Then("errorCode {string} and errorMessage {string} should be displayed with {string} bad request status code")
	public void error_code_and_error_message_should_be_displayed_with_bad_request_status_code(String errorCode, String errorMessage, String statusCode) {
		int statusCd = response.getStatusCode();
		if (statusCd == 400) {
			response.then().statusCode(Integer.parseInt(statusCode));
			logger.info("Status code 400 received for DELETE by programName with invalid program ID");
			System.out.println("works");
			System.out.println("errorCode right here: "+ response.getBody().jsonPath().get("errorCode"));
			
			String message = response.getBody().jsonPath().getString("errorMessage");
			message = errorMessage.replace("{batchId}", "999999999");
			
			// 404 schema same for 400 schema
			response.then().assertThat()
			.body(JsonSchemaValidator.matchesJsonSchema
					(new File("src/test/resources/JsonSchemaProgramDelete/deleteprogramByName400schema.json")));
			
			Assert.assertEquals(true,response.getBody().asString().contains(errorCode));
			Assert.assertEquals(true,response.getBody().asString().contains(errorMessage));
			logger.info("Status code 400 received for DELETE by programName with invalid program ID Asserted for Schema and its values");
		}
		else { 
			logger.info("Delete Request unsuccessful");
		}
	}
	
	@Given("User sets request for Program module with no programName")
	public void user_sets_request_for_program_module_with_no_program_name() {
	    setDeleteProgramName();
	}
	@When("User sends DELETE request with no programName")
	public void user_sends_delete_request_with_no_program_name() {
	    sendDeleteProgramName(null);
	}
	
	@Given("User sets request for Program module with empty string in programName")
	public void user_sets_request_for_program_module_with_empty_string_in_program_name() {
	    setDeleteProgramId();
	}
	@When("User sends DELETE request with empty string in {string}")
	public void user_sends_delete_request_with_empty_string_in(String string) throws InvalidFormatException, IOException {
	    sendDeleteProgramId("");
	}
	
	public void sendDeleteProgramId(String programId) throws InvalidFormatException, IOException {
		response = this.request
				.when()
				.delete(this.uri + "/" + programId)
				.then()
				.log().all().extract().response();
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
	
	public void setDeleteProgramId() {
		this.uri = Config.BASE_URL + this.basePathDelByProgramId; 
		this.request = RestAssured.given().header("Content-Type", "application/json");
	}

	public void setDeleteProgramName() {
		this.uri = Config.BASE_URL + this.basePathDelByProgramName;
		this.request = RestAssured.given().header("Content-Type", "application/json");
	}
	
	public void sendDeleteProgramName(String programName) {
		response = this.request
				.when()
				.delete(this.uri + "/" + programName)
				.then()
				.log().all().extract().response();
	}
	
}
