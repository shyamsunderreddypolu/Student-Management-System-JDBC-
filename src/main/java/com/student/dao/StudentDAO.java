package com.student.dao;

import com.student.model.Student;
import java.util.List;

/**
 * The StudentDAO interface defines the abstract CRUD (Create, Read, Update, Delete) 
 * operations for Student data persistence.
 * This separates the business logic of our application from the database implementation details.
 */
public interface StudentDAO {

    /**
     * Inserts a new student record into the database.
     * 
     * @param student The Student object containing details to insert (except ID).
     */
    void addStudent(Student student);

    /**
     * Retrieves all student records from the database.
     * 
     * @return A List of Student objects representing all records.
     */
    List<Student> getAllStudents();

    /**
     * Finds a specific student in the database by their unique ID.
     * 
     * @param id The unique identifier of the student.
     * @return The Student object if found, or null if no record matches the ID.
     */
    Student getStudentById(int id);

    /**
     * Updates an existing student record in the database.
     * 
     * @param student The Student object containing updated details (must have a valid ID).
     */
    void updateStudent(Student student);

    /**
     * Deletes a student record from the database using their ID.
     * 
     * @param id The unique identifier of the student to delete.
     */
    void deleteStudent(int id);
}
