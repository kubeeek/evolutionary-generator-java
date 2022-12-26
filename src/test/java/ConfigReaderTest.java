import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConfigReaderTest {
    @Test
    void testValidFilename() throws IOException {
        var configReader = new ConfigReader("default");
        var defaultProps = configReader.read();

        assertEquals(15, defaultProps.size());
    }

    @Test()
    void testInvalidFilename() {
        var configReader = new ConfigReader("this_does_not_exist");

        assertThrows(NullPointerException.class, configReader::read);
    }
}
