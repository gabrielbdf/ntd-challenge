# Calculation API

## Overview

The Calculation API provides endpoints for performing various mathematical operations and managing user authentication. The API supports different strategies for performing operations and includes user authentication mechanisms using JWT tokens.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
  - [Operations](#operations)
    - [Operate by Groovy Strategy](#operate-by-groovy-strategy)
    - [Operate by Enum Strategy](#operate-by-enum-strategy)
  - [Authentication](#authentication)
    - [Register](#register)
    - [Login](#login)
- [Schemas](#schemas)
- [Security](#security)
- [Contributing](#contributing)
- [License](#license)

## Installation

To run this project locally, clone the repository and install the dependencies.

```bash
git clone https://github.com/your-repository/calculation-api.git
cd calculation-api
npm install
```

Run the application:

```bash
npm start
```

The API will be available at `http://localhost:8080`.
The Swagger API will be available at `http://localhost:8080/swagger-ui/index.html`

## Usage

The API can be used to perform various operations and manage user authentication. To use the operations endpoints, you must be authenticated. Ensure that you include the JWT token in the `Authorization` header when making requests.

## API Endpoints

### Operations

#### Operate by Groovy Strategy

- **Endpoint**: `/operation/groovy`
- **Method**: `POST`
- **Summary**: Perform a mathematical operation using the Groovy strategy.
- **Request Body**:
  - `operation`: A string representing the operation (e.g., `ADD`, `SUBTRACT`).
  - `args`: An array of integers for the operation.
- **Response**:
  - `200 OK`: The result of the operation.

#### Operate by Enum Strategy

- **Endpoint**: `/operation/enum`
- **Method**: `POST`
- **Summary**: Perform a mathematical operation using the Enum strategy.
- **Request Body**:
  - `operation`: A string representing the operation (e.g., `ADD`, `SUBTRACT`).
  - `args`: An array of integers for the operation.
- **Response**:
  - `200 OK`: The result of the operation.

### Authentication

#### Register

- **Endpoint**: `/auth/register`
- **Method**: `POST`
- **Summary**: Register a new user.
- **Request Body**:
  - `username`: A string representing the username.
  - `password`: A string representing the password.
  - `role`: A string representing the user role.
- **Response**:
  - `200 OK`: User successfully registered.

#### Login

- **Endpoint**: `/auth/login`
- **Method**: `POST`
- **Summary**: Authenticate a user and obtain a JWT token.
- **Request Body**:
  - `username`: A string representing the username.
  - `password`: A string representing the password.
- **Response**:
  - `200 OK`: JWT token provided for authenticated user.

## Schemas

### OperationRequest

- **operation**: `string` - The operation to be performed. Possible values: `ADD`, `SUBTRACT`, `MULTIPLY`, `DIVIDE`, `SQRT`, `RAMDOM_STRING`.
- **args**: `array` of `integer` - The arguments for the operation.

### OperationResponse

- **operation**: `string` - The operation performed.
- **result**: `string` - The result of the operation.

### RegisterDto

- **username**: `string` - The username of the user.
- **password**: `string` - The password of the user.
- **role**: `string` - The role of the user.

### AuthDto

- **username**: `string` - The username of the user.
- **password**: `string` - The password of the user.

### LoginResponseDto

- **token**: `string` - The JWT token provided to the authenticated user.

## Security

This API uses JWT tokens for authentication. Include the token in the `Authorization` header in the format `Bearer <token>` when making requests to secure endpoints.
