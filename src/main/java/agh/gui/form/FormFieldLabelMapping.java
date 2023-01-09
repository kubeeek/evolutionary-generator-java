package agh.gui.form;

import agh.gui.form.elements.ChoiceVariantFormField;
import agh.gui.form.elements.FormField;
import agh.gui.form.elements.TextFormField;
import agh.simulation.config.SimulationConfigVariant;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.LinkedHashMap;

/**
 * Class to store the mappingsf from keys to proper FormFields. Keys are identic with the config's ones.
 */
public class FormFieldLabelMapping {
    static LinkedHashMap<String, FormField<Object>> formFields = new LinkedHashMap<>();

    static {
        formFields.put("width", new TextFormField<String>("Szerokosc mapy", "width", new TextField("")));
        formFields.put("height", new TextFormField<String>("Wysokosc mapy", "height", new TextField("")));
        formFields.put("plants_start", new TextFormField<String>("Startowa liczba roslin", "plants_start", new TextField("")));
        formFields.put("plants_energy", new TextFormField<String>("Energia z rosliny", "plants_energy", new TextField("")));
        formFields.put("plants_daily", new TextFormField<String>("Dzienny przyrost roslin", "plants_daily", new TextField("")));
        formFields.put("plants_variant", new ChoiceVariantFormField<>("Wariant wzrostu roslin",
                "plants_variant",
                new ChoiceBox<>(),
                SimulationConfigVariant.PlantGrowth.values()
        ));
        formFields.put("animals_start", new TextFormField<String>("Startowa liczba zwierzakow", "animals_start", new TextField("")));
        formFields.put("energy_start", new TextFormField<String>("Startowa energia zwierzakow", "energy_start", new TextField("")));
        formFields.put("energy_healthy_status", new TextFormField<String>("Prog zdrowia dla energii", "energy_healthy_status", new TextField("")));
        formFields.put("energy_reproduce_loss", new TextFormField<String>("Zuzycie energii", "energy_reproduce_loss", new TextField("")));
        formFields.put("mutation_minimal", new TextFormField<String>("Maksymalna liczba mutacji", "mutation_minimal", new TextField("")));
        formFields.put("mutation_maximal", new TextFormField<String>("Minimalna liczba mutacji", "mutation_maximal", new TextField("")));
        formFields.put("mutation_variant", new ChoiceVariantFormField<>(
                "Wariant mutacji",
                "mutation_variant",
                new ChoiceBox<>(),
                SimulationConfigVariant.Mutation.values()
        ));
        formFields.put("genome_animal_length", new TextFormField<String>("Dlugosc genomu", "genome_animal_length", new TextField("")));
        formFields.put("animal_behavior_variant", new ChoiceVariantFormField<>(
                "Wariant zachowania zwierzakow",
                "animal_behavior_variant",
                new ChoiceBox<>(),
                SimulationConfigVariant.AnimalBehavior.values()
        ));
    }

    public static LinkedHashMap<String, FormField<Object>> getFields() {
        return formFields;
    }
}
