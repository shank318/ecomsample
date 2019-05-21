## Turing

Rest api's for building an ecommerce website. 

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `co.turing.TuringAp` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## API Docs

Swagger --> https://turingbackend.herokuapp.com/swagger-ui.html#/

The app is deployed on Heroku which is considerably slow most of the time. Please run the code locally to check the api's.

## Architecture

This is a monolith backed service architecture which are divided into modules for extensibility. I have used in-memory chaching in a lot of api's to reduce the DB load. 

## Advanced requirements

1. To support 1000000 users a day which is approx 11~ TPS we need to focus on three things. 

# Web server

11 TPS can be handled by a single server tuned to support the use case. Number of threads, connections, network buffer size, open file descriptor etc. If needed we might need to add more instances of the server and load balance it.




