package agh.gui;

import javafx.scene.Node;
import javafx.scene.control.TextField;

public abstract class FormField implements IFormField {
    String label;
    String key;

    public FormField(String label, String key) {
        this.key = key;
        this.label = label;
    }
}
