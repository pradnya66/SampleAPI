package stepDefinitions;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.simple.JSONObject;

import base.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.Config;

public class ProgramPostStepdefinition extends BaseClass{
	
	String uri;
	public RequestSpecification request;
	Response response;
	
	@Given("User sets request for Program module with valid base URL and valid request body")
	public void user_sets_request_for_Program_module_with_valid_base_URL_and_valid_request_body() {
		this.uri = Config.PostProgram_URL;
		this.request = RestAssured.given().header("Content-Type", "application/json");
		logger.info("Request set for Program module with valid base URL and valid data");
	}

	@When("User sends POST request with data from {string} and {int}")
	public void user_sends_POST_request_with_data_from_and(String SheetName, Integer Rownumber) throws InvalidFormatException, IOException {
		createPostRequest(SheetName, Rownumber);   
		logger.info("Post Request sent with valid request body");
	}
			
	//Created common method to post request and test each case	
	@SuppressWarnings("unchecked")
		public void createPostRequest(String SheetName, int Rownumber) throws InvalidFormatException, IOException {
		String programName = getDataFromExcel(SheetName,Rownumber).get("programName");
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
	
    @Then("Request should be successfull with status code {string} for POST request")
	public void request_should_be_successfull_with_status_code_for_POST_request(String statuscode) {
		int Poststatuscode = response.getStatusCode();
		if (Poststatuscode == 201) {
		response.then().statusCode(Integer.parseInt(statuscode));
		logger.info("Post Request Successful");
		
		
		//use delete request to delete the posted data//
		//response.getBody()
		
	}
	
	else 
		logger.info("Post Request unsuccessful with status code " + statuscode);
	}
	
	@Given("User sets request for Program module with invalid base URL and valid request body")
	public void user_sets_request_for_program_module_with_invalid_base_url_and_valid_request_body() {
		this.uri = Config.PostProgram_URL;
		this.request = RestAssured.given().header("Content-Type", "application/json");
		logger.info("Request set for Program module with invalid base URL and valid data");
	}

	@SuppressWarnings("unchecked")
	@When("User sends POST request with invalid URL and data from {string} and {int}")
	public void user_sends_post_request_with_invalid_url_and_data_from_and(String SheetName, Integer Rownumber) throws InvalidFormatException, IOException {
		String programName = getDataFromExcel(SheetName,Rownumber).get("programName");
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
				.post(this.uri + "/saveprogram")
				.then()
				.log().all().extract().response();	
		
		logger.info("Valid request body sent");
	
	}

	@Then("Bad request error message should be displayed with status code {string} for POST request with invalid URL")
	public void bad_request_error_message_should_be_displayed_with_status_code_for_post_request_with_invalid_url(String statuscode) {
		int Poststatuscode = response.getStatusCode();
		if (Poststatuscode == 404) {
		response.then().statusCode(Integer.parseInt(statuscode));
		logger.info("Bad request error message received with " + statuscode);
	}
	
	else 
		logger.info("Statuscode received" + statuscode + ". Error to be reported");
	}

	@Given("User sets request for Program module with valid base URL and empty request body")
	public void user_sets_request_for_program_module_with_valid_base_url_and_empty_request_body() {
		this.uri = Config.PostProgram_URL;
		this.request = RestAssured.given().header("Content-Type", "application/json");
		logger.info("Request set for Program module with valid base URL and empty request body");
	}

	@When("User sends POST request with empty request body")
	public void user_sends_post_request_with_empty_request_body() {
		response = this.request
				.when()
				.post(this.uri)
				.then()
				.log().all().extract().response();
		logger.info("No data sent in request body");
	}

	@Then("Bad request error message should be displayed with status code {string} for POST request with empty request body")
	public void bad_request_error_message_should_be_displayed_with_status_code_for_post_request_with_empty_request_body(String statuscode) {
		int Poststatuscode = response.getStatusCode();
		if (Poststatuscode == 400) {
		response.then().statusCode(Integer.parseInt(statuscode));
		logger.info("Bad request error message received with " + statuscode);
	}
	
	else 
		logger.info("Statuscode received" + statuscode + ". Error to be reported");
	}
	
	@Given("User sets request for Program module with valid base URL and invalid data")
	public void user_sets_request_for_program_module_with_valid_base_url_and_invalid_data() {
		this.uri = Config.PostProgram_URL;
		this.request = RestAssured.given().header("Content-Type", "application/json");
		logger.info("Request set with invalid data in the request body");
	}

	@When("User sends POST request without programName parameter in request body from {string} and {int}")
	public void user_sends_post_request_without_program_name_parameter_in_request_body_from_and(String SheetName, Integer Rownumber) throws InvalidFormatException, IOException {
		createPostRequest(SheetName, Rownumber);
		logger.info("Post request sent without programName parameter");
	}

	@Then("Bad request error message should be displayed with status code {string}")
	public void bad_request_error_message_should_be_displayed_with_status_code(String statuscode) {
		int Statuscode400 = response.getStatusCode();
		if (Statuscode400 == 400) {
		response.then().statusCode(Integer.parseInt(statuscode));
		logger.info("Bad request error message displayed with statuscode " + statuscode);
	}
	
	else 
		logger.info("Statuscode received" + statuscode + ". Error to be reported");
	}

	@When("User sends POST request with empty String value for programName parameter")
	public void user_sends_post_request_with_empty_string_value_for_program_name_parameter() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("User sends POST request with null value for programName parameter from {string} and {int}")
	public void user_sends_post_request_with_null_value_for_program_name_parameter_from_and(String SheetName, Integer Rownumber) throws InvalidFormatException, IOException {
		createPostRequest(SheetName, Rownumber);
		logger.info("Post request sent with null value in the programName parameter");
	}

	@When("User sends POST request without programDescription parameter from {string} and {int}")
	public void user_sends_post_request_without_program_description_parameter_from_and(String SheetName, Integer Rownumber) throws InvalidFormatException, IOException {
		createPostRequest(SheetName, Rownumber);
		logger.info("Post request sent without programDescription parameter");
	}

	@Then("Request should be successfull with status code {string}")
	public void request_should_be_successfull_with_status_code(String statuscode) {
		int statuscode201 = response.getStatusCode();
		if (statuscode201 == 201) {
		response.then().statusCode(Integer.parseInt(statuscode));
		logger.info("Post Request Successful with " + statuscode);
	}
	
	else 
		logger.info("Post Request unsuccessful with " + statuscode );
	}

	@When("User sends POST request with null value for programDescription parameter from {string} and {int}")
	public void user_sends_post_request_with_null_value_for_program_description_parameter_from_and(String SheetName, Integer Rownumber) throws InvalidFormatException, IOException {
		createPostRequest(SheetName, Rownumber);
		logger.info("Post request sent with null value in the programDescription parameter");
	}

	@When("User sends POST request empty String value for programDescription parameter in the request body")
	public void user_sends_post_request_empty_string_value_for_program_description_parameter_in_the_request_body() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("User sends POST request without programStatus parameter from {string} and {int}")
	public void user_sends_post_request_without_program_status_parameter_from_and(String SheetName, Integer Rownumber) throws InvalidFormatException, IOException {
		createPostRequest(SheetName, Rownumber);
		logger.info("Post request sent without programStatus parameter");
	}

	@When("User sends POST request with null value for programStatus parameter from {string} and {int}")
	public void user_sends_post_request_with_null_value_for_program_status_parameter_from_and(String SheetName, Integer Rownumber) throws InvalidFormatException, IOException {
		createPostRequest(SheetName, Rownumber);
		logger.info("Post request sent with null value in the programStatus parameter");
	}

	@When("User sends POST request with empty string value for programStatus parameter in the request body")
	public void user_sends_post_request_with_empty_string_value_for_program_status_parameter_in_the_request_body() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("User sends POST request without creationTime parameter in the request body")
	public void user_sends_post_request_without_creation_time_parameter_in_the_request_body() {
	    File jsonfile = new File("Timestamp1.json");
	    
	    response = this.request
				.body(jsonfile)
				.when()
				.post(this.uri)
				.then()
				.log().all().extract().response();
	    logger.info("Post request sent without creationTime parameter");
	}

	@When("User sends POST request without value for creationTime parameter in the request body")
	public void user_sends_post_request_without_value_for_creation_time_parameter_in_the_request_body() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("User sends POST request with creationTime parameter value as alphabets in the request body")
	public void user_sends_post_request_with_creation_time_parameter_value_as_alphabets_in_the_request_body() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("User sends POST request with creationTime parameter value as special characters in the request body")
	public void user_sends_post_request_with_creation_time_parameter_value_as_special_characters_in_the_request_body() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("User sends POST request with creationTime parameter value as Date\\(yyyy-mm-dd) format in the request body")
	public void user_sends_post_request_with_creation_time_parameter_value_as_date_yyyy_mm_dd_format_in_the_request_body() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("User sends POST request with lastMOdTime parameter value as numbers in the request body")
	public void user_sends_post_request_with_last_m_od_time_parameter_value_as_numbers_in_the_request_body() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("User sends POST request without lastModTime parameter in the request body")
	public void user_sends_post_request_without_last_mod_time_parameter_in_the_request_body() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("User sends POST request with lastModTime parameter value as empty string in the request body")
	public void user_sends_post_request_with_last_mod_time_parameter_value_as_empty_string_in_the_request_body() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("User sends POST requestwith lastModTime parameter value as alphabets in the request body")
	public void user_sends_post_requestwith_last_mod_time_parameter_value_as_alphabets_in_the_request_body() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("User sends POST requestwith lastModTime parameter value as special characters in the request body")
	public void user_sends_post_requestwith_last_mod_time_parameter_value_as_special_characters_in_the_request_body() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("User sends POST requestwith lastModTime parameter value as Date\\(yyyy-mm-dd) format in the request body")
	public void user_sends_post_requestwith_last_mod_time_parameter_value_as_date_yyyy_mm_dd_format_in_the_request_body() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

}
