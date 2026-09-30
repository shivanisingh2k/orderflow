# OrderFlow — Order Management & Inventory REST API

A backend REST API built with Java and Spring Boot for managing products, users, orders, and inventory.

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven
- Spring Security
- JWT
- BCrypt
- Swagger / OpenAPI
- JUnit 5
- Mockito

## Features

- Product CRUD operations
- User registration and management
- Product validation
- Global exception handling
- Order creation and order items
- Automatic inventory/stock deduction
- Transaction management with `@Transactional`
- Pagination
- BCrypt password hashing
- JWT-based authentication
- Role-based authorization (`USER` / `ADMIN`)
- Swagger API documentation
- Unit testing with JUnit and Mockito

## API Endpoints

### Authentication

| Method | Endpoint | Access |
|---|---|---|
| POST | `/auth/login` | Public |

### Products

| Method | Endpoint | Access |
|---|---|---|
| GET | `/products` | Authenticated |
| GET | `/products/{id}` | Authenticated |
| POST | `/products` | ADMIN |
| PUT | `/products/{id}` | ADMIN |
| DELETE | `/products/{id}` | ADMIN |

### Users

| Method | Endpoint | Access |
|---|---|---|
| POST | `/users` | Public |
| GET | `/users` | Authenticated |
| GET | `/users/{id}` | Authenticated |
| PUT | `/users/{id}` | Authenticated |
| DELETE | `/users/{id}` | Authenticated |

### Orders

| Method | Endpoint | Access |
|---|---|---|
| POST | `/orders` | Authenticated |

## Order Flow

1. User logs in using email and password.
2. Password is verified using BCrypt.
3. Server generates a JWT containing the user's email and role.
4. Client sends the JWT as a Bearer token.
5. JWT filter authenticates the request.
6. Spring Security checks the user's role.
7. Orders calculate the total amount from product prices.
8. Product stock is reduced when an order is created.
9. Transaction management ensures the order operation is atomic.

## Pagination

Products support pagination using Spring Data `Pageable`.

Example:

`GET /products?page=0&size=10`

## API Documentation

Swagger UI:

`http://localhost:8080/swagger-ui.html`

OpenAPI specification:

`http://localhost:8080/v3/api-docs`

## Running the Project

### Requirements

- Java 21
- Maven
- PostgreSQL

### Database

Create a PostgreSQL database named:

`orderflow`

Configure the following environment variables:

```text
DB_PASSWORD=<your-postgresql-password>
JWT_SECRET=<your-jwt-secret>