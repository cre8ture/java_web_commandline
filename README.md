# Command Line Controller Web Application

This project is a full-stack web application that allows users to control their command line from a website. It is built with Spring Boot and Thymeleaf.

## System Description

This application serves as a platform for controlling command line operations remotely via a web interface. It is particularly useful for industrial and commercial-based systems debugging where direct access to the command line might not be feasible or efficient. Users can execute commands and view the results directly in the web interface.

## Tech Stack

- **Spring Boot**: Used for creating the backend of the application. It handles command execution and result retrieval.
- **Thymeleaf**: Server-side Java template engine for web applications. It is used for creating the frontend of the application, providing users with an interface to input commands and view results.
- **Maven**: Used for managing the project's build and dependencies.

## Running the Application with Docker

1. Build the Docker image: `docker build -t my-app .`
2. Run the Docker container: `docker run -p 8080:8080 my-app`

Visit `http://localhost:8080` in your browser to view the application.