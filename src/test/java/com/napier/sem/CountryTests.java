package com.napier.sem;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

public class CountryTests
{
    private static Connection conn;
    private static CountryMethod countryMethod;

    @BeforeAll
    static void init()
    {
        String jdbcUrl = "jdbc:mysql://localhost:3306/world";
        String username = "root";
        String password = "sem";
        try
        {
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            countryMethod = new CountryMethod(conn);
        } catch (SQLException e)
        {
            fail("failed to connect to the database");
        }
    }

    @AfterAll
    static void tearDown() throws SQLException
    {
        if (conn != null && !conn.isClosed())
        {
            conn.close();
        }
    }

    @Test
    void doesCountryTableExist() throws SQLException
    {
        assertTrue(countryMethod.doesCountryTableExist());
    }
    @Test
    void insertDataInCountry() throws SQLException
    {
        assertTrue(countryMethod.insertDataInCountry());
    }
}