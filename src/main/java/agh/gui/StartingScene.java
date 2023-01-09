package agh.gui;

import agh.ConfigReader;
import agh.simulation.config.SimulationConfig;
import agh.gui.form.ConfigForm;
import agh.world.IMap;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.IOException;

public class StartingScene {

    private final Scene scene;
    private final Stage primaryStage;
    private SimulationConfig simulationConfig = null;

    public StartingScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        ConfigReader defaultConfigReader = new ConfigReader("default.properties");

        try {
            var defaultProp = defaultConfigReader.read();
            simulationConfig = new SimulationConfig(defaultProp);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        GridPane userInterface = createUserSelectInterface();
        Node form = createConfigFormInterface();
        VBox startSimulation = getStartSimulationButton();


        this.scene = new Scene(new VBox(userInterface, form, startSimulation));
        primaryStage.setMinWidth(WindowConstant.FORM_WIDTH);
        primaryStage.setMinHeight(WindowConstant.FORM_HEIGHT);

    }

    private GridPane createUserSelectInterface() {
        Text inputField = new Text("Mozesz wczytac swoja konfiguracje ze wskazanego pliku. Jezeli pozostawisz pole puste, " + "symutlacja uruchomi sie z domyslna konfiguracja");

        Button configFileButton = new Button("Chce wczytac swoja konfiguracje");
        final FileChooser fileChooser = new FileChooser();

        configFileButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(this.primaryStage);
            try {
                load(selectedFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        GridPane userInterface = new GridPane();
        userInterface.add(inputField, 0, 0);
        userInterface.add(configFileButton, 0, 1);
        GridPane.setHalignment(configFileButton, HPos.CENTER);
        userInterface.setPadding(new Insets(10));
        userInterface.setVgap(10);
        userInterface.setAlignment(Pos.CENTER);
        return userInterface;
    }
    private VBox getStartSimulationButton() {
        startButton.setOnAction(click -> {
            ConfigForm.fillConfig(this.simulationConfig);
            new SimulationScene(this.simulationConfig);

        });
        VBox vbox = new VBox(startButton);
        vbox.setAlignment(Pos.CENTER);
        startButton.setAlignment(Pos.CENTER);

        return vbox;
    }   Button startButton = new Button("Start Simulation");

    ////////////////////// Stworzenie przycisku i otwieranie sie nowych okien
    private void openNewStageWithGrid() {
        final int SIZE = 30;
        final int WIDTH = 400;
        final int HEIGHT = 400;

        GridPane grid = new GridPane();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Rectangle rect = new Rectangle(SIZE, SIZE);
                rect.setStrokeWidth(1);
                rect.setStroke(javafx.scene.paint.Color.YELLOW);
                grid.add(rect, col, row);
            }
        }

        Stage stage = new Stage();
        stage.setTitle("New Stage with Grid");
        stage.setScene(new Scene(grid, WIDTH, HEIGHT));
        stage.show();
    }
    //////////////////////

    private Node createConfigFormInterface() {
        GridPane container = new GridPane();

        ConfigForm.createForm(container);
        ConfigForm.fillForm(this.simulationConfig);
        container.setPadding(new Insets(10));
        container.setAlignment(Pos.CENTER);
        container.setHgap(10);
        return container;
    }


    private void load(File file) throws IOException {
        ConfigReader configReader = new ConfigReader(file);

        var userProp = configReader.read();
        this.simulationConfig.setUserConfig(userProp);

        ConfigForm.fillForm(this.simulationConfig);
    }

    public void setActive() {
        primaryStage.setScene(this.scene);
        primaryStage.show();
    }
}
