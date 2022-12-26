import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        ConfigReader configReader = new ConfigReader("default");
        Properties props = null;
        try {
            props = configReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SimulationConfig simConfig = new SimulationConfig(prps);
        System.out.println(simConfig.getParameter("energy_start"));
    }
}
