**NAME:** Tomé de Jesus

# Starling Round-Up Application

The Starling Round-Up Application is a command-line application that helps Starling Bank customers to save money by "rounding up" their transactions for a chosen week to the nearest pound and transferring the difference to a savings goal. 

The application is built using Java and uses the Starling public developer API.

## Prerequisites

You need to have Java 11+ and Gradle installed on your machine to build and run the application.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

1. Clone the repository:
```sh
git clone https://github.com/tomejesus/starling-round-up.git
```

2. Go to the project directory:
```sh
cd starling-round-up
```

## Build the Application

To build the application, you can use the gradle wrapper included in the project:
```sh
./gradlew clean build
```

This will compile the source code, run the tests and create a runnable jar file in `build/libs` directory.

## Run the Application

After the application is built, you can run the application using the following command:
```sh
./gradlew run --args='{week start date in YYYY-MM-DD format} {Access Token}'
```

## Testing

To run the tests for the application:
```sh
./gradlew test
```

## Features

The application provides the following features:

1. Fetches the accounts of a customer.
2. Retrieves the transactions for a customer for a given week.
3. Calculates the "round-up" amount of the transactions (i.e., rounds up each transaction to the nearest pound and sums up the difference).
4. Creates a savings goal for the customer if it does not exist.
5. Transfers the "round-up" amount to the savings goal.

## Technology Stack

This application is built using:

1. **Java:** The programming language used to build the application.

2. **Gradle:** The build tool used for managing dependencies and automating tasks like building and testing the application.

3. **JUnit 5 and Mockito:** These are used for unit testing and mocking dependencies in tests, respectively.

4. **JSONPath:** This is used for parsing JSON data.

5. **Logback:** This is used for application logging.

6. **Jackson:** This is used for mapping between Java objects and JSON.

7. **Starling Public Developer API:** This API is used to fetch customer accounts and transactions, as well as to create savings goals and transfer money into them.
