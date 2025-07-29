# Order Orchestration Module

This module is responsible for orchestrating the order processing workflow across the different services in the SagaOrchestration project.

## Overview

The Order Orchestration module coordinates the interactions between the Order Service, Inventory Service, and Payment Service to ensure a smooth order processing experience.

## Setup Instructions

1. Ensure you have Java 11 or higher installed.
2. Clone the repository:
   ```
   git clone <repository-url>
   ```
3. Navigate to the OrderOrchestration directory:
   ```
   cd OrderOrchestration
   ```
4. Build the project using Gradle:
   ```
   ./gradlew build
   ```
5. Run the application:
   ```
   ./gradlew bootRun
   ```

## Usage

Once the application is running, it will listen for incoming requests to manage orders. You can interact with the Order Orchestration module through its REST API endpoints.

## Dependencies

This module may depend on the following services:
- Order Service
- Inventory Service
- Payment Service

Ensure that these services are running before testing the orchestration functionality.