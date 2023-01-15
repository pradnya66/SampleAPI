@prgdel
Feature: Program Delete
	
	@Program_Delete_001
	Scenario Outline: Verify DELETE request by programId with valid existing programId
		Given User sets request for Program module with valid base URL and path
		When User sends DELETE request with valid programId from "<SheetName>" and <Rownumber> 
		Then Request should be successfull with status code "200" and get message "Message: Program with Id-{programId} deleted Successfully!"
		When User sends GET request with programId from "<SheetName>" and <Rownumber>
		Then Program Bad request error message should be displayed with status code "400" for GET single program for delete
		
		Examples:
			| SheetName      	 | Rownumber |
	    | deleteProgram  |         0 |

	@Program_Delete_002
  Scenario Outline: Verifying DELETE request by programId with nonexisting valid programId
  	Given User sets request for Program module with nonexisting valid <programId>	
  	When User sends DELETE request with valid programId from "<SheetName>" and <Rownumber>
  	Then Program "program with this: {programId}not found" error message should be displayed with "400" bad request status code
	
		Examples:
			| SheetName      	 | Rownumber |
	    | deleteProgram  |         0 |
	
	@Program_Delete_003
	Scenario: Verifying DELETE request by programId with no programId
		Given User sets request for Program module with no programId
		When User sends DELETE request with no programId
		Then Program Not found error message should be displayed with status code "404"

	@Program_Delete_004
	Scenario Outline: Verifying DELETE request by programId with invalid programId
		Given User sets request for Program module with invalid <programId> 
		When User sends DELETE request with invalid programId from "<SheetName>" and <Rownumber>
		Then Program Bad request error message should be displayed with status code "400"
		
		Examples: 
			| SheetName        | Rownumber |
	    | deleteProgram  |         0 |
		 
	@Program_Delete_005
	Scenario Outline: Verifying DELETE request by programName with valid programName
		Given User sets request for Program module with valid <programName> 
		When User sends DELETE request with programName from "<SheetName>" and <Rownumber>
		Then Request should be successfull with status code "200" and receive message "Message: Program with Name-(programName) deleted Successfully!"
		
		Examples: 
			| SheetName      | Rownumber |
	    | deleteProgram  |         0 |
	
	@Program_Delete_006
	Scenario Outline: Verifying DELETE request by programName with nonexisting valid programName
		Given User sets request for Program module with nonexisting valid <programName>
		When User sends DELETE request with programName from "<SheetName>" and <Rownumber>
		Then errorCode "ENTITY_DOES_NOT_EXIST" and errorMessage "no record found with programName" should be displayed with "400" bad request status code
	
		Examples: 
			| SheetName      | Rownumber |
	    | deleteProgram  |         0 |

	@Program_Delete_007
	Scenario: Verifying DELETE request by programName with no programName
		Given User sets request for Program module with no programName
		When User sends DELETE request with no programName
		Then Program Not found error message should be displayed with status code "404"
	
	@Program_Delete_008
	Scenario: Verifying DELETE request by programName with empty string in programName
		Given User sets request for Program module with empty string in programName
		When User sends DELETE request with empty string in "programName"
		Then errorCode "ENTITY_DOES_NOT_EXIST" and errorMessage "no record found with programName" should be displayed with "400" bad request status code
