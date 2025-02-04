🚀 Overview
This is a simple Spring Boot application that provides CRUD (Create, Read, Update, Delete) operations for managing car details. The application is built using Spring Boot, JPA/Hibernate, and a SQL database.




Configure Database (PostgreSQL/MySQL):

Modify the application.properties file based on your database

spring.datasource.url=jdbc:mysql://localhost:3306/cars-app-db

spring.datasource.username=root

spring.datasource.password=root




▶️ Running the Application Using Maven

mvn clean spring-boot:run


🔍 API Endpoints

Method	Endpoint	          Description

POST	  /rest/cars	        Add a new car wth fuels details

GET	    /rest/cars/{id}	    Get car with fuel details by ID

GET	    /rest/cars	        Get all cars with fuel details with pagination

PUT	    /rest/cars/{id}	    Update car with fuel details

DELETE	/rest/cars/{id}	    Delete a car details


🧪 Running Tests

Run All Unit Tests

mvn test



## 📩 Postman Collection

Click the button below to import the Postman collection:


