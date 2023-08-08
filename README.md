## Flea Market

![Авторизация](src/main/screenshots/login.png)

Flea Market is a simple **Spring Boot** application. 
The developed application is a platform for selling goods.
This project was created **for educational purposes**.

Application runs from localhost:8080/
## Technology stack
Below is a set of technologies that were used in the development of the application:
* Spring: Boot, Security, Data
* MySQL
* FreeMarker 
* JavaScript
* Bootstrap
* Maven

## Functionality
The functionality of the application is described below:
* User registration
* User authorization 
* Add a new product 
* Get additional product information
* Delete a product
* Viewing the list of registered users (as the administrator)
* Block/unblock a user (as the administrator)
* Find the product by name
* Change the user role to administrator and vice versa.

## Database settings
MySQL is used as the database.
If you want to run the project locally, follow these steps:
1. Open src/main/resources/application.properties
2. Change ```spring.datasource.username``` and ```spring.datasource.password``` 
according to your MySQL installation.
3. If necessary, change the ```spring.datasource.url```.

The ER (Entity Relationship) diagram is shown below:

![ER-модель](src/main/screenshots/database.png)

There are users of type user and admin.
## Demonstration of the application
TODO