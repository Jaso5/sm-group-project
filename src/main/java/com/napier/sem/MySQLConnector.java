package com.napier.sem;

import java.sql.*;

public class MySQLConnector
{
    private final String user;
    private final String pass;
    private final String dbName;
    private Connection con = null;

    public MySQLConnector(String user, String pass, String dbName)
    {
        this.user = user;
        this.pass = pass;
        this.dbName = dbName;
    }
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 100;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(20000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/" + dbName + "?allowPublicKeyRetrieval=true&useSSL=false", user, pass);
                System.out.println("Successfully connected");
                // Wait a bit
                Thread.sleep(3000);
                // Exit for loop
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }
}
