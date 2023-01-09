package agh.gui.form.elements;

import javafx.scene.Node;

/**
 * Interface for embedding the JavaFX's form elements
 */
public interface IFormField<T> {

    Node getField();
    void setValue(String value);
    T getValue();
}
