import agh.ConfigReader;
import agh.SimulationConfig;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationConfigReaderIntegrationTest {
    @Test
    void testValidDefaultConfig() throws IOException {
        var configReader = new ConfigReader("default.properties");
        var defaultProps = configReader.read();

        new SimulationConfig(defaultProps);
    }

    @Test()
    void testInvalidDefaultConfigName() {
        var configReader = new ConfigReader("invalid_default");

        assertThrows(NullPointerException.class, configReader::read);
    }

    @Test()
    void testInvalidDefaultConfig() throws IOException {
        var configReader = new ConfigReader("invalid_default.properties");
        Properties defaultProps = new Properties();

        var props = configReader.read();


        var exception = assertThrows(IllegalArgumentException.class, () -> new SimulationConfig(props));
        assertEquals(exception.getMessage(), "Default config is invalid. Missing pairs.");
    }

    @Test
    void testValidUserConfig() throws URISyntaxException, IOException {
        URL res = getClass().getClassLoader().getResource("default.properties");
        File file = Paths.get(res.toURI()).toFile();

        var defaultConfigProps = new ConfigReader("default.properties").read();
        var userConfigProps = new ConfigReader(file).read();

        assertDoesNotThrow(() -> new SimulationConfig(userConfigProps, defaultConfigProps));

        SimulationConfig simulationConfig = new SimulationConfig(userConfigProps, defaultConfigProps);
        assertEquals("50", simulationConfig.getParameter("width"));
    }

    @Test
    @Description("If the key is missing in user's config, a default value should be returned")
    void testInvalidUserConfig() throws URISyntaxException, IOException {
        URL res = getClass().getClassLoader().getResource("invalid_default.properties");
        File file = Paths.get(res.toURI()).toFile();

        var defaultConfigProps = new ConfigReader("default.properties").read();
        var userConfigProps = new ConfigReader(file).read();

        assertDoesNotThrow(() -> new SimulationConfig(userConfigProps, defaultConfigProps));

        SimulationConfig simulationConfig = new SimulationConfig(userConfigProps, defaultConfigProps);
        assertEquals("5", simulationConfig.getParameter("energy_reproduce_loss"));
    }

}
