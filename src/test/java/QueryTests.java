import com.napier.sem.Region;
import com.napier.sem.Query;
import org.junit.jupiter.api.*;
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
}
