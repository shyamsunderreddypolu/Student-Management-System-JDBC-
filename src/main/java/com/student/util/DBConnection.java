package com.student.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection provides a centralized way to establish a connection to our MySQL Database.
 * It uses standard JDBC classes to manage connection details.
 */
public class DBConnection {

    // JDBC URL: Specifies database type (mysql), host (localhost), port (3306), database name (student_db),
    // and timezone/SSL properties to prevent common connection warnings.
    private static final String URL = "jdbc:mysql://localhost:3306/student_db?useSSL=false&serverTimezone=UTC";
    
    // MySQL username (default for local installation is usually 'root')
    private static final String USER = "root";
    
    // MySQL password (change this to match your actual local MySQL root password)
    private static final String PASSWORD = "root";

    /**
     * Establishes and returns a connection to the MySQL database.
     * 
     * @return Connection object if connection is successful
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Explicitly load the MySQL JDBC Driver class.
            // Although modern JDBC (4.0+) loads drivers automatically,
            // explicitly loading it is an excellent practice for learning and troubleshooting.
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Attempt to establish the connection using the credentials defined above.
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (ClassNotFoundException e) {
            // Thrown if the MySQL Connector/J driver dependency is missing from the classpath (pom.xml)
            System.err.println("Error: MySQL JDBC Driver not found! Please check your Maven dependencies.");
            e.printStackTrace();
            throw new SQLException("MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            // Thrown if credentials are wrong, database server is offline, or database does not exist
            System.err.println("Error: Failed to connect to the database! Check if MySQL is running and your credentials are correct.");
            e.printStackTrace();
            throw e;
        }
        return connection;
    }
}
