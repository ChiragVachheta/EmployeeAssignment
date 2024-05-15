Notes: spring boot 2.X, Java 1.8,JPA Crud,JWT, Swagger, JUnit
-> This project is in Running condition
-> TO run project fist change mysql connection details and database name accordingly
-> first time whene you run this project please update Hibernate Properties as create like exmaple :: spring.jpa.hibernate.ddl-auto=create, this will create tables in database
-> I have also provided postman collection in git repository 
After runnig Application :
step 1: Add User using "Add User" Api  http://localhost:8081/bizsense/employee
Step 2:Get JWT token with "Get Token" http://localhost:8081/auth/login
Now you can use any Apis from list of postman collection 

Unit Test case For Demo purpose i have added Two Test case for Token and create Employee in test package as AuthApiControllerTest and EmployeeControllerTest respectively , So you can run and test it.
