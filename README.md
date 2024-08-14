# Backend of BloodDonation App

The project built with Spring Boot. It connects to a PostgreSQL database. 

## Prerequisites

Ensure you have the following prerequisites installed on your system:

- [Docker](https://www.docker.com/get-started) 
- Integrated Development Environment (IDE), such as [IntelliJ](https://www.jetbrains.com/idea/download/?source=google&medium=cpc&campaign=EMEA_en_TR_IDEA_Branded&term=intellij&content=619479151433&gclid=CjwKCAiApuCrBhAuEiwA8VJ6JlQbcnH8jIklp-ZEi2X74TRKNA-Jz5cWjWwumwTgZQaHw7auozMrEhoCINEQAvD_BwE&section=windows) or [VSCode](https://visualstudio.microsoft.com/downloads/).
- Java Development Kit (JDK) 

## Running the Application

### 1. Start PostgreSQL Docker Container

- Run the following command to start a PostgreSQL Docker container:

```
docker run --name postgres-docker -e POSTGRES_PASSWORD=password -e POSTGRES_DB=kanver -p 5432:5432 -d postgres
```

### 2. Build and run the application 
Now that the PostgreSQL container is running, you can build and start the Kanver Spring Boot application. Follow these steps:

- You can run it simply by pressing run symobol in the main class "KanverApplication"

OR

- Running from the terminal
1. Open the terminal, then navigate to the root directory of the Kanver project.
2. Run the following Gradle command to build and run the application:

```
./gradlew bootRun
```

### 3. Access the Application
Open your web browser and navigate to http://localhost:8080 to access the Kanver application.

## After Running
After successfully launching Kanver, and the server is up and running, you can explore one of the following:

### 1. Postman Collection
Import the provided Postman Collection into your Postman workspace. The collection includes sample requests for creating, retrieving, updating, and deleting users and other models.

### 2. Swagger for API Documentation

Use swagger to explore the available endpoints and try them out. Find it at:
http://localhost:8080/swagger-ui/index.html

### 3. Testing

Run the following command to test:

```
./gradlew test --tests itu.blg411.kanver.*.*
```

Code Coverage (JaCoCo) reports can be found in:

```
build/reports/jacoco/test/html
```
## After Deployment

- You can access the backend side of kanver app by this link:
http://20.81.37.184

OR

- Via Swagger : http://20.81.37.184/swagger-ui/index.html
