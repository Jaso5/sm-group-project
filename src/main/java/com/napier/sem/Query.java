package com.napier.sem;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Query interface for generic query execution in CLI
 */
public class Query {
    private boolean lang = false;
    private Region reg1 = Region.NONE;
    private Region reg2 = Region.NONE;
    private String reg2Name;
    private int nRows = -1;

    public Query()
    {

    }

    /**
     * Building function to specify if this is a language query.
     * @param lang If it is a language query or not.
     * @return Self reference to continue calling other building functions.
     */
    public Query addLanguage(boolean lang)
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

        if (reg1 == Region.NONE && !lang)
        {
            System.out.println("Must enter either language or reg1!");
            return null;
        }
        if (lang)
        {
            query += "language, SUM((percentage*population)/100) AS languagepop, " +
                     "CONCAT(ROUND((SUM(percentage*population)/(SELECT SUM(population) FROM country)), 2), '%') AS \"%speakers in world\" " +
                     "FROM countrylanguage " +
                     "JOIN country ON (country.code=countrylanguage.countrycode) " +
                     "WHERE language = 'English' " +
                        "OR language = 'Chinese' " +
                        "OR language = 'Hindi' " +
                        "OR language = 'Spanish' " +
                        "OR language = 'Arabic' " +
                     "GROUP BY language " +
                     "ORDER BY SUM(population) DESC;";
            return query;
        }

        switch (reg1)
        {
            case WORLD:
                query += "SUM(population) AS \"world population\" FROM country;";

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
     * Executes query and outputs as csv.
     * @param con Connection to database
     */
    public void execute(Connection con) {
        String query = buildQuery();

        System.out.println("Sending query: " + query);
        ResultSet rs;
        try {
            rs = con.createStatement().executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        CSVWriter csvWriter = null;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            csvWriter = new CSVWriter(new FileWriter("web/" + timestamp.toString() + ".csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            csvWriter.writeAll(rs, true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

