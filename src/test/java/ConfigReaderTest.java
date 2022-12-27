import agh.ConfigReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConfigReaderTest {
    @Test
    void testValidFilename() throws IOException {
        var configReader = new ConfigReader("default.properties");
        var defaultProps = configReader.read();

        assertEquals(15, defaultProps.size());
        assertEquals("50", defaultProps.getProperty("width"));

    }

    @Test()
    void testInvalidFilename() {
        var configReader = new ConfigReader("this_does_not_exist");

        assertThrows(NullPointerException.class, configReader::read);
    }
}
