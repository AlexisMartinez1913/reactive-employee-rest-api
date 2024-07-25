# Employee Management API

## Description

This is a REST API for managing employees, implemented with reactive programming using Spring Boot and MongoDB. It provides CRUD functionalities (Create, Read, Update, Delete) for efficiently managing employees.

## Technologies Used

- **Java 21**
- **Spring Boot 3**
- **Spring WebFlux**
- **Spring Data MongoDB**
- **MongoDB**
- **Project Reactor**
- **Lombok**
- **Maven**

## Prerequisites

- JDK 17 or higher
- MongoDB
- Maven

## Project Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/AlexisMartinez1913/reactive-employee-rest-api.git
   cd reactive-employee-rest-api
2. Configure your MongoDB connection in the application.properties or application.yml file:
   ```bash
   spring.data.mongodb.uri=mongodb://localhost:27017/your-database
3. Build and run the project:
   ```bash
   mvn clean install
   mvn spring-boot:run
   
## Endpoints
### Create an Employee
   ```bash
   URL: /api/employees

   Method: POST

   Request Body:
   {
   "firstName": "John",
   "lastName": "Doe",
   "email": "john.doe@example.com",
   
   }
   Response:
   {
    "id": "60c72b2f9b1e8a5b3c3e7a7e",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    }
  
```
## Get All Employees
```bash
URL: /api/employees

Method: GET

Response:
[
  {
    "id": "60c72b2f9b1e8a5b3c3e7a7e",
    "firstName": "John",
    "lastName": "Doe",
   
  }
]
```
## Get Employee by ID
```bash
URL: /api/employees/{id}

Method: GET

Response:
{
  "id": "60c72b2f9b1e8a5b3c3e7a7e",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  
}
```
## Update an Employee
```bash
URL: /api/employees/{id}

Method: PUT

Request Body:
{
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "jane.doe@example.com",
  
}

Response:

{
  "id": "60c72b2f9b1e8a5b3c3e7a7e",
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "jane.doe@example.com",
  
}
```
## Delete an Employee
```bash
URL: /api/employees/{id}

Method: DELETE

Response:
{
  "message": "Employee deleted successfully."
}
```
## Contributions
Contributions are welcome. To contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch (git checkout -b feature/new-feature).
3. Make your changes and commit them (git commit -m 'Add new feature').
4. Push to the branch (git push origin feature/new-feature).
5. Open a Pull Request.
