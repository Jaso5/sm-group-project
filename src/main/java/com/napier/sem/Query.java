package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Query interface for generic query execution in CLI
 */
public class Query {
    Region reg1;
    Region reg2;

    void execute(Connection con)
    {
        execute(con, 0);
    }
    void execute(Connection con, int n)
    {

        String query = "SELECT name FROM " + reg1.name().toLowerCase() + " ORDER BY population;";
        if (n!=0)
        {
            query = "SELECT name FROM " + reg1.name().toLowerCase() + " ORDER BY population DESC LIMIT " + n + ";";
        }

        ResultSet rs;
        try {
            rs = con.createStatement().executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (rs==null)
        {
            return;
        }

        String regName = null;
        switch (reg1)
        {
            case CONTINENT:
            case DISTRICT:
            case REGION:
                try {
                    regName = rs.getString(reg1.name().toLowerCase());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case CITY:
            case COUNTRY:
                try {
                    regName = rs.getString(reg1.name().toLowerCase() + "Name");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
        }


    }
}
