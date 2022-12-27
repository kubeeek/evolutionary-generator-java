package agh.gui;

import agh.ConfigReader;
import agh.SimulationConfig;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StartingScene {

    private final Scene scene;
    private final Stage primaryStage;
    private final ArrayList<TextField> inputs = new ArrayList<>();


    public StartingScene(Stage primaryStage) {
        this.primaryStage = primaryStage;

        VBox userInterface = createUserSelectInterface();
        Node form = createConfigFormInterface();

        this.scene = new Scene(new VBox(userInterface, form));

    }

    private VBox createUserSelectInterface() {
        Text inputField = new Text("Mozesz wczytac swoja konfiguracje ze wskazanego pliku. Jezeli pozostawisz pole puste, " + "symutlacja uruchomi sie z domyslna konfiguracja");

        Button configFileButton = new Button("Chce wczytac swoja konfiguracje");
        final FileChooser fileChooser = new FileChooser();

        configFileButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(this.primaryStage);
            try {
                if(selectedFile != null) load(selectedFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        VBox userInterface = new VBox(inputField, configFileButton);
        userInterface.setSpacing(10);
        userInterface.setPadding(new Insets(10));
        userInterface.setAlignment(Pos.CENTER);
        return userInterface;
    }

    private Node createConfigFormInterface() {
        var fields = FormFieldLabelMapping.getFields();
        GridPane container = new GridPane();

        int rowIndex = 0;
        for (var entry : fields.entrySet()) {
            Label fieldLabel = new Label(entry.getValue());
            TextField inputField = new TextField("");

            inputField.setId(entry.getKey());

            this.inputs.add(inputField);
            container.add(fieldLabel, 0, rowIndex);
            container.add(inputField, 2, rowIndex++);
        }


        container.setPadding(new Insets(10));
        container.setAlignment(Pos.CENTER);
        container.setHgap(10);
        return container;
    }

    private void load(File file) throws IOException {
        ConfigReader configReader = new ConfigReader(file);

        var prop = configReader.read();
        var simulationConfig = new SimulationConfig(prop);

        for (var element : inputs) {
            System.out.print(element.getId() + " | ");
            System.out.println(simulationConfig.getParameter(element.getId()));
            element.setText(simulationConfig.getParameter(element.getId()));
        }
    }

    public void setActive() {
        primaryStage.setScene(this.scene);
        primaryStage.show();
    }
}
