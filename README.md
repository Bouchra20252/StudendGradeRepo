# Student Grade Management System

> A Java desktop application with a JavaFX graphical interface for managing and viewing student grades.

## Overview

The Student Grade Management System is a desktop application developed using Java and JavaFX to facilitate the management and consultation of academic grades.

The application provides dedicated functionalities for two types of users. Teachers can manage student grades by adding and modifying academic results, while students can access the application to consult their own grades.

The project combines a graphical user interface with structured application logic to provide a simple and organized solution for academic grade management.

## Features

### Teacher

- Add student grades
- Modify existing grades
- Manage student academic results
- Access and manage grade information
- Use a dedicated teacher interface

### Student

- Access a dedicated student interface
- View personal academic results
- Consult grades through the application
- Access academic information in an organized way

### Application

- Role-based access
- JavaFX graphical user interface
- Structured navigation between application views
- Grade management and consultation
- Organized project architecture
- User-oriented desktop experience

## Application Architecture

The application separates the graphical interface from the underlying application logic and grade management functionalities.

    +---------------------------+
    |      JavaFX Interface     |
    |                           |
    |   Teacher    |   Student  |
    +------+------+------+------+
           |             |
           v             v
    +---------------------------+
    |     Application Logic     |
    |                           |
    |   Grade Management        |
    |   Grade Consultation      |
    +-------------+-------------+
                  |
                  v
    +---------------------------+
    |     Student Grade Data    |
    +---------------------------+

This organization allows different functionalities to be provided according to the user's role while keeping the application components structured.

## Technologies

| Technology | Purpose |
|---|---|
| Java | Core application development |
| JavaFX | Graphical user interface |
| FXML | Interface design and view structure |
| Maven | Project and dependency management |

## User Roles

### Teacher

The teacher interface provides functionalities for managing academic results, including adding and modifying student grades.

### Student

The student interface allows students to access and consult their academic results.

## Project Structure

    StudendGradeRepo/
    |
    ├── .mvn/
    │   └── wrapper/
    |
    ├── src/
    │   └── main/
    │       └── ...
    |
    ├── .gitignore
    ├── mvnw
    ├── mvnw.cmd
    ├── pom.xml
    └── README.md

## Getting Started

### Prerequisites

Make sure the following tools are installed:

- Java Development Kit (JDK)
- Maven
- JavaFX-compatible development environment

### Clone the Repository

    git clone https://github.com/Bouchra20252/StudendGradeRepo.git
    cd StudendGradeRepo

### Build the Project

Using the Maven Wrapper on Linux or macOS:

    ./mvnw clean install

On Windows:

    mvnw.cmd clean install

### Run the Application

Run the application using the configured JavaFX and Maven environment.

## Project Objectives

The main objectives of the project were to:

- Develop a functional Java desktop application
- Build a graphical user interface using JavaFX
- Implement different user roles and access levels
- Provide functionality for managing student grades
- Allow students to consult their academic results
- Apply object-oriented programming principles
- Practice desktop application development
- Use Maven for project and dependency management

## Future Improvements

Possible future extensions include:

- Database integration for persistent grade storage
- User authentication and account management
- Student and teacher profile management
- Automatic calculation of averages and academic results
- Academic performance statistics and visualizations
- Grade and report export functionality
- Improved input validation and error handling
- Enhanced graphical interface and user experience

## Screenshots

Screenshots of the application's main interfaces can be added here to showcase the teacher and student interfaces and the overall user experience.

## Author

**Simali Bouchra**

AI & Data Engineering Student

Interested in Artificial Intelligence, Machine Learning, Data Engineering, Data Science, and Software Development.
