## Turing

Rest api's for building an e-commerce website. 

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `co.turing.TuringAp` class from your IDE.

Alternatively, you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## API Docs

Swagger --> https://turingbackend.herokuapp.com/swagger-ui.html#/

The app is deployed on Heroku which is considerably slow most of the time. Please run the code locally to check the api's. Also, the mysql database I am using is a free tier Heroku database which allows only 10 connections at a time. 

## Architecture

This is a monolith backed service architecture which is divided into modules for extensibility. JPA is getting used as an ORM, MySQL for database. I have used in-memory caching in a lot of api's to reduce the DB load. 

![IMG_3942](https://user-images.githubusercontent.com/5608772/58866288-e898e100-86d5-11e9-93f9-11826c5c3a37.jpg)

## Advanced requirements

To support 1000000 users a day which is approx **11~ TPS** we need to focus on three things. 

### Web server

11 TPS can be handled by a single server tuned to support the use case. Number of **threads, connections, network buffer size, open file descriptor** etc. If needed we might need to add more instances of the server and load balance it.

Currently, we are searching the products from the products/attributes/categories database which will not scale because search is the heavy traffic API which needs to be scaled separately. I'd use a searching engine like elasticsearch/solr to handle the search. Whenever a product is added into the database we have to send the product/attributes/categories metadata to our search engine to index. 

**Kafka** can be used to send the events to our search engine whenever there is a change in the price, attributes etc in a product.

### Caching

We'd need a caching(**Redis**) layer on top of API's like catalog, cart, attributes, reviews etc. These are the data which doesn't change frequently thus we can cache it and update the cache whenever there is any change. 

### Microservices 

Even though we might not need to split the entire architecture to support 11TPS but some of the components can be broken into separate microservices. 

- User management 
- Product management
- Order management 
- Search management 
- Payment management


### Database 

- User management - SQL
- Product management - NoSQL
- Order management - SQL/State managment/Workflows
- Search management - Elastic search/Solr
- Payment management - SQL/State managment/Workflows

For SQL database we'd need master/slave architecture to send write queries to master and read from slaves. NOSQL databases are distributed/ multi master architecture comes by default. We'll try to use the NOSQL for non-transactional services.

If we see a lot of traffic is coming from a particular area/country. Say, half of the daily active users coming from the United States then we can do these changes. 

**Sharding the database** - We can **shard** the database by country id and also set up a cluster of web server which can be scaled separately for a specific country. 









