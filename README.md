# FOUNDATION


## Description

A secure and production-ready Spring Boot backend implementing full user CRUD, authentication and authorization with support of role-based access control and multi-session management. <br/>
Derived from **[Wheel Picker](https://github.com/trubetscoin/WheelPicker)**

## Features
- JWT-based authentication using access and refresh token pair. Access token is stored client-side (e.g., session storage), while the refresh token is securely stored in HTTP-only cookies with SameSite=None, supporting cross-origin scenarios.
- Origin-header validation via a custom filter to mitigate CSRF and ensure refresh requests originate from trusted front-ends. Tokens are protected by modern browser SOP from reading via JavaScript.
- Refresh tokens are also stored in the database, enabling revocation and multi-session support.
- Role-based access (user/admin) that can be expanded, including admin-only operations like user search, ban/unban via dedicated controllers.
- Spring Security filter chains to handle public and protected endpoints, applied secure DTO exposure (minimal user info).
- Global unified success and error responses, simplifying front-end response handling.
- Global exception handling with a custom error-handling filter that intercepts all requests in combination with @ControllerAdvice and a fallback /error endpoint.
- Scheduled cron tasks for periodic cleanup of expired refresh tokens from the database.
- Liquibase for database versioning and PostgreSQL as the primary relational store.
- Comprehensive test coverage including unit, integration, and end-to-end tests using Testcontainers for isolated, Dockerized test environments. Along with them are used: Mockito, TestRestTemplate, WebClient. Test coverage tool: JaCoCo.
- Three Spring profiles (test, dev, prod) with .env support for environment-specific behavior.
- Full Docker support for production, including automatic build, database provisioning and isolated service deployment via .env.prod.

## Quick Start
Create the database which you plan connect to. <br/>
Change application files in `src\main\resources` to your configuration. <br/> <br/>

Run `FoundationApplication` with env variable `SPRING_PROFILES_ACTIVE=dev` <br/>
Maven `mvn spring-boot:run -D"spring-boot.run.profiles"=dev`

### Testing
All in directory: `src\test\java\com\foundation` with env variable `SPRING_PROFILES_ACTIVE=test` <br/>
Maven `mvn clean test -D"spring-boot.run.profiles"=test`. It sets spring profile to test and run all tests.

## Docker support
You have to set environment variables for the database container creation. <br/>

Examples: <br/>
Windows
```
set DB_USERNAME=postgres
set DB_PASSWORD=reallySecuredPasswordForSuperUser
set DB_NAME=foundation
```
Linux
```
export DB_USERNAME=postgres
export DB_PASSWORD=reallySecuredPasswordForSuperUser
export DB_NAME=foundation
```

To start the project with docker simply use: `docker compose up` or `docker compose up -d` to run it as daemon <br/>

## Important Notes
Change application-dev-secrets.yml and application-prod-secrets.yml files data ASAP and add them to .gitignore.  <br/>
The environment variables for the docker compose database must be the same as in application-prod-secrets.yml to work. Otherwise, spring backend would not be able to connect to the postgres database.

## Dependencies
Docker Engine >= 20.10

## Dependencies for local development
Java >= 17.0 \
PostgreSQL >= 17.5 \
