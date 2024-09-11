![Banque Misr Logo](https://github.com/user-attachments/assets/37e80cf8-9f4d-423d-9079-6069d3d132b6)


# User Account Management API

This project provides a backend API for user account management, session tracking, and financial transactions. It allows users to create accounts, authenticate, manage their balances, and perform money transfers.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [API Endpoints](#api-endpoints)
- [Installation](#installation)
- [Usage](#usage)
- [Testing](#testing)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [License](#license)
- [Link Swagger For Documentation](#Link)
- [Link render for Deploy](#Deploy)

## Features

- **User Account Management**: Create, log in, and log out user accounts.
- **Session Management**: Track user sessions and automatically invalidate after inactivity.
- **Account Balance**: Securely fetch the current account balance.
- **Transaction History**: Retrieve transaction history with pagination and filtering.
- **Money Transfer**: Initiate transfers to other users with validation.
- **Favorite Recipients**: (Optional) Save and manage favorite recipients for quick access.

## Technologies Used

- **Java**: Programming language used for the backend.
- **Spring Boot**: Framework for building the API.
- **PostgreSQL**: Database for storing user and transaction data.
- **Docker**: Containerization for easy deployment and environment consistency.

## API Endpoints

### User Account Management

- **POST** `/api/register`: Create a new user account.
- **POST** `/api/login`: Authenticate a user and return an auth token.
- **POST** `/api/logout`: Invalidate the user's auth token.

### Session Management

- Middleware to track user activity and manage session timeouts.

### Account Balance

- **GET** `/api/balance`: Get the current account balance for the authenticated user.

### Transaction History

- **GET** `/api/transactions`: Get the transaction history for the authenticated user.

### Money Transfer

- **POST** `/api/transfer`: Transfer money to another user.

### Favorite Recipients (Optional)

- **POST** `/api/favorites`: Add a favorite recipient.
- **GET** `/api/favorites`: Get the list of favorite recipients.
- **DELETE** `/api/favorites/{id}`: Remove a favorite recipient.
  
## Link Swagger For Documentation
-http://localhost:8081/swagger-ui/index.html#

## Link render for Deploy
-https://banquemisr-transfer-service.onrender.com/


##
