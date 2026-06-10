# Scalable API Rate Limiter Platform

A robust, microservices-based API Rate Limiter platform built using Spring Boot. This platform allows developers to register, define rate limiting configurations for their APIs using various algorithms, and view analytics of their API usage. It's designed to be highly scalable, using Redis for fast rate limit evaluation and Kafka for asynchronous analytics processing.

## 🌟 Key Features

* **Developer Authentication:** Secure registration and login using JWT.
* **Multiple Rate Limiting Algorithms:** Supports:
  * Fixed Window
  * Sliding Window
  * Leaky Bucket
  * Token Bucket
* **Dynamic API Configuration:** Developers can configure rate limits per API endpoint dynamically.
* **Real-time Analytics:** Asynchronously processes request logs via Kafka and stores them in PostgreSQL for analytics.
* **Microservices Architecture:** Built with Spring Cloud, including an API Gateway and Eureka Service Registry.

## 🏗️ Architecture

The platform is divided into several microservices:

1. **API Gateway (`api-gateway`):** The single entry point for all requests. Handles JWT validation and routes requests to the appropriate backend services.
2. **Auth Service (`auth-service`):** Manages developer registration and authentication.
3. **API Config Service (`api-config-service`):** Allows developers to create, update, and manage rate limit rules for their APIs.
4. **Rate Limiter Service (`rate-limiter-service`):** The core service that evaluates incoming requests against configured limits using Redis. It supports multiple algorithms and publishes request events to Kafka.
5. **Analytics Service (`analytics-service`):** Consumes request events from Kafka and stores them in PostgreSQL for reporting and analytics.
6. **Eureka Server (`eureka-server`):** Service registry for discovering all microservices.

## 🛠️ Technology Stack

* **Java 17** & **Spring Boot 3.5.x**
* **Spring Cloud:** Gateway, Netflix Eureka
* **Database:** PostgreSQL (Supabase)
* **Caching & Rate Limiting:** Redis
* **Message Broker:** Apache Kafka & Zookeeper
* **Containerization:** Docker Compose for infrastructure

## 🚀 Getting Started

### Prerequisites

* Java 17
* Maven
* Docker & Docker Compose

### 1. Start Infrastructure (Redis, Kafka, Zookeeper)

Run the provided `docker-compose.yml` to start the required infrastructure components:

```bash
docker-compose up -d
```

This will start Redis on port `6379`, Kafka on `9092`, and Kafka UI on `8090`.

### 2. Configure Database

By default, the services are configured to use a Supabase PostgreSQL instance. You can update the `application.properties` in `auth-service`, `api-config-service`, `rate-limiter-service`, and `analytics-service` to point to your local or remote database.

### 3. Start the Microservices

You need to start the services in the following order:

1. **Eureka Server** (`eureka-server`) - Runs on port 8761
2. **Auth Service** (`auth-service`) - Runs on port 8081
3. **API Config Service** (`api-config-service`) - Runs on port 8082
4. **Rate Limiter Service** (`rate-limiter-service`) - Runs on port 8083
5. **Analytics Service** (`analytics-service`) - Runs on port 8084
6. **API Gateway** (`api-gateway`) - Runs on port 8080

You can start each service using Maven:

```bash
cd <service-folder>
./mvnw spring-boot:run
```

## 📚 API Flow

1. **Register/Login:** Obtain a JWT token from the Auth Service.
2. **Configure API:** Use the API Config Service to create a rate-limit configuration (e.g., 100 requests / minute using Token Bucket).
3. **Send Request:** Pass the request through the API Gateway, which validates the JWT.
4. **Evaluate Limit:** The Rate Limiter Service checks Redis. If allowed, it forwards the request; if blocked, it returns a `429 Too Many Requests`.
5. **Analytics:** Every request's outcome (Allowed/Blocked) is sent to Kafka and processed by the Analytics Service.

## 📜 License

This project is licensed under the MIT License.
