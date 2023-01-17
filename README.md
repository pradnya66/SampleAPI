# LMSRestAssuredAutomation

## Project Title:   
   LMSRestAssuredAutomationProject 

## Project Description:    
LMSRestAssuredAutomation project is an automation testing project in which the base url will be "https://lms-backend-service.herokuapp.com/lms".    


### Table of Contents:
1.	Tools and Technologies
2.	Frame Work
3.	Running Project
4.	Reporting

#### Tools and Technologies used in this project:

1.	##### Maven      
•	Handles the entire lifecycle of the project.    
•	Manages project dependencies.    
•	Uses the concept of POM (Project Object Model)    

2.	##### Java    
•	Object-Oriented Programming language.    
•	Java with Selenium execution is faster.    

3.	##### Rest API    
•	API that conforms to the constraints of REST architectural style and allows for interaction with RESTful web services.    
•	REST API strictly operates on the web concept of Client and Server.

4.	##### Cucumber    
•	Framework that supports Behavior Driven Development (BDD approach).   

5.	##### JUnit   
•	 Open- Source automation testing framework for Java.    
•	JUnit testing is used to test the behavior of methods inside classes we have written.      

6.	##### Log4j    
•	Logging framework which is responsible for capturing, publishing, and formatting logging information.    

7.	##### Allure reports    
•	Multi-Language test report tool.    

8.	##### Extent reports    
•	Open-source reporting library useful for test automation.  
•	 These reports are HTML documents that depict results as pie charts.

#### Framework:             
![Framework](https://user-images.githubusercontent.com/97117913/213026497-3ad656f0-56cf-4343-89bb-c7ac956d0960.png)




#### Running the project:    
    Through Eclipse:	
      	Go to TestRunner.java in  src\test\java\runner\TestRunner.java
        Right Click – run as – JUnit Test

##### Features will run in order :     
1.	BatchDELETE.feature
2.  BatchGET.feature
3.  BatchPOST.feature
4.  BatchPUT.feature
5.  ProgramDELETE.feature
6.  ProgramGET.feature
7.  ProgramPOST.feature
8.  ProgramPUT.feature    


#### Reporting      
Once tests complete run reports are generated. This framework uses different types of test reporters to communicate pass/failure.

#### Allure Report:          
Allure Report will be generated into the temp folder. To see the report
Go to command prompt
	$ cd <Project Directory>

	$ allure serve allure-results

#### HTML Report:           
HTML report will be generated in the directory: 
target/HtmlReport.html       
#### Cucumber Report:        
	The cucumber report will be generated in the directory:
			target/cucumber.html

