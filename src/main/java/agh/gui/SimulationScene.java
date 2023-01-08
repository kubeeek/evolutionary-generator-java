package agh.gui;

import agh.Vector2d;
import agh.simulation.SimulationEngine;
import agh.simulation.config.SimulationConfig;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class SimulationScene {
    private final Stage stage;
    private final SimulationConfig simulationConfig;
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
        GridPane mapGrid = this.createMapGrid();

        this.stage.setScene(new Scene(new HBox(userInterface, mapGrid)));
        this.stage.show();
    }

    private void setup() {
        this.simulationEngine = new SimulationEngine(this.simulationConfig);

        this.simulationEngine.start();
    }

    void calculateSpriteSize(int mapHeight, int mapWidth) {
        int biggerDimension = Math.max(mapWidth, mapHeight);


        if (WindowConstant.SIM_WIDTH >= WindowConstant.SIM_HEIGHT) {
            WindowConstant.SPRITE_HEIGHT = WindowConstant.SPRITE_WIDTH = (WindowConstant.SIM_WIDTH / biggerDimension)*2/3;
        } else {
            WindowConstant.SPRITE_HEIGHT = WindowConstant.SPRITE_WIDTH = (WindowConstant.SIM_HEIGHT / biggerDimension)*2/3;
        }
    }

    private GridPane createMapGrid() {
        var map = this.simulationEngine.getMap();
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

                stacked.getChildren().add(new GuiElementBox(dirtImage).getImageView());
                stacked.setMaxWidth(WindowConstant.SPRITE_HEIGHT);
                stacked.setMaxHeight(WindowConstant.SPRITE_HEIGHT);
                var occupants = map.objectsAt(new Vector2d(j, i)).stream().map(e -> {
                    try {
                        return new GuiElementBox(e);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }).map(e -> e.getImageView()).toList();

                stacked.setMaxHeight(40);
                stacked.setMaxWidth(40);

                occupants.forEach(e -> stacked.getChildren().add(e));

                grid.add(stacked, j, i);
            }
        }

        return grid;
    }

    private GridPane createUserInterface() {
        return new GridPane();
    }
}
