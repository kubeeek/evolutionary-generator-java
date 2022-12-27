package agh.gui;

import java.util.LinkedHashMap;

public class FormFieldLabelMapping {
    static LinkedHashMap<String, String> formFields = new LinkedHashMap<>();

    static {
        formFields.put("width", "Szerokosc mapy");
        formFields.put("height", "Wysokosc mapy");
        formFields.put("plants_start", "Startowa liczba roslin");
        formFields.put("plants_energy", "Energia z rosliny");
        formFields.put("plants_daily", "Dzienny przyrost roslin");
        formFields.put("plants_growth", "Wariant wzrostu roslin");
        formFields.put("animals_start", "Startowa liczba zwierzakow");
        formFields.put("energy_start", "Startowa energia zwierzakow");
        formFields.put("energy_healthy_status", "Prog energii (status)");
        formFields.put("energy_reproduce_loss", "Zuzycie energii");
        formFields.put("mutation_minimal", "Minimalna liczba mutacji");
        formFields.put("mutation_maximal", "Maksymalna liczba mutacji");
        formFields.put("mutation_variant", "Wariant mutacji");
        formFields.put("genome_animal_length", "Dlugosc genomu potomka");
        formFields.put("animal_behavior_variant", "Wariant zachowania zwierzaka");
    }

    static LinkedHashMap<String, String> getFields() {
        return formFields;
    }
}
