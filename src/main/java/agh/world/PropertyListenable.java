package agh.world;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PropertyListenable {
    PropertyChangeSupport support;

    public PropertyListenable() {
        PropertyChangeSupport support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        support.removePropertyChangeListener(l);
    }
}
