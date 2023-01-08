package agh.gui.form;

import agh.simulation.config.SimulationConfig;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

/**
 *
 */
public class ConfigForm {
    static ArrayList<Node> fieldsxd = new ArrayList<>();

    public static void createForm(GridPane container) {
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

    public static void fillForm(SimulationConfig simulationConfig) {
        var fields = FormFieldLabelMapping.getFields();
        for (var entry : fields.entrySet()) {
            var field = entry.getValue();

            field.setValue(simulationConfig.getParameter(field.key));
        }
    }

    public static void fillConfig(SimulationConfig simulationConfig) {
        var fields = FormFieldLabelMapping.getFields();

        for (var entry : fields.entrySet()) {
            var field = entry.getValue();

            simulationConfig.setParameter(field.key, field.getValue());
        }
    }
}
