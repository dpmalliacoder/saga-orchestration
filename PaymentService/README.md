# Payment Service

The Payment Service is a module of the SagaOrchestration project responsible for handling payment processing. This service interacts with other services to ensure that payments are processed correctly and efficiently.

## Setup Instructions

1. **Clone the Repository**: 
   Clone the SagaOrchestration repository to your local machine.

   ```bash
   git clone <repository-url>
   ```

2. **Navigate to the PaymentService Directory**:
   Change your directory to the PaymentService module.

   ```bash
   cd PaymentService
   ```

3. **Build the Project**:
   Use Gradle to build the project and resolve dependencies.

   ```bash
   ./gradlew build
   ```

4. **Run the Payment Service**:
   You can run the Payment Service using the following command:

   ```bash
   ./gradlew bootRun
   ```

## Usage

The Payment Service exposes RESTful APIs for processing payments. You can interact with these APIs using tools like Postman or curl.

### API Endpoints

- **Process Payment**: 
  - **POST** `/api/payments`
  - Description: Initiates a payment process.

## Dependencies

This module may depend on various libraries for Spring Boot, database access, and other functionalities. Please refer to the `build.gradle` file for a complete list of dependencies.

## Notes

Ensure that the necessary configurations are set up in your application properties for connecting to external payment gateways and databases.