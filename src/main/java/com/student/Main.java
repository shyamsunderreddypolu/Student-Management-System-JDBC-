package com.student;

import com.student.dao.StudentDAO;
import com.student.dao.StudentDAOImpl;
import com.student.model.Student;

import java.util.List;
import java.util.Scanner;

/**
 * The Main class serves as the entry point for the console-based Student Management System.
 * It provides a command-line interface (CLI) to interact with the database operations.
 */
public class Main {

    // Instantiate the DAO implementation class using the interface reference.
    // This allows us to access all CRUD database methods.
    private static final StudentDAO studentDAO = new StudentDAOImpl();
    
    // Scanner object to read user input from the console standard input.
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("================================================");
        System.out.println("     WELCOME TO STUDENT MANAGEMENT SYSTEM");
        System.out.println("================================================");

        boolean running = true;
        // Keep the application running in a loop until the user decides to exit (Choice 6).
        while (running) {
            printMenu();
            System.out.print("Enter your choice (1-6): ");
            
            // Read user choice. If the input is not an integer, handle it.
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                continue;
            }

            // Route to the appropriate helper method based on user choice.
            switch (choice) {
                case 1:
                    handleAddStudent();
                    break;
                case 2:
                    handleViewAllStudents();
                    break;
                case 3:
                    handleSearchStudentById();
                    break;
                case 4:
                    handleUpdateStudent();
                    break;
                case 5:
                    handleDeleteStudent();
                    break;
                case 6:
                    System.out.println("Thank you for using Student Management System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please select an option from 1 to 6.");
            }
            System.out.println(); // Print a blank line for visual formatting spacing
        }
        
        // Close scanner resource to avoid memory leak warnings
        scanner.close();
    }

    /**
     * Prints the console options menu.
     */
    private static void printMenu() {
        System.out.println("------------- MENU -------------");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Update Student Details");
        System.out.println("5. Delete Student");
        System.out.println("6. Exit");
        System.out.println("--------------------------------");
    }

    /**
     * Helper to collect new student details from console and trigger DB insert.
     */
    private static void handleAddStudent() {
        System.out.println("\n--- Add New Student ---");
        
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Enter Email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Enter Age: ");
        int age;
        try {
            age = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid age format. Age must be a number. Registration aborted.");
            return;
        }
        
        System.out.print("Enter Course Name: ");
        String course = scanner.nextLine().trim();

        // Create a new Student model carrier object (no ID needed, database auto-increments it)
        Student newStudent = new Student(name, email, age, course);
        
        // Send the student object to the DAO layer for DB insertion
        studentDAO.addStudent(newStudent);
    }

    /**
     * Helper to read and display all students currently in the database.
     */
    private static void handleViewAllStudents() {
        System.out.println("\n--- List of All Students ---");
        
        // Request list of students from the DAO layer
        List<Student> students = studentDAO.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("No records found in the database.");
        } else {
            // Print each student object using its toString() format
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    /**
     * Helper to search for a specific student using their database primary key (ID).
     */
    private static void handleSearchStudentById() {
        System.out.println("\n--- Search Student by ID ---");
        System.out.print("Enter Student ID: ");
        
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. ID must be a number.");
            return;
        }

        // Query the DAO layer for the student
        Student student = studentDAO.getStudentById(id);
        
        if (student != null) {
            System.out.println("Record Found:");
            System.out.println(student);
        } else {
            System.out.println("No student found with ID " + id);
        }
    }

    /**
     * Helper to update database records for an existing student.
     */
    private static void handleUpdateStudent() {
        System.out.println("\n--- Update Student Details ---");
        System.out.print("Enter ID of Student to update: ");
        
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. ID must be a number.");
            return;
        }

        // Verify first if the student exists before asking for details
        Student existingStudent = studentDAO.getStudentById(id);
        if (existingStudent == null) {
            System.out.println("No student found with ID " + id + ". Update aborted.");
            return;
        }

        System.out.println("Current details: " + existingStudent);
        System.out.println("Enter new details (press enter to keep existing values):");

        System.out.print("New Name [" + existingStudent.getName() + "]: ");
        String nameInput = scanner.nextLine().trim();
        String name = nameInput.isEmpty() ? existingStudent.getName() : nameInput;

        System.out.print("New Email [" + existingStudent.getEmail() + "]: ");
        String emailInput = scanner.nextLine().trim();
        String email = emailInput.isEmpty() ? existingStudent.getEmail() : emailInput;

        System.out.print("New Age [" + existingStudent.getAge() + "]: ");
        String ageInput = scanner.nextLine().trim();
        int age = existingStudent.getAge();
        if (!ageInput.isEmpty()) {
            try {
                age = Integer.parseInt(ageInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid age format. Keeping old age: " + age);
            }
        }

        System.out.print("New Course [" + existingStudent.getCourse() + "]: ");
        String courseInput = scanner.nextLine().trim();
        String course = courseInput.isEmpty() ? existingStudent.getCourse() : courseInput;

        // Build updated student object with the same ID
        Student updatedStudent = new Student(id, name, email, age, course);
        
        // Pass the updated details to the DAO layer for persistence
        studentDAO.updateStudent(updatedStudent);
    }

    /**
     * Helper to delete a student record by ID.
     */
    private static void handleDeleteStudent() {
        System.out.println("\n--- Delete Student Record ---");
        System.out.print("Enter Student ID to delete: ");
        
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. ID must be a number.");
            return;
        }

        // Search first to verify existence
        Student student = studentDAO.getStudentById(id);
        if (student == null) {
            System.out.println("No student found with ID " + id + ". Deletion aborted.");
            return;
        }

        System.out.println("Are you sure you want to delete: " + student.getName() + "? (y/n)");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        
        if (confirmation.equals("y") || confirmation.equals("yes")) {
            // Trigger deletion in the DAO layer
            studentDAO.deleteStudent(id);
        } else {
            System.out.println("Deletion canceled.");
        }
    }
}
