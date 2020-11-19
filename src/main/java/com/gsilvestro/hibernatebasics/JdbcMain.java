package com.gsilvestro.hibernatebasics;

import java.sql.*;

public class JdbcMain {

    public static void main(String[] args) throws ClassNotFoundException {
        // Load the SQLite JDBC driver (JDBC class implements java.sql.Driver)
        Class.forName("org.sqlite.JDBC");

        // Create a DB connection with a try with resources to autoclose the resource
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:contactmgr.db")) {

            // Create a SQL statement
            Statement statement = connection.createStatement();
            // Create a DB table
            statement.executeUpdate("DROP TABLE IF EXISTS contacts");
            statement.executeUpdate("CREATE TABLE contacts (id INTEGER PRIMARY KEY, firstname STRING, lastname STRING, email STRING, phone INT(10))");

            // Insert a couple contacts
            statement.executeUpdate("INSERT INTO contacts (firstname, lastname, email, phone) VALUES ('Gennaro', 'Esposito', 'ncsbaca@live.com', 123456789), ('Giuseppe', 'Silvestro', 'cjiosanoca@live.com', 789456123)");
            // Fetch all the records from the contacts table. We store the result in a ResultSet
            ResultSet selectAll = statement.executeQuery("SELECT * FROM contacts");

            // Iterate over the ResultSet & display contact info
            while (selectAll.next()) {
                int id = selectAll.getInt("id");
                String firstName = selectAll.getString("firstname");
                String lastName = selectAll.getString("lastname");
                System.out.printf("%s %s (%d)%n", firstName, lastName, id);
            }

        } catch (SQLException ex) {
            // Display connection or query errors
            System.err.printf("There was a database error: %s%n",ex.getMessage());
        }
    }
}
