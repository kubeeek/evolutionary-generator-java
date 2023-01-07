package agh.gui;

import agh.SimulationConfig;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class ConfigForm {
    static ArrayList<Node> fieldsxd = new ArrayList<>();

    static void createForm(GridPane container) {
        var fields = FormFieldLabelMapping.getFields();

        int row = 0;
        for (var entry : fields.entrySet()) {
            var field = entry.getValue();

            Label label = new Label(field.label);
            Node fieldNode = field.getField();
            fieldsxd.add(field.getField());

            container.add(label, 0, row);
            container.add(fieldNode, 2, row);

            row++;
        }

        container.setHgap(5);
        container.setVgap(5);
    }

    public static void fill(SimulationConfig simulationConfig) {
        var fields = FormFieldLabelMapping.getFields();
        for (var entry : fields.entrySet()) {
            var field = entry.getValue();

            field.setValue(simulationConfig.getParameter(field.key));
        }
    }
}
