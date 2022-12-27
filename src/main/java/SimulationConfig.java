import java.util.Properties;


public class SimulationConfig {
    private Properties defaultProp;
    private final Properties userProp;
    final int expectedConfigPairs = 15;

    public SimulationConfig(Properties userProp, Properties defaultProp) {
        validateDefaultConfig(defaultProp);
        this.userProp = userProp;
    }

    public SimulationConfig(Properties defaultProp) {
        validateDefaultConfig(defaultProp);
        this.userProp = new Properties();
    }

    private void validateDefaultConfig(Properties defaultProp) {
        if (defaultProp.size() < this.expectedConfigPairs) {
            throw new IllegalArgumentException("Default config is invalid. Missing pairs.");
        }

        this.defaultProp = defaultProp;
    }

    String getParameter(String key) {
        return this.userProp.getProperty(key, this.defaultProp.getProperty(key));
    }
}
