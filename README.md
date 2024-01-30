# Cl-Juices

# Microservices Learning Project

This project serves as a hands-on learning repository for implementing a microservices architecture using Spring Boot, Eureka, Spring Cloud Gateway, Resilience4j, OpenFeign, and Flyway. It is designed to demonstrate the synchronous communication between microservices, service registration and discovery, and the implementation of an API gateway in a cloud-native environment.

## Overview

The Microservices Learning Project is structured around a set of independently deployable services, each running its own process and communicating with lightweight mechanisms, often an HTTP resource API. The services are registered with a discovery server (Eureka), and they interact with each other using synchronous REST calls facilitated by OpenFeign clients.

## Features

- **Service Registry and Discovery:** Eureka is used for service registration and discovery, allowing services to find and communicate with each other without hardcoding hostname and port.
- **API Gateway:** Spring Cloud Gateway is used to route requests to the appropriate microservice, acting as an entry point for all client requests.
- **Resilience Patterns:** Resilience4j is integrated to provide resilience patterns like Circuit Breaker, Rate Limiter, Bulkhead, and Retry mechanisms.
- **Database Migration:** Flyway is integrated for version control and migration of the database schema.
- **Synchronous Communication:** Microservices communicate synchronously using RESTful APIs, facilitated by OpenFeign for declarative REST client creation.

## Technologies

- **Spring Boot 2.6.7:** Simplifies the bootstrapping and development of new Spring Applications.
- **Java 17:** The latest long-term support (LTS) version of Java at the time of this project's creation, providing modern features and optimizations.
- **Spring Data JPA:** Provides repository support for JPA to easily interact with database entities.
- **Eureka:** Provides service registry and discovery capabilities to support dynamic scaling and flexibility within the microservices ecosystem.
- **Spring Cloud Gateway:** An API Gateway built on Spring Framework 5, Project Reactor, and Spring Boot 2.0.
- **Resilience4j-Spring-Boot2:** A fault tolerance library designed for Java8 and functional programming.
- **OpenFeign:** A declarative REST client for microservices communication.
- **Flyway:** An open-source database migration tool for version control of your database schema.
