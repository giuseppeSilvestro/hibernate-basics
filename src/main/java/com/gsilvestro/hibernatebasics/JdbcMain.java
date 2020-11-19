package com.gsilvestro.hibernatebasics;

import com.gsilvestro.hibernatebasics.model.Contact;

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
            Contact c = new Contact("Giuseppe", "Silvestro", "cnsbca@live.com", 123456798L);
            save(c, statement);
            c = new Contact("Gennaro", "Esposito", "cbsivai@live.com", 789456123L);
            save(c, statement);
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

    //Save a new contact in the database
    public static void save(Contact contact, Statement statement) throws SQLException {
        //get the values from the Contact object and compose an INSERT query
        String sql = "INSERT INTO contacts (firstname, lastname, email, id) VALUES ('%s', '%s', '$s', $d)";
        sql = String.format(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getId());

        //execute the query
        statement.executeUpdate(sql);
    }
}
