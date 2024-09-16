# Planner API

### Description

This API allows the user to sign up, sign in and send basic CRUD requests on Activity objects. The data is hosted on AWS RDS

### Usage
To get the project up and running, create first a file at root level called ``nv.properties`` with these values:

```properties
# Database Configuration
database.url=dburlexample
database.username=dbusername
database.password=dbpassword
# JWT Configuration
jwt.secret.key=jwtsecretkey
```

Once the file has been created with private values that will be provided, the project should successfully run locally.

The project is also linked to OpenAPI so to make requests you can navigate to [Swagger localhost](http://localhost:8080/swagger-ui/index.html) once the project is running.

### Authentication and Authorization
The API offers a basic sign up endpoint with a form that allows the use to create an account (with basic check on username) and login endpoint that generates a JWT token.

Since all the other routes are protected, to be able to make request, the login token should be stored in the authorized section in the top right. This grants that all the requests will contains the authorization header.

### TODOs
The API lacks in some aspects like Registration and Category management
