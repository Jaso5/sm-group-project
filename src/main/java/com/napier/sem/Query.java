package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Query interface for generic query execution in CLI
 */
public class Query {
    private boolean isPop;
    private Language lang;
    private Region reg1 = Region.NONE;
    private Region reg2 = Region.NONE;
    private String reg2Name;
    private int nRows = -1;

    public Query()
    {

    }

    public Query isPopulation(boolean isPop)
    {
        this.isPop = isPop;
        return this;
    }
    public Query addLanguage(Language lang)
    {
        this.lang = lang;
        return this;
    }

    public Query addReg1(Region reg1)
    {
        this.reg1 = reg1;
        return this;
    }

    public Query addReg2(Region reg2, String reg2Name)
    {
        this.reg2 = reg2;
        this.reg2Name = reg2Name;
        return this;
    }

    public Query addNRows(int nRows)
    {
        this.nRows = nRows;
        return this;
    }

    public ResultSet execute(Connection con) {
        return executePop(con);
    }

    private ResultSet executePop(Connection con)
    {
        String query = "SELECT ";

        switch (reg1)
        {
            case CONTINENT:
            case DISTRICT:
            case REGION:
                query += reg1.name() + ", SUM(population) AS pop FROM country GROUP BY " + reg1.name().toLowerCase() + " ";
                break;
            case CAPITAL:
                query += "city.name, city.population AS pop FROM country JOIN city on (country.capital=city.id) ";
            case CITY:
                query += "city.name, city.population AS pop FROM country JOIN city on (country.code=city.countrycode) ";
            case COUNTRY:
                query += "population AS pop FROM country ";
        }

        switch (reg2)
        {
            case CONTINENT:
            case DISTRICT:
            case REGION:
                query += "WHERE " + reg2.name().toLowerCase() + "=" + reg2Name + " ";
                break;
            case COUNTRY:
            case CAPITAL:
            case CITY:
                query += "WHERE " + reg2.name().toLowerCase() + ".name=" + reg2Name + " ";
                break;
            case NONE:
                break;
        }

        if (nRows>0)
        {
            query += "LIMIT " + nRows;
        }

        query += "ORDER BY pop DESC ;";

        System.out.println(query);

        ResultSet rs;
        try {
            rs = con.createStatement().executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    private ResultSet executeName(Connection con)
    {
        ResultSet rs;
        try {
            rs = con.createStatement().executeQuery("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
}
