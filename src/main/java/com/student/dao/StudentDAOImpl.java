package com.student.dao;

import com.student.model.Student;
import com.student.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentDAOImpl implements the StudentDAO interface.
 * It contains the concrete JDBC code to perform CRUD operations on the 'students' table.
 */
public class StudentDAOImpl implements StudentDAO {

    // SQL Query templates defined as constants to avoid hardcoding inside methods.
    // Question marks (?) are placeholders for parameters, to be replaced by PreparedStatement.
    private static final String INSERT_STUDENT_SQL = "INSERT INTO students (name, email, age, course) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_STUDENTS_SQL = "SELECT id, name, email, age, course FROM students";
    private static final String SELECT_STUDENT_BY_ID_SQL = "SELECT id, name, email, age, course FROM students WHERE id = ?";
    private static final String UPDATE_STUDENT_SQL = "UPDATE students SET name = ?, email = ?, age = ?, course = ? WHERE id = ?";
    private static final String DELETE_STUDENT_SQL = "DELETE FROM students WHERE id = ?";

    /**
     * CREATE operation: Adds a new student record to the database.
     */
    @Override
    public void addStudent(Student student) {
        // Try-with-resources automatically closes the Connection and PreparedStatement when done.
        // This prevents connection leaks that can slow down or crash the database.
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT_SQL)) {
            
            // Set the parameter values for the query placeholders (1-indexed)
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getEmail());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setString(4, student.getCourse());

            // executeUpdate() is used for INSERT, UPDATE, or DELETE statements.
            // It returns the number of rows affected.
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Success: Student record added successfully!");
            }
            
        } catch (SQLException e) {
            // Print error details if the insert fails (e.g. duplicate email)
            System.err.println("Error adding student: " + e.getMessage());
        }
    }

    /**
     * READ operation: Retrieves all student records.
     */
    @Override
    public List<Student> getAllStudents() {
        List<Student> studentsList = new ArrayList<>();
        
        // Try-with-resources ensures Connection, PreparedStatement, and ResultSet are closed
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENTS_SQL);
             // executeQuery() is used for SELECT statements. It returns a ResultSet.
             ResultSet resultSet = preparedStatement.executeQuery()) {
            
            // Iterate through the result set rows one by one
            while (resultSet.next()) {
                // Read field values from the current row in the result set
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("age");
                String course = resultSet.getString("course");
                
                // Construct a Student object and add it to our array list
                studentsList.add(new Student(id, name, email, age, course));
            }
            
        } catch (SQLException e) {
            System.err.println("Error reading students: " + e.getMessage());
        }
        return studentsList;
    }

    /**
     * READ operation: Searches for a student by their ID.
     */
    @Override
    public Student getStudentById(int id) {
        Student student = null;
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_BY_ID_SQL)) {
            
            // Bind the ID parameter to the SQL query placeholder
            preparedStatement.setInt(1, id);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // If a matching row is found
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    int age = resultSet.getInt("age");
                    String course = resultSet.getString("course");
                    
                    // Create the student object to return
                    student = new Student(id, name, email, age, course);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching student: " + e.getMessage());
        }
        return student;
    }

    /**
     * UPDATE operation: Modifies an existing student's data.
     */
    @Override
    public void updateStudent(Student student) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STUDENT_SQL)) {
            
            // Bind new values to placeholders
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getEmail());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setString(4, student.getCourse());
            preparedStatement.setInt(5, student.getId()); // Locate student record by ID

            // Execute the update query
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Success: Student record updated successfully!");
            } else {
                System.out.println("Warning: No student found with ID " + student.getId());
            }
            
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
        }
    }

    /**
     * DELETE operation: Removes a student record from the database.
     */
    @Override
    public void deleteStudent(int id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STUDENT_SQL)) {
            
            // Bind the target ID to delete
            preparedStatement.setInt(1, id);

            // Execute deletion
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Success: Student record deleted successfully!");
            } else {
                System.out.println("Warning: No student found with ID " + id);
            }
            
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }
    }
}
