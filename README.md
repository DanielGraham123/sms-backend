# SCHOOL MANAGEMENT SYSTEM BACKEND
This is the backend api for the school management system.

Follow the instructions below to get started.

## Getting Started

### Requirements
For building and running the application you need:

* JDK 19.0.1
* Maven 4.0.0

### Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in the com.sms.api.SmsBackendApplication class from your IDE.

Alternatively you can use the Spring Boot Maven plugin like so:

```
mvn spring-boot:run
```

### Environment Variables
```
db_url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
db_username=DB_USERNAME
db_password=DB_PASSWORD
admin_pass=ADMIN_PASSWORD
```

### Deploying to Railway
```
db_url=jdbc:postgresql://containers-us-west-37.railway.app:7465/railway
db_username=postgres
db_password=Ghuu34dt7UYCE5FjXfP9
admin_pass=admin
```

## Reference Documentation

API documentation coming soon...

## Copyright

All rights reserved. This repository contains confidential and proprietary software and data, and the contents are intended for authorized users only.