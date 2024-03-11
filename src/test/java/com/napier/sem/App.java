package com.napier.sem;
import java.sql.*;

public class App {
    public static void main(String[] args)
    {
        String jdbcUrl = "jdbc:mysql://localhost:3306/world";
        String username = "root";
        String password = "sem";
        try
        {
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
            CountryMethod countryMethod = new CountryMethod(conn);
            boolean tableExists = countryMethod.doesCountryTableExist();
            System.out.println("does the country table exist: " + tableExists);
            conn.close();
        } catch (SQLException e)
        {
            System.out.println("database connection failed");
            e.printStackTrace();
        }
    }
}
