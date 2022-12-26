import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimulationConfigReaderIntegrationTest {
    @Test
    void testValidDefaultConfig() throws IOException {
        var configReader = new ConfigReader("default");
        var defaultProps = configReader.read();

        new SimulationConfig(defaultProps);
    }

    @Test()
    void testInvalidDefaultConfig() {
        var configReader = new ConfigReader("invalid_default");
        Properties defaultProps = null;

        try {
            defaultProps = configReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Properties finalDefaultProps = defaultProps;

        var exception = assertThrows(IllegalArgumentException.class, () -> new SimulationConfig(finalDefaultProps));
        assertEquals(exception.getMessage(), "Default config is invalid. Missing pairs.");
    }

    // TODO
    void testValidUserConfig() {}
    void testInvalidUserConfig() {}

}
