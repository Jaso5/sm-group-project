package com.napier.sem;
import java.sql.*;

public class CountryMethod
{
    private Connection conn;
    public CountryMethod(Connection conn)
    {
        this.conn = conn;
    }
    public boolean doesCountryTableExist() throws SQLException
    {
        DatabaseMetaData dbm = conn.getMetaData();
        ResultSet tables = dbm.getTables(null, null, "country", null);
        boolean exists = tables.next();
        tables.close();
        return exists;
    }
}
