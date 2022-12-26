import java.io.*;
import java.util.Properties;

/**
 * ConfigReader is reponsible for reading the config.
 */
public class ConfigReader {
    private final String fileName;
    private final String filePath;

    /**
     * @param fileName The name of .properties file without a file extension. File is meant to be in the project's resources folder by default.
     */
    public ConfigReader(String fileName) {
        this.fileName = fileName;
        this.filePath = null;
    }

    /**
     * @param filePath The path of .properties file.
     * @param fileName The name of .properties file without a file extension.
     */
    public ConfigReader(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    /**
     * @return Loads the file into memory and returns Properties object with already loaded config pairs (key, value).
     * @throws IOException Throws the exception if file is missing.
     */
    public Properties read() throws IOException {
        if (this.filePath == null) {
            return this.readFromResources();
        }

        return this.readFromFilePath();
    }

    // TODO Implement the method. It is meant to load a file from given filepath. Given by the user in the GUI before the simulation start.
    private Properties readFromFilePath() throws IOException {
        Properties appProp = null;

        return appProp;
    }

    private Properties readFromResources() throws IOException {
        InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream(this.fileName + ".properties");

        Properties appProp = new Properties();
        appProp.load(fileInputStream);

        return appProp;
    }
}
