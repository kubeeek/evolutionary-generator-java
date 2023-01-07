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
    static LinkedHashMap<String, FormField> formFields = new LinkedHashMap<>();

    static {
        formFields.put("width", new TextFormField("Szerokosc mapy", "width", new TextField("")));
        formFields.put("height", new TextFormField("Wysokosc mapy", "height", new TextField("")));
        formFields.put("plants_start", new TextFormField("Startowa liczba roslin", "plants_start", new TextField("")));
        formFields.put("plants_energy", new TextFormField("Energia z rosliny", "plants_energy", new TextField("")));
        formFields.put("plants_daily", new TextFormField("Dzienny przyrost roslin", "plants_daily", new TextField("")));
        formFields.put("plants_variant", new ChoiceVariantFormField<>("Wariant wzrostu roslin",
                "plants_variant",
                new ChoiceBox<>(),
                SimulationConfigVariant.PlantGrowth.values()
        ));
        formFields.put("animals_start", new TextFormField("Startowa liczba zwierzakow", "animals_start", new TextField("")));
        formFields.put("energy_start", new TextFormField("Startowa energia zwierzakow", "energy_start", new TextField("")));
        formFields.put("energy_healthy_status", new TextFormField("Prog zdrowia dla energii", "energy_healthy_status", new TextField("")));
        formFields.put("energy_reproduce_loss", new TextFormField("Zuzycie energii", "energy_reproduce_loss", new TextField("")));
        formFields.put("mutation_minimal", new TextFormField("Maksymalna liczba mutacji", "mutation_minimal", new TextField("")));
        formFields.put("mutation_maximal", new TextFormField("Minimalna liczba mutacji", "mutation_maximal", new TextField("")));
        formFields.put("mutation_variant", new ChoiceVariantFormField<>(
                "Wariant mutacji",
                "mutation_variant",
                new ChoiceBox<>(),
                SimulationConfigVariant.Mutation.values()
        ));
        formFields.put("genome_animal_length", new TextFormField("Dlugosc genomu", "genome_animal_length", new TextField("")));
        formFields.put("animal_behavior_variant", new ChoiceVariantFormField<>(
                "Wariant zachowania zwierzakow",
                "animal_behavior_variant",
                new ChoiceBox<>(),
                SimulationConfigVariant.AnimalBehavior.values()
        ));
    }

    public static LinkedHashMap<String, FormField> getFields() {
        return formFields;
    }
}
