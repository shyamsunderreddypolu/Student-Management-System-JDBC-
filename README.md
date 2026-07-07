# Student Management System (JDBC + MySQL)

A robust, console-based **Student Management System** built with **Java**, **JDBC (Java Database Connectivity)**, and **MySQL** database. It follows a clean **DAO (Data Access Object) Design Pattern** and standard Maven directory structure.

---

## 🚀 Features (CRUD Operations)

* **Add Student**: Insert new student records into the database (Name, Email, Age, Course). The database automatically handles generating IDs.
* **View All Students**: Retrieve and neatly display all student records currently stored in MySQL.
* **Search Student by ID**: Quickly lookup details of a specific student using their unique ID.
* **Update Student Details**: Modify existing student profiles. Pressing Enter during input preserves the old values, ensuring a smooth UX.
* **Delete Student**: Delete a student record with a safe console confirmation step (`y/n`) to prevent accidental data loss.

---

## 🛠️ Technology Stack

* **Language**: Java 11 (or higher)
* **Build Tool**: Maven (handles dependencies and compilation)
* **Database**: MySQL Server
* **Driver**: MySQL Connector/J JDBC Driver (version `8.0.33`)

---

## 📂 Project Structure

```text
student-jdbc/
├── pom.xml                        # Maven dependencies & compile configurations
├── README.md                      # Project documentation
├── .gitignore                     # Excludes target/ and IDE configuration folders
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── student/
        │           ├── model/
        │           │   └── Student.java         # Entity Model representing Student table rows
        │           ├── dao/
        │           │   ├── StudentDAO.java      # Interface defining CRUD database signatures
        │           │   └── StudentDAOImpl.java  # JDBC Concrete implementations of the CRUD operations
        │           ├── util/
        │           │   └── DBConnection.java    # Connection manager utility loading the MySQL driver
        │           └── Main.java                # Menu-driven Console UI Controller
        └── resources/
            └── schema.sql         # SQL script to initialize DB and students table
```

---

## 💾 Database Schema Setup

Before running the application, make sure MySQL is running and set up the schema. You can run the commands below in your MySQL Command Line Client or GUI tool (like MySQL Workbench, DBeaver, etc.):

```sql
-- Create the database if it doesn't already exist
CREATE DATABASE IF NOT EXISTS student_db;

-- Switch to using the created database
USE student_db;

-- Create the students table to store student records
CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY, -- Unique ID auto-incremented by MySQL
    name VARCHAR(100) NOT NULL,        -- Student name
    email VARCHAR(100) UNIQUE NOT NULL, -- Student email (must be unique)
    age INT NOT NULL,                  -- Student age
    course VARCHAR(100) NOT NULL       -- Course enrolled by student
);
```

---

## ⚙️ How to Setup and Run

### 1. Prerequisites
* **Java Development Kit (JDK)**: JDK 11 or higher installed. Check with `java -version`.
* **Maven**: Installed and configured. Check with `mvn -version`.
* **MySQL Server**: Running locally on your machine.

### 2. Configure Database Credentials
Open the database connection utility at:
`src/main/java/com/student/util/DBConnection.java`

Update the `USER` and `PASSWORD` constants to match your MySQL database root credentials:
```java
private static final String USER = "your_mysql_username";     // e.g. "root"
private static final String PASSWORD = "your_mysql_password"; // e.g. "root" or your custom password
```

### 3. Build the Project
Open your command terminal, navigate to the root directory containing `pom.xml`, and compile the application to verify everything downloads and compiles correctly:
```bash
mvn clean compile
```

### 4. Run the Application
Execute the following command to boot the menu-driven console app:
```bash
mvn exec:java -Dexec.mainClass="com.student.Main"
```

---

## 💡 Concepts Explined in the Code

* **Data Access Object (DAO) Pattern**: Decouples the storage access mechanism from the presentation layer (`Main`). Business methods call `StudentDAO` interfaces instead of direct MySQL implementations.
* **PreparedStatement**: All queries use parameterized queries (`?` placeholders) and typed setters. This prevents **SQL Injection** and improves statement parsing performance.
* **Try-with-Resources**: Ensures database statements, result sets, and connection sockets are closed automatically upon block exit, avoiding memory/connection leaks.
* **Scanner Buffer Clearing**: Avoids Java's common console input skipping bug by reading inputs as entire strings (`nextLine()`) and parsing them manually where numbers are required.
