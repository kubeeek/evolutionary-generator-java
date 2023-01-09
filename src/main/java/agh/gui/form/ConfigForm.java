package agh.gui.form;

import agh.simulation.config.SimulationConfig;
import agh.simulation.config.SimulationConfigVariant;
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

            var value = field.getValue();

            // ugly code :(
            if (value instanceof SimulationConfigVariant.AnimalBehavior)
                simulationConfig.setParameter(field.key, ((SimulationConfigVariant.AnimalBehavior) field.getValue()).name());
            else if (value instanceof SimulationConfigVariant.PlantGrowth)
                simulationConfig.setParameter(field.key, ((SimulationConfigVariant.PlantGrowth) field.getValue()).name());
            else if (value instanceof SimulationConfigVariant.Mutation)
                simulationConfig.setParameter(field.key, ((SimulationConfigVariant.Mutation) field.getValue()).name());
            else if (value instanceof SimulationConfigVariant.MapLoop)
                simulationConfig.setParameter(field.key, ((SimulationConfigVariant.MapLoop) field.getValue()).name());
            else
                simulationConfig.setParameter(field.key, (String) field.getValue());
        }
    }
}
