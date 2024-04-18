package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Query interface for generic query execution in CLI
 */
public class Query {
    private Language lang = Language.NONE;
    private Region reg1 = Region.NONE;
    private Region reg2 = Region.NONE;
    private String reg2Name;
    private int nRows = -1;

    public Query()
    {

    }

    //TODO: Do something about languages.
    /**
     * Building function to specify if this is a language query.
     * @param lang
     * @return
     */
    public Query addLanguage(Language lang)
    {
        this.lang = lang;
        return this;
    }

    /**
     * Building function to specify type of region to list.
     * @param reg1 Type of region.
     * @return Self reference to continue calling other building functions.
     */
    public Query addReg1(Region reg1)
    {
        this.reg1 = reg1;
        return this;
    }

    /**
     * Building function to specify what specific region to look for.
     * @param reg2 Type of region for WHERE clause.
     * @param reg2Name Name of region for WHERE clause.
     * @return Self reference to continue calling other building functions.
     */
    public Query addReg2(Region reg2, String reg2Name)
    {
        this.reg2 = reg2;
        this.reg2Name = reg2Name;
        return this;
    }

    /**
     * Building function to specify the number of rows in the results table.
     * @param nRows The maximum number of rows the results table can have.
     * @return Self reference to continue calling other building functions.
     */
    public Query addNRows(int nRows)
    {
        this.nRows = nRows;
        return this;
    }

    /**
     * Constructs query.
     * @return Constructed query.
     */
    public String buildQuery()
    {
        String query = "SELECT ";

        if (reg1 == Region.NONE && lang == Language.NONE)
        {
            System.out.println("Must enter either language or reg1!");
            return null;
        }
        if (lang != Language.NONE)
        {
//            query += "language,  FROM countrylanguage";
//            ResultSet rs;
//            try {
//                rs = con.createStatement().executeQuery(query);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//            return rs;
        }

        switch (reg1)
        {
            case WORLD:
                query += " SUM(population) AS \"world population\" FROM country;";

                return query;
            case REGION:
            case CONTINENT:
                query += reg1.name().toLowerCase() + ", SUM(country.population) AS population, " +
                        "CONCAT(ROUND((PIC.a/SUM(country.population))*100, 2), '%') AS \"%pop in cities\", " +
                        "CONCAT(100-ROUND((PIC.a/SUM(country.population))*100, 2), '%') AS \"%pop not in cities\" " +
                        "FROM country " +
                        "JOIN city ON (country.code=city.countrycode) " +
                        "JOIN (SELECT SUM(city.population) AS a, " + reg1.name().toLowerCase() + " AS b " +
                        "FROM city " +
                        "JOIN country ON (city.countrycode=country.code) " +
                        "GROUP BY " + reg1.name().toLowerCase() + ") PIC on (PIC.b=" + reg1.name().toLowerCase() +") " +
                        "GROUP BY " + reg1.name().toLowerCase() + " ";
                break;
            case COUNTRY:
                query += "country.code, country.name, country.continent, country.region, " +
                        "country.population, city.name as capital, " +
                        "CONCAT(ROUND((PIC.a/country.population)*100, 2), '%') AS \"%Pop in Cities\", " +
                        "CONCAT(100-ROUND((PIC.a/country.population)*100, 2), '%') AS \"%Pop not in Cities\" " +
                        "FROM country " +
                        "JOIN city ON (country.capital=city.id) " +
                        "JOIN (SELECT SUM(city.population) as a, city.countrycode as b FROM city GROUP BY city.countrycode) PIC on (PIC.b=country.code) ";
                break;
            case DISTRICT:
                query += "district, SUM(city.population) AS population" +
                        "FROM country " +
                        "JOIN city ON (country.code=city.countrycode) ";
            case CAPITAL:
                query += "city.name, city.population AS population " +
                        "FROM country " +
                        "JOIN city ON (country.capital=city.id) ";
                break;
            case CITY:
                query += "city.name, city.population AS population " +
                        "FROM country " +
                        "JOIN city ON (country.code=city.countrycode) ";
                break;
        }

        switch (reg2)
        {
            case REGION:
            case CONTINENT:
            case DISTRICT:
                query += "WHERE " + reg2.name().toLowerCase() + "=\"" + reg2Name + "\" ";
                break;
            case COUNTRY:
            case CAPITAL:
            case CITY:
                query += "WHERE " + reg2.name().toLowerCase() + ".name=\"" + reg2Name + "\" ";
                break;
            case NONE:
                break;
        }

        switch (reg1)
        {
            case CONTINENT:
            case REGION:
            case DISTRICT:
                query += "ORDER BY SUM(population) DESC ";
                break;
            case COUNTRY:
            case CAPITAL:
            case CITY:
                query += "ORDER BY population DESC ";
                break;
        }

        if (nRows>0)
        {
            query += "LIMIT " + nRows;
        }

        query += ";";
        return query;
    }

    /**
     * Executes query.
     * @param con Connection to database
     * @return ResultSet from query
     */
    public ResultSet execute(Connection con) {
        String query = buildQuery();

        ResultSet rs;
        try {
            rs = con.createStatement().executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
}

