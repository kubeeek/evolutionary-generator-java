package agh.gui.form.elements;

import javafx.scene.Node;
import javafx.scene.control.TextField;

public class TextFormField extends FormField {
    TextField formField;

    public TextFormField(String label, String key, TextField formField) {
        super(label, key);
        this.formField = formField;
    }

    public void setValue(String val) {
        this.formField.setText(val);
    }

    @Override
    public Node getField() {
        return this.formField;
    }

    @Override
    public String getValue() {
        return this.formField.getText();
    }
}
