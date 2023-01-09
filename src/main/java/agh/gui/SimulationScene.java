package agh.gui;

import agh.Animal;
import agh.Vector2d;
import agh.simulation.ISimulationChange;
import agh.simulation.SimulationEngine;
import agh.simulation.config.SimulationConfig;
import agh.world.IMap;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class SimulationScene implements ISimulationChange {
    private final Stage stage;
    private final SimulationConfig simulationConfig;
    private final HashMap<StackPane, List<GuiElementBox>> mapping = new HashMap<>();
    private SimulationEngine simulationEngine;

    public SimulationScene(SimulationConfig simulationConfig) {
        this.simulationConfig = simulationConfig;
        this.setup();
        this.stage = new Stage();
        this.stage.setTitle("Chuj")
        ;
        this.stage.setMinWidth(WindowConstant.SIM_WIDTH);
        this.stage.setMinHeight(WindowConstant.SIM_HEIGHT);

        GridPane userInterface = this.createUserInterface();
        var map = this.simulationEngine.getMap();
        GridPane mapGrid = this.createMapGrid(map);

        this.stage.setScene(new Scene(new HBox(userInterface, mapGrid)));
        this.stage.show();
        this.start();
    }

    private void start() {
        this.simulationEngine.start();


    }

    private void update() throws ExecutionException, InterruptedException {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    GridPane userInterface = createUserInterface();
                    // GridPane mapGrid = createMapGrid();

                    //stage.setScene(new Scene(new HBox(userInterface, mapGrid)));
                    stage.show();
                });
            }
        }, 0, 500);
        //this.update();
    }

    private void setup() {
        this.simulationEngine = new SimulationEngine(this.simulationConfig, this);
    }

    void calculateSpriteSize(int mapHeight, int mapWidth) {
        int biggerDimension = Math.max(mapWidth, mapHeight);


        if (WindowConstant.SIM_WIDTH >= WindowConstant.SIM_HEIGHT) {
            WindowConstant.SPRITE_HEIGHT = WindowConstant.SPRITE_WIDTH = (WindowConstant.SIM_WIDTH / biggerDimension) * 2 / 3;
        } else {
            WindowConstant.SPRITE_HEIGHT = WindowConstant.SPRITE_WIDTH = (WindowConstant.SIM_HEIGHT / biggerDimension) * 2 / 3;
        }
    }

    private synchronized GridPane createMapGrid(IMap map) {
        this.mapping.clear();
        Image dirtImage;

        try {
            dirtImage = new GuiElementBox().getImage();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        int mapHeight = map.getHeight();
        int mapWidth = map.getWidth();
        calculateSpriteSize(mapHeight, mapWidth);
        GridPane grid = new GridPane();
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                var stacked = new StackPane();

                stacked.getChildren().add(new GuiElementBox(dirtImage, null).getImageView());

                stacked.setMaxWidth(WindowConstant.SPRITE_HEIGHT);
                stacked.setMaxHeight(WindowConstant.SPRITE_HEIGHT);
                var occupants = map.objectsAt(new Vector2d(j, i)).stream().map(e -> {
                    try {
                        return new GuiElementBox(e);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }).toList();

                mapping.put(stacked, occupants);
                stacked.setMaxHeight(40);
                stacked.setMaxWidth(40);

                stacked.setOnMouseClicked(event -> {
                    System.out.println("clicked");
                    var occupant = mapping.get(stacked).stream().filter(e -> e.mapObject instanceof Animal).findFirst();
                    System.out.println(occupant.isPresent());
                    occupant.ifPresent(guiElementBox -> this.simulationEngine.animalChosen((Animal) guiElementBox.mapObject));
                });
                occupants.forEach(e -> stacked.getChildren().add(e.getImageView()));

                grid.add(stacked, j, i);
            }
        }

        return grid;
    }

    private GridPane createUserInterface() {
        return new GridPane();
    }
    @Override
    public synchronized void simulationChanged(IMap map) {
        Platform.runLater(() -> {
            GridPane userInterface = createUserInterface();
            GridPane mapGrid = this.createMapGrid(map);

            stage.setScene(new Scene(new HBox(userInterface, mapGrid)));
            stage.show();
        });
        System.out.println("notified");
    }

}
