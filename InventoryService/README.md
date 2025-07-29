# Inventory Service

The Inventory Service is a module of the SagaOrchestration project responsible for managing inventory-related operations. This service handles inventory checks, updates, and notifications.

## Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd SagaOrchestration/InventoryService
   ```

2. **Build the Project**
   Ensure you have Gradle installed, then run:
   ```bash
   ./gradlew build
   ```

3. **Run the Service**
   You can run the Inventory Service using:
   ```bash
   ./gradlew bootRun
   ```

## Usage

The Inventory Service exposes RESTful APIs to interact with inventory data. You can use tools like Postman or curl to test the endpoints.

## Dependencies

This module may depend on various libraries for Spring Boot, database access, and other functionalities. Check the `build.gradle` file for the complete list of dependencies.

## Notes

- Ensure that the necessary configurations are set in `application.properties` or `application.yml`.
- This service is designed to work in conjunction with other services in the SagaOrchestration project.