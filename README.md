
# Child Care Management System

This is a Spring Boot application designed to manage child care activities, including meal consumption, attendance, and daily activities. It supports multiple roles such as Parent, Educator, Kitchen Staff, and Admin.

## Features
- **Parent**: View child details, register children, and track daily activities, meals, and attendance.
- **Educator**: Record and manage development records for children.
- **Kitchen Staff**: Manage daily meals and mark meal consumption for children.
- **Admin**: Manage users and system configurations.

---

## Prerequisites
1. **Java**: Ensure you have Java 17 or later installed.
2. **Gradle**: Install Gradle for dependency management.
3. **Database**: Install and configure a PostgreSQL database.
4. **Git**: Ensure Git is installed to clone the repository.

---

## Installation

### 1. Clone the Repository
```bash
git clone https://github.com/AmirBnsL/child-care-management.git
cd child-care-management
```

### 2. Configure the Database
Using Sqlite is preferred for minimal configuration

Update the database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/child_care_db

spring.jpa.hibernate.ddl-auto=update
```

### 3. Build the Project
Run the following command to build the project:
```bash
./gradlew build
```

---

## Running the Application

### 1. Start the Application
Run the following command to start the application:
```bash
./gradlew bootRun
```

### 2. Access the Application
Open your browser and navigate to:
```
http://localhost:8080
```

---

## Default Users
The application initializes with the following default users:

| Role          | Username   | Password   |
|---------------|------------|------------|
| Admin         | admin      | password   |
| Parent        | parent     | password   |
| Educator      | educator   | password   |
| Kitchen Staff | kitchen    | password   |

---

## Development Notes
- **Database Initialization**: The database is automatically initialized with sample data using the `DataInitializer` class.
- **Port Configuration**: The default port is `8080`. You can change it in `application.properties`:
  ```properties
  server.port=8080
  ```

---

## Testing
Run the following command to execute tests:
```bash
./gradlew test
```

---

## Troubleshooting
- **Database Connection Issues**: Ensure PostgreSQL is running and the credentials in `application.properties` are correct.
- **Port Conflicts**: If port `8080` is in use, change the port in `application.properties`.

---

## License
This project is licensed under the MIT License.
```
