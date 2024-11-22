# FinanceApp

FinanceApp is a web application designed to manage financial accounts and transactions. It provides functionalities to
manage user accounts, bank details, and individual account information.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Contributing](#contributing)

## Features

- Manage user accounts
- Manage bank details
- Manage individual account information

## Technologies Used

- **Java 17**: Core language used
- **Spring Boot**: To create stand-alone, production-grade Spring-based applications
- **Spring Data JPA**: For database interactions
- **Spring MVC**: For creating REST APIs
- **Lombok**: To reduce boilerplate code
- **Jakarta EE**: Various enterprise features and specifications

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher

### Setup

1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/FinanceApp.git
   ```
2. Navigate to the project directory:
   ```sh
   cd FinanceApp
   ```
3. Build the project using Maven:
   ```sh
   mvn clean install
   ```
4. Run the application:
   ```sh
   mvn spring-boot:run
   ```


## Project Structure

The project follows the standard Maven project structure:

- **src/main/java**: Contains the main source code
    - **com.finance.app.financeapp.controller**: Controllers for handling web requests
        - `AccountController.java`: Manages account-related operations
        - `BankController.java`: Manages bank-related operations
        - `UserController.java`: Manages user-related operations
    - **com.finance.app.financeapp.dto**: Data Transfer Objects
        - `Account.java`: DTO for account data
    - **com.finance.app.financeapp.repository**: JPA repositories
        - `AccountRepository.java`: Repository for account entities
    - **com.finance.app.financeapp.service**: Service interfaces
        - `AccountService.java`
        - `BankService.java`
        - `UserService.java`
    - **com.finance.app.financeapp.service.impl**: Service implementations
        - `AccountServiceImpl.java`
        - `BankServiceImpl.java`
        - `UserServiceImpl.java`

## Contributing

Contributions are welcome! Please follow these guidelines:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature-name`).
3. Make your changes.
4. Commit your changes (`git commit -m 'Add some feature'`).
5. Push to the branch (`git push origin feature/your-feature-name`).
6. Open a pull request.
