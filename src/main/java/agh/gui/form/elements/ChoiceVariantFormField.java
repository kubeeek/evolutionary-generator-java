package agh.gui.form.elements;

import agh.gui.EnumStringParser;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

public class ChoiceVariantFormField<T extends Enum<T>> extends FormField {
    private final ChoiceBox<T> formField;

    public ChoiceVariantFormField(String label, String key, ChoiceBox<T> formField, T[] values) {
        super(label, key);
        this.formField = formField;

        formField.setItems(FXCollections.observableArrayList(values));
        formField.setValue(values[0]);
    }

    @Override
    public Node getField() {
        return this.formField;
    }

    @Override
    public void setValue(String value) {
        var parser = new EnumStringParser(value);

        this.formField.setValue((T) parser.getValue());
    }

    @Override
    public String getValue() {
        return (String) new EnumStringParser(this.formField.getValue().toString()).getValue();
    }
}

