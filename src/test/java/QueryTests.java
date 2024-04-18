import com.napier.sem.Region;
import com.napier.sem.Query;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class QueryTests
{
    @Test
    void WorldPopulation()
    {
        //assertEquals(new Query().addReg1(Region.WORLD).buildQuery(), "SELECT SUM(population) AS \"world population\" FROM country;");
    }
}
