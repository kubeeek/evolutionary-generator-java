package agh.simulation;

import agh.*;
import agh.gui.EnumStringParser;
import agh.simulation.config.SimulationConfig;
import agh.simulation.config.SimulationConfigVariant;
import agh.world.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {

    private final SimulationConfig simulationConfig;

    IMap map;
    int moveEnergy;
    int delay = 1;
    Random random = new Random();
    ArrayList<Animal> animals = new ArrayList<>();

    private int mapHeight;
    private int mapWidth;

    private IGrassGenerator grassGenerator;
    private SimulationConfigVariant.Mutation mutationVariant;
    private SimulationConfigVariant.AnimalBehavior genePickerVariant;
    private SimulationConfigVariant.MapLoop mapLoopVariant;

    private int dailyPlantCount;
    private int startPlantCount;
    private int energyHealthyStatus;
    private int energyReproduceLoss;
    private int animalStartingCount;
    private int mutationMinimal;
    private int mutationMaximal;
    private int genomeAnimalLength;
    private int startEnergy;
    private int plantEnergy;
    private boolean paused = false;

    private int passedTicks = 0;

    public SimulationEngine(SimulationConfig simulationConfig) {
        this.simulationConfig = simulationConfig;
        this.setup();
    }

    public void setup() {
        this.setupConfigValues();
        this.setupAnimals();
    }

    private void setupConfigValues() {
        this.mapHeight = Integer.parseInt(this.simulationConfig.getParameter("height"));
        this.mapWidth = Integer.parseInt(this.simulationConfig.getParameter("width"));
        this.startPlantCount = Integer.parseInt(this.simulationConfig.getParameter("plants_start"));
        this.dailyPlantCount = Integer.parseInt(this.simulationConfig.getParameter("plants_daily"));
        this.plantEnergy = Integer.parseInt(this.simulationConfig.getParameter("plants_energy"));

        EnumStringParser parser = new EnumStringParser(this.simulationConfig.getParameter("plants_variant"));
        if (SimulationConfigVariant.PlantGrowth.EQUATOR == parser.getValue())
            this.grassGenerator = new EquatorGrassGenerator(3);
        else
            this.grassGenerator = new ToxicGravesGrassGenerator();

        this.mutationVariant = (SimulationConfigVariant.Mutation)
                new EnumStringParser(this.simulationConfig.getParameter("mutation_variant")
                ).getValue();
        this.genePickerVariant = (SimulationConfigVariant.AnimalBehavior)
                new EnumStringParser(this.simulationConfig.getParameter("animal_behavior_variant")
                ).getValue();

        parser = new EnumStringParser(this.simulationConfig.getParameter("map_loop"));
        if (SimulationConfigVariant.MapLoop.GLOBE == parser.getValue())
            this.map = new GlobeMap(this.mapWidth, this.mapHeight, this.startPlantCount, this.dailyPlantCount, this.grassGenerator);
        else
            this.map = new HellMap(this.mapWidth, this.mapHeight, this.startPlantCount, this.dailyPlantCount, this.grassGenerator);

        this.animalStartingCount = Integer.parseInt(this.simulationConfig.getParameter("animals_start"));
        this.startEnergy = Integer.parseInt(this.simulationConfig.getParameter("energy_start"));
        this.energyHealthyStatus = Integer.parseInt(this.simulationConfig.getParameter("energy_healthy_status"));
        this.energyReproduceLoss = Integer.parseInt(this.simulationConfig.getParameter("energy_reproduce_loss"));
        this.mutationMinimal = Integer.parseInt(this.simulationConfig.getParameter("mutation_minimal"));
        this.mutationMaximal = Integer.parseInt(this.simulationConfig.getParameter("mutation_maximal"));
        this.genomeAnimalLength = Integer.parseInt(this.simulationConfig.getParameter("genome_animal_length"));
    }

    private void setupAnimals() {
        for (int i = 0; i < this.animalStartingCount; i++) {
            Vector2d position;
            IGenePicker genePicker;
            Animal animal;

            if (genePickerVariant == SimulationConfigVariant.AnimalBehavior.LITTLE_CRAZY) {
                genePicker = new RandomGenPicker();
            } else
                genePicker = new DeterministicGenePicker();

            position = map.getRandomPosition();
            animal = new Animal(
                    this.map,
                    this.startEnergy,
                    position,
                    genePicker,
                    this.genomeAnimalLength,
                    this.plantEnergy,
                    this.energyReproduceLoss,
                    this.mutationMinimal,
                    this.mutationMaximal,
                    this.energyHealthyStatus,
                    this.mutationVariant
            );

            animals.add(animal);
            map.place(animal);
        }
    }

    public void start() {
        this.paused = false;

        loop();
    }

    private void loop() {
////        if(this.paused)
////            return;
//        System.out.println("tick");
//
//        SimulationTick tick = new SimulationTick(animals, map);
//        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//
//        executorService.shutdown();
//        executorService.schedule(tick, delay, TimeUnit.SECONDS);
////        this.paused = true;
//        this.passedTicks++;
//
//        loop();
    }
}
