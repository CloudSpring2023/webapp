# Mysql CURD API using Spring Boot, MySQL.



## Requirements

### Install Mysql

```bash
brew install mysql

brew services start mysql
```

###Create project from https://start.spring.io

Add dependency :

Spring Boot DevTools DEVELOPER TOOLS,
Spring Data JPA SQL,
Spring Web WEB,
MySQL Driver SQL,
jakarta validation

## Steps to Setup

**1. Clone the application**

```bash

git clone 

```

**2. Create Mysql database**

```bash

create database yourdatabaseName

```

**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application.properties`


+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Build and run the app using maven**

 you can run the app without packaging it using -

```bash

mvn spring-boot:run

```

The app will start running at <http://localhost:9191>.

## Rest APIs

GET API :

```bash

/users : Fetch all the users present in database

curl --location --request GET 'http://localhost:9191/users' \
--header 'Content-Type: application/json' \
--data-raw ''
```

Post API 



   ```bash
   
   /addMember : Create an user and stored in mysql
   

curl --location --request POST 'http://localhost:8080/addMember' \
--header 'Content-Type: application/json' \
--data-raw '{
  "emailId": "mahek@gmail.com"
  "password": "Mahek123",
  "first_name": "Mahek",
  "last_name": "Patel",
}

#for testing the workflow

this is for test
test5
