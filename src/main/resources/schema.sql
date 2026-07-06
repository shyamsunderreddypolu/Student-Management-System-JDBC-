-- Create the database if it doesn't already exist
CREATE DATABASE IF NOT EXISTS student_db;

-- Switch to using the created database
USE student_db;

-- Create the students table to store student records
CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY, -- Unique ID for each student, auto-incremented by MySQL
    name VARCHAR(100) NOT NULL,        -- Student name, must not be empty
    email VARCHAR(100) UNIQUE NOT NULL, -- Student email, must be unique and not empty
    age INT NOT NULL,                  -- Student age
    course VARCHAR(100) NOT NULL       -- Course enrolled by student
);
