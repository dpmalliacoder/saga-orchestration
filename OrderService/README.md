# Order Service Module

This module is responsible for handling order-related operations in the Saga Orchestration project.

## Setup Instructions

1. **Clone the Repository**: 
   Clone the repository to your local machine using:
   ```
   git clone <repository-url>
   ```

2. **Navigate to the OrderService Directory**:
   ```
   cd SagaOrchestration/OrderService
   ```

3. **Build the Project**:
   Use Gradle to build the project:
   ```
   ./gradlew build
   ```

4. **Run the Application**:
   To run the Order Service application, execute:
   ```
   ./gradlew bootRun
   ```

## Usage

The Order Service provides endpoints to create, update, and retrieve orders. You can interact with the service using tools like Postman or curl.

### API Endpoints

- **Create Order**: `POST /orders`
- **Get Order**: `GET /orders/{id}`
- **Update Order**: `PUT /orders/{id}`

## Dependencies

This module may depend on various libraries for Spring Boot, database access, and other functionalities. Please refer to the `build.gradle` file for a complete list of dependencies.

## Notes

Ensure that the necessary configurations are set up in your application properties for database connections and other service integrations.