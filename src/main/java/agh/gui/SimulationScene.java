package agh.gui;

import agh.Animal;
import agh.Vector2d;
import agh.simulation.ISimulationChange;
import agh.simulation.SimulationEngine;
import agh.simulation.config.SimulationConfig;
import agh.world.IMap;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimulationScene implements ISimulationChange {
    private final Stage stage;
    private final SimulationConfig simulationConfig;
    private final HashMap<StackPane, List<GuiElementBox>> mapping = new HashMap<>();
    private SimulationEngine simulationEngine;
    private Animal chosenAnimal = null;

    public SimulationScene(SimulationConfig simulationConfig) {
        this.simulationConfig = simulationConfig;
        this.setup();
        this.stage = new Stage();
        this.stage.setTitle("GameOfLife_Simulation");

        this.stage.setMinWidth(WindowConstant.SIM_WIDTH);
        this.stage.setMinHeight(WindowConstant.SIM_HEIGHT);

        GridPane userInterface = this.createUserInterface(null);
        var map = this.simulationEngine.getMap();
        GridPane mapGrid = this.createMapGrid(map);

        this.stage.setScene(new Scene(new HBox(userInterface, mapGrid)));
        this.stage.show();
        this.start();
    }


    private void start() {
        this.simulationEngine.buttonClicked();

        this.stage.setOnHiding((event) -> this.simulationEngine.stop());
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
                }).collect(Collectors.toList());

                var anyAnimal = occupants.stream().filter(e -> e.mapObject instanceof Animal || e.mapObject == this.chosenAnimal).findFirst();

                mapping.put(stacked, occupants);
                stacked.setMaxHeight(40);
                stacked.setMaxWidth(40);

                stacked.setUserData(anyAnimal);

                stacked.setOnMouseClicked(event -> {
                    System.out.println("clicked");
                    var src = event.getSource();
                    if (src instanceof StackPane) {
                        var casted = (StackPane) src;
                        var objectsAtClick = (Optional<GuiElementBox>) casted.getUserData();

                        System.out.println(objectsAtClick.isPresent());
                        if (objectsAtClick.isPresent()) {
                            var val = objectsAtClick.get();
                            this.simulationEngine.animalChosen((Animal) val.mapObject);
                        }
                    }
                });

                anyAnimal.ifPresent(occupants::remove);

                occupants.forEach(e -> stacked.getChildren().add(e.getImageView()));

                // last added imageview is at front layer
                anyAnimal.ifPresent(guiElementBox -> stacked.getChildren().add(guiElementBox.getImageView()));
                if (anyAnimal.isPresent()) {
                    var energyLabel = new Rectangle(WindowConstant.SPRITE_WIDTH, WindowConstant.SPRITE_HEIGHT * 1 / 5);
                    energyLabel.setFill(((Animal) anyAnimal.get().mapObject).getLabelColor());
                    stacked.getChildren().add(energyLabel);
                    stacked.setAlignment(Pos.BOTTOM_CENTER);

                    if (anyAnimal.get().mapObject == this.chosenAnimal) {
                        var mark = new Rectangle(WindowConstant.SPRITE_WIDTH * 1 / 5, WindowConstant.SPRITE_HEIGHT*1/10);
                        mark.setFill(Color.FLORALWHITE);
                        stacked.getChildren().add(mark);
                    }
                }

                grid.add(stacked, j, i);
            }
        }

        return grid;
    }

    private GridPane createUserInterface(Animal chosenAnimal) {
        var container = new GridPane();
        container.setPadding(new Insets(20));
        Button pauseButton = new Button("Pause");

        pauseButton.setOnAction(click -> {
            this.simulationEngine.buttonClicked();

        });

        var stats = simulationEngine.getStats();
        container.add(pauseButton, 0, 0);
        container.add(new Label("Animals: " + stats.getAmountOfAnimals()), 0, 1);
        container.add(new Label("free spaces: " + stats.getAmountOfFreeSpaces()), 0, 2);
        container.add(new Label("grasses: "+ stats.getAmountOfGrass()), 0, 3);
        container.add(new Label("Average energy: " + stats.getAverageEnergy()), 0, 4);
        container.add(new Label("Average dead age: "+ stats.getAverageAgeOfDeadAnimals()), 0, 5);
        container.add(new Label("Most frequent genotype: "+ stats.getMostFrequentGenotype()), 0, 6);

        if (chosenAnimal != null) {
            this.chosenAnimal = chosenAnimal;
            container.add(new Label("Chosen animal:"), 0, 7);
            container.add(new Label("Genotype: " + chosenAnimal.getGenotype().toString()), 0, 8);
            container.add(new Label("Current gene index: " + chosenAnimal.currentActiveGene()), 0, 9);
            container.add(new Label("Energy: " + chosenAnimal.getEnergy()), 0, 10);
            container.add(new Label("Grass eaten: " + chosenAnimal.getCountEatenGrass()), 0, 11);
            container.add(new Label("Children amount: " + chosenAnimal.getChildrenAmount()), 0, 12);
            if(chosenAnimal.isDead()) {
                container.add(new Label("Died at: " + chosenAnimal.getAge()), 0, 13);
            }
        }

        return container;
    }
    public void saveToFile(){
        var stats = simulationEngine.getStats();
        CSVHandler file = simulationEngine.getFilehandler();
        if (file.name!=null) {
            file.saveStatisticsToFile(stats.getAmountOfAnimals(), stats.getAmountOfGrass(), stats.getAmountOfFreeSpaces(), stats.getAverageEnergy(),
                    stats.getAverageAgeOfDeadAnimals(), stats.getMostFrequentGenotype());
        }
    }

    @Override
    public synchronized void simulationChanged(IMap map, Animal chosenAnimal) {
        Platform.runLater(() -> {
            GridPane userInterface = createUserInterface(chosenAnimal);
            GridPane mapGrid = this.createMapGrid(map);

            stage.setScene(new Scene(new HBox(userInterface, mapGrid)));
            stage.show();
        });

        if(this.simulationConfig.getParameter("filename").length() > 0);
            saveToFile();

        System.out.println("notified");
    }

}
