# FinanceApp

## Overview

FinanceApp is a robust financial management application designed to help individuals and businesses manage their finances effectively. With features like expense tracking, budget planning, and financial reporting, it aims to provide a comprehensive solution for all financial management needs.

## Features

- Expense tracking
- Budget planning
- Financial reporting
- Multi-currency support
- User-friendly interface

## Installation

To run FinanceApp locally, follow these steps:

1. **Clone the repository:**
    ```bash
    git clone https://github.com/yourusername/FinanceApp.git
    cd FinanceApp
    ```

2. **Setup the environment:**

   Ensure you have Java SDK version 21 installed. You can download it from [here](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html).

3. **Install dependencies:**
    ```bash
    ./mvnw clean install
    ```

4. **Run the application:**
    ```bash
    ./mvnw spring-boot:run
    ```

## Usage

Once the application is running, navigate to `http://localhost:8080` to access the app.

### API Endpoints

- **Get All Expenses:** `GET /api/expenses`
- **Add Expense:** `POST /api/expenses`
- **Update Expense:** `PUT /api/expenses/{id}`
- **Delete Expense:** `DELETE /api/expenses/{id}`

### Example Requests

- **To get all expenses:**
    ```bash
    curl -X GET http://localhost:8080/api/expenses
    ```

- **To add an expense:**
    ```bash
    curl -X POST http://localhost:8080/api/expenses -H "Content-Type: application/json" -d '{"amount": 100, "description": "Dinner", "category": "Food"}'
    ```

## Dependencies

- Java SDK 17
- Jakarta EE
- Spring Boot
- Spring Data JPA
- Spring MVC
- Lombok

---

Happy Coding!