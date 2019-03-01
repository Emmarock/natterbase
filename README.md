# Getting Started

### Guides
The following guides illustrates how to run this application:

* To run this application, modify the application.properties file to reflect your database configuration to use the in memory database, comment out the properties file

* run ``mvn spring-boot:run``
* when the application starts, create a user the swagger documentation here http://localhost:8080/swagger-ui.html 
* login with the new username and password created in the above step, a token will be returned in the header
e.g `` Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU1MTQ3MDc3OX0.gmMdXqvRWYUVTxwB-qm29ThlhbnAG6OPhlAWpD2yS_Dv_TRPgT8X4b1LR4Dgq9q8s4bN_KAxGFvFWveoxsPjAw ``

* use the token generated in the above step to call all the other API's 

* To run the unit test and the integration test
    run ``mvn test ``
* after the application must have start, the swagger documentation can be found at http://localhost:8080/swagger-ui.html

* The API spec can also be found here http://localhost:8080/v2/api-docs 