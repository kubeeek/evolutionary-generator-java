package agh;

import agh.gui.App;
import agh.simulation.SimulationEngine;
import agh.simulation.config.SimulationConfig;
import javafx.application.Application;

import java.io.IOException;
import java.util.Properties;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        var configReader = new ConfigReader("default.properties");
        Properties defaultProps = null;

        try {
            defaultProps = configReader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SimulationConfig simulationConfig = new SimulationConfig(defaultProps);

        var engine = new SimulationEngine(simulationConfig, null);
                for(int i = 0; i < 100000; i++) {
                    engine.start();
                    System.out.println(i);
                }

    }
}
