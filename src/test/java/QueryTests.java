import com.napier.sem.MySQLConnector;
import com.napier.sem.Region;
import com.napier.sem.Query;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QueryTests
{
    @Test
    void WorldPopulation()
    {
        assertEquals(new Query().addReg1(Region.WORLD).buildQuery(), "SELECT SUM(population) AS \"world population\" FROM country;");
    }
    @Test
    void AllCountriesQuery()
    {
        assertEquals(new Query().addReg1(Region.COUNTRY).buildQuery(), "SELECT country.code, country.name, country.continent, country.region, country.population, city.name as capital, CONCAT(ROUND((PIC.a/country.population)*100, 2), '%') AS \"%Pop in Cities\", CONCAT(100-ROUND((PIC.a/country.population)*100, 2), '%') AS \"%Pop not in Cities\" FROM country JOIN city ON (country.capital=city.id) JOIN (SELECT SUM(city.population) as a, city.countrycode as b FROM city GROUP BY city.countrycode) PIC on (PIC.b=country.code) ORDER BY population DESC ;");
    }
    @Test
    void AllCitiesQuery()
    {
        assertEquals(new Query().addReg1(Region.CITY).buildQuery(), "SELECT city.name, city.population AS population FROM country JOIN city ON (country.code=city.countrycode) ORDER BY population DESC ;");
    }
    @Test
    void EmptyQuery() {
        assertNull(new Query().buildQuery(), "Must enter either language or reg1!");
    }
    @Test
    void ContinentPopulationQuery() {
        assertEquals(new Query().addReg1(Region.CONTINENT).buildQuery(), "SELECT continent, SUM(country.population) AS population, CONCAT(ROUND((PIC.a/SUM(country.population))*100, 2), '%') AS \"%pop in cities\", CONCAT(100-ROUND((PIC.a/SUM(country.population))*100, 2), '%') AS \"%pop not in cities\" FROM country JOIN city ON (country.code=city.countrycode) JOIN (SELECT SUM(city.population) AS a, continent AS b FROM city JOIN country ON (city.countrycode=country.code) GROUP BY continent) PIC on (PIC.b=continent) GROUP BY continent ORDER BY SUM(population) DESC ;");
    }
    @Test
    void SpecificCountryQuery() {
        assertEquals(new Query().addReg1(Region.COUNTRY).addReg2(Region.COUNTRY, "USA").buildQuery(), "SELECT country.code, country.name, country.continent, country.region, country.population, city.name as capital, CONCAT(ROUND((PIC.a/country.population)*100, 2), '%') AS \"%Pop in Cities\", CONCAT(100-ROUND((PIC.a/country.population)*100, 2), '%') AS \"%Pop not in Cities\" FROM country JOIN city ON (country.capital=city.id) JOIN (SELECT SUM(city.population) as a, city.countrycode as b FROM city GROUP BY city.countrycode) PIC on (PIC.b=country.code) WHERE country.name=\"USA\" ORDER BY population DESC ;");
    }
    @Test
    void CapitalCityQuery() {
        assertEquals(new Query().addReg1(Region.CAPITAL).buildQuery(), "SELECT city.name, city.population AS population FROM country JOIN city ON (country.capital=city.id) ORDER BY population DESC ;");
    }
    @Test
    void GenerateConnectionUrl()
    {
        MySQLConnector connector = new MySQLConnector("user", "pass", "dbName");
        String expectedUrl = "jdbc:mysql://db:3306/dbName?allowPublicKeyRetrieval=true&useSSL=false";
        assertEquals(connector.generateConnectionUrl(), expectedUrl, "The connection matches with the Url");

    }
}
