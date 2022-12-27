package agh;

import java.util.Properties;


public class SimulationConfig {
    private Properties defaultProp;
    private Properties userProp;

    public SimulationConfig(Properties userProp, Properties defaultProp) {
        validateDefaultConfig(defaultProp);
        this.userProp = userProp;
    }

    public SimulationConfig(Properties defaultProp) {
        validateDefaultConfig(defaultProp);
        this.userProp = new Properties();
    }

    private void validateDefaultConfig(Properties defaultProp) {
        int expectedConfigPairs = 15;
        if (defaultProp.size() < expectedConfigPairs) {
            throw new IllegalArgumentException("Default config is invalid. Missing pairs.");
        }

        this.defaultProp = defaultProp;
    }

    void setUserConfig(Properties userProp) {
        this.userProp = userProp;
    }

    public String getParameter(String key) {
        return this.userProp.getProperty(key, this.defaultProp.getProperty(key));
    }
}
