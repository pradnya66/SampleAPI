@batchdel
Feature: Batch Delete
	
	@Batch_Delete_001
	Scenario Outline: Verifying DELETE request by batchId with valid batchId
		Given User sets delete request for Batch module 
		When User sends DELETE request with valid batchId from "<SheetName>" and <Rownumber>
		Then Batch delete should be successfull with status code "200" and get message "Message: Batch with Id-{batchId} deleted Successfully!"
		When User sends GET request for batchId from "<SheetName>" and <Rownumber>
		Then Batch Bad request error message should be displayed with status code "400" for GET single program for Delete
	
		Examples:
			| SheetName    | Rownumber |
	    | deleteBatch  |         0 |

	@Batch_Delete_002
	Scenario Outline: Verifying DELETE request by batchId with nonexisting valid batchId
		Given User sets delete request for Batch module
		When User sends DELETE request with nonexisting valid batchId from "<SheetName>" and <Rownumber>	
		Then Batch errorCode "ENTITY_DOES_NOT_EXIST" and errorMessage "Batch not found with Id : {batchId} " should be displayed with "400" bad request status code

		Examples:
			| SheetName    | Rownumber |
	    | deleteBatch  |         0 |
	
	@Batch_Delete_003
	Scenario Outline: Verifying DELETE request by batchId with invalid batchId
		Given User sets delete request for Batch module
		When User sends DELETE request with invalid batchId from "<SheetName>" and <Rownumber>
		Then Batch Bad request error message should be displayed with status code "400"
		
		Examples:
			| SheetName    | Rownumber |
	    | deleteBatch  |         0 |
	
	@Batch_Delete_004
	Scenario Outline: Verifying DELETE request by batchId with no batchId
		Given User sets delete request for Batch module 
		When User sends DELETE request with no batchId 
		Then Batch Not found error message should be displayed with status code "404"

	
	@Batch_Delete_005
	Scenario: Verifying DELETE request by batchId with empty string in batchId
		Given User sets delete request for Batch module 
		When User sends DELETE request with empty string in batchId 	
		Then Batch Bad request error message should be displayed with status code "400"
	
	@Batch_Delete_006
	Scenario: Verifying DELETE request by batchId with large value
		Given User sets delete request for Batch module 
		When User sends DELETE request with "9999999999"
		Then Batch Bad request error message should be displayed with status code "400"
	
	@Batch_Delete_007
	Scenario: Verifying DELETE request by batchId with boundary check
		Given User sets delete request for Batch module 
		When User sends DELETE request with "999999999"
		Then Batch errorCode "ENTITY_DOES_NOT_EXIST" and errorMessage "Batch not found with Id : {batchId} " should be displayed with "400" bad request status code
