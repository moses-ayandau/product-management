# Product Management API
## Overview
### The Product Management API is a RESTful web service designed for managing products, categories, and orders in an e-commerce system. It supports core functionalities like creating, updating, retrieving, and deleting products, as well as managing their categories and associated orders.

## Features
- CRUD operations for Products.
- Category assignment and retrieval for products.
- Image URLs for products stored as downloadable links.
- Order and cart management with seamless integration to products.
- Use of MongoDB Atlas for image storage and PostgreSQL for other relational data.
- Optimized with DTOs and mappers for clean data handling.

## Technologies Used
- Backend Framework: Spring Boot 3.x
- Database: PostgreSQL (for core relational data) and MongoDB Atlas (for image links)
- ORM: Hibernate/JPA
- Build Tool: Maven
- Java Version: Java 21
- Swagger/OpenAPI: For API documentation
- Lombok: For reducing boilerplate code

## Installation
#### Prerequisites
##### Ensure you have the following installed:

- Java 21 or higher
- PostgreSQL database
- MongoDB Atlas account
- Maven 3.8+ (build tool)

- MongoDB Atlas account
- Maven 3.8+ (build tool)
## Steps
### Clone the repository:

- bash
Copy code
git clone https://github.com/your-username/product-management-api.git
cd product-management-api
Configure the application properties:

- Update the application.properties or application.yml file with your database and MongoDB credentials.
properties
Copy code
### PostgreSQL Configuration
- spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
- spring.datasource.username=your_username
- spring.datasource.password=your_password

### MongoDB Configuration
spring.data.mongodb.uri=mongodb+srv://<username>:<password>@cluster0.mongodb.net/myFirstDatabase
Build and run the project:

bash
Copy code
mvn clean install
mvn spring-boot:run
Access the API documentation:

Open http://localhost:8080/swagger-ui.html in your browser to view the API endpoints.


## Endpoints
### Products
#### Method	Endpoint	Description
- GET	/api/products	Retrieve all products
- GET	/api/products/{id}	Retrieve a product by ID
- POST	/api/products	Create a new product
- PUT	/api/products/{id}	Update an existing product
- DELETE	/api/products/{id}	Delete a product
### Categories
#### Method	Endpoint	Description
- GET	/api/categories	Retrieve all categories
- POST	/api/categories	Create a new category
- GET	/api/categories/{id}	Retrieve a category by ID
### Orders
#### Method	Endpoint	Description
- GET	/api/orders/{orderId}	Get order details by order ID
- POST	/api/orders	Place a new order
- GET	/api/orders/{userId}/user	Get all orders for a user


### Images of code and swagger ui
- <img width="956" alt="image" src="https://github.com/user-attachments/assets/a783ac84-695b-4e5a-a1fa-9a067e6e374e">
- <img width="929" alt="image" src="https://github.com/user-attachments/assets/9e9db6bb-b02c-4536-ac12-ec31ac5949df">


