package agh;

import java.io.*;
import java.util.Properties;

/**
 * ConfigReader is responsible for reading the config.
 */
public class ConfigReader {
    private final String fileName;
    private final String filePath;

    /**
     * Default constructor.
     * @param fileName The name of .properties file without a file extension. File is meant to be in the project's resources folder by default.
     */
    public ConfigReader(String fileName) {
        this.fileName = fileName;
        this.filePath = null;
    }

    public ConfigReader(File file) {
        this(String.valueOf(file.toPath()), null);
    }

    /**
     * Constructor for case where config used in the simulation is not a default one.
     * @param filePath The path of .properties file.
     * @param fileName The name of .properties file without a file extension.
     */
    public ConfigReader(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    /** Loads the file into memory and returns Properties object with already loaded config pairs (key, value).
     * @return Properties objects
     * @throws IOException Throws the exception if file is missing.
     */
    public Properties read() throws IOException {
        if (this.filePath == null) {
            return this.readFromResources();
        }

        return this.readFromFilePath();
    }

    /**
     * Reads a config from the given path
     * @return Properties object
     * @throws IOException If error occured during reading the file
     */
    private Properties readFromFilePath() throws IOException {
        File file = new File(this.filePath);
        FileInputStream fileInputStream = new FileInputStream(file);

        Properties appProp = new Properties();
        appProp.load(fileInputStream);

        return appProp;
    }

    /**
     * Reads the config from resource. It meant to be used to read default config from the app's resource directory.
     * @return Properties object.
     * @throws IOException Throws if error occured during reading the stream.
     * @throws NullPointerException Throws if the resource stream is null. So, ex. given file that does not exist.
     */
    private Properties readFromResources() throws IOException, NullPointerException {
        InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream(this.fileName);

        Properties appProp = new Properties();
        appProp.load(fileInputStream);

        return appProp;
    }
}
