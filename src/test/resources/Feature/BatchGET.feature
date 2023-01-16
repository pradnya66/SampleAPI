@batchget
Feature: Batch-Get Requests

@Batch_Get_001
Scenario: Verifying GET request to retrieve all batch data in valid URL
    Given User sets request for Batch module with valid base URL
    When User sends GET request to batch module
    Then Request should be successfull with status code "200" for GET All batches

@Batch_Get_002    
Scenario: Verifying GET request to retrieve all batch data with invalid URL
    Given User sets request for Batch module with invalid base URL
    When User sends GET request to batch invalid URL
    Then Not found error message should be displayed with status code "404" for GET All batches
    
@Batch_Get_003
Scenario Outline: Verifying GET request to retrieve single batch data with valid batch ID
  	Given User sets request for Batch module with valid base URL and valid path
  	When User sends GET request with valid batch ID from "<SheetName>" and <Rownumber> 
  	Then Request should be successfull with status code "200" for GET single batch  
  	
  	Examples: 
      | SheetName        | Rownumber |
      | getsinglebatch   |     0     |
   
 @Batch_Get_004
 Scenario Outline: Verifying GET request to retrieve single batch data with invalid path
  	Given User sets request for Batch module with valid base URL and invalid path
  	When User sends GET request with invalid path
  	Then Bad request error message should be displayed with status code "400" for invalid path
  	
@Batch_Get_005
Scenario Outline: Verifying GET request to retrieve single batch data with invalid batch ID
  	Given User sets request for Batch module with valid base URL and invalid path
  	When User sends GET request with invalid batch ID from "<SheetName>" and <Rownumber> 
  	Then Request should be unsuccessfull with status code "400" for GET single batch invalid ID
  	
  	Examples: 
      | SheetName                 | Rownumber |
      | getsinglebatchinvalidid   |     0     |
      
@Batch_Get_006
Scenario Outline: Verifying GET request to retrieve single batch data with valid batch Name
  	Given User sets request for Batch module with valid base URL and valid path
  	When User sends GET request with valid batch Name from "<SheetName>" and <Rownumber> 
  	Then Request should be successfull with status code "200" for GET single batch Name 
  	
  	Examples: 
      | SheetName            | Rownumber |
      | getsinglebatchname   |     0     |
  
  
@Batch_Get_007
Scenario Outline: Verifying GET request to retrieve single batch data with invalid batch Name
  	Given User sets request for Batch module with valid base URL and invalid path
  	When User sends GET request with invalid batch Name from "<SheetName>" and <Rownumber> 
  	Then Request should be unsuccessfull with status code "400" for GET single batch invalid Name
  	
  	Examples: 
      | SheetName                 | Rownumber |
      | getinvalidbatchname       |     0     |
 
 @Batch_Get_008
 Scenario Outline: Verifying GET request to retrieve single batch data with valid program ID
  	Given User sets request for Batch module with valid base URL and valid path 
  	When User sends GET request with valid batch program ID from "<SheetName>" and <Rownumber> 
  	Then Request should be successfull with status code "200" for GET single batch program ID
  	
  	Examples: 
      | SheetName           | Rownumber |
      | getsingleprogram    |     0     |

@Batch_Get_009
Scenario Outline: Verifying GET request to retrieve single batch data with invalid program ID
  	Given User sets request for Batch module with valid base URL and valid path 
  	When User sends GET request with invalid batch program ID from "<SheetName>" and <Rownumber> 
  	Then Request should be successfull with status code "400" for GET single batch program invalidID
  	
  	Examples: 
      | SheetName                 | Rownumber |
      | getinvalidbatchprogramid  |     0     |
      
    