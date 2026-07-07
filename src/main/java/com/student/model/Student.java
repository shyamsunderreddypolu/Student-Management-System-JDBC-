package com.student.model;

/**
 * The Student class is a POJO (Plain Old Java Object) representing a student entity.
 * It maps directly to the columns of the 'students' table in our MySQL database.
 */
public class Student {
    
    // Member variables mapping to table columns
    private int id;          // Maps to the 'id' column (Primary Key)
    private String name;     // Maps to the 'name' column
    private String email;    // Maps to the 'email' column
    private int age;         // Maps to the 'age' column
    private String course;   // Maps to the 'course' column

    /**
     * Default No-Args Constructor.
     * Needed by many frameworks and useful for blank object creation.
     */
    public Student() {
    }

    /**
     * Constructor without 'id'.
     * Used when creating a NEW student to insert into the database.
     * Since the database 'id' is AUTO_INCREMENT, MySQL will generate the ID for us.
     * 
     * @param name Name of the student
     * @param email Email of the student
     * @param age Age of the student
     * @param course Course enrolled
     */
    public Student(String name, String email, int age, String course) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.course = course;
    }

    /**
     * Full Constructor including 'id'.
     * Used when fetching an EXISTING student from the database, or 
     * when we need to update/delete a specific student record by their ID.
     * 
     * @param id ID of the student
     * @param name Name of the student
     * @param email Email of the student
     * @param age Age of the student
     * @param course Course enrolled
     */
    public Student(int id, String name, String email, int age, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.course = course;
    }

    // ==========================================
    // Getters and Setters
    // ==========================================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    // ==========================================
    // toString() Method
    // ==========================================

    /**
     * Returns a formatted string representation of the Student object.
     * Useful for displaying student data directly in our console UI.
     */
    @Override
    public String toString() {
        return String.format("Student [ID: %d | Name: %s | Email: %s | Age: %d | Course: %s]", 
                id, name, email, age, course);
    }
}
