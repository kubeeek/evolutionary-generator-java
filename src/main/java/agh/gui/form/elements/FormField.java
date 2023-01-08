package agh.gui.form.elements;

/**
 * Class for managing the FormField. It wraps the JavaFX's element in order to provide convenient way for storing the label
 * and (id-value) relation
 */
public abstract class FormField<T> implements IFormField<T> {
    public String label;
    public String key;

    /**
     * @param label Visible label in the form
     * @param key   Key for the field/value, it needs be the same as in SimulationConfig
     */
    public FormField(String label, String key) {
        this.key = key;
        this.label = label;
    }
}
