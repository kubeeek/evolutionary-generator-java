package agh.simulation;

import agh.*;
import agh.gui.CSVHandler;
import agh.gui.EnumStringParser;
import agh.gui.SimulationScene;
import agh.simulation.config.SimulationConfig;
import agh.simulation.config.SimulationConfigVariant;
import agh.world.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimulationEngine implements IAnimalChosenListener {

    private final SimulationConfig simulationConfig;

    private final SimulationScene simulationScene;
    public CSVHandler filehandler;
    IMap map;
    int moveEnergy;
    int delay = 1;
    Random random = new Random();
    CopyOnWriteArrayList<Animal> animals = new CopyOnWriteArrayList<>();

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
    private String filename;

    private int passedTicks = 0;
    private Animal chosenAnimal;
    private SimulationTick simulationTick;
    private ScheduledExecutorService executor;
    private ArrayList<Integer> deads = new ArrayList<>();


    public SimulationEngine(SimulationConfig simulationConfig, SimulationScene app) {
        this.simulationConfig = simulationConfig;
        this.setup();
        this.simulationScene = app;
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
            this.grassGenerator = new EquatorGrassGenerator(this.mapHeight * 1 / 3);

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
        this.filename = this.simulationConfig.getParameter("filename");

        this.filehandler=new CSVHandler(filename);
    }
    public CSVHandler getFilehandler(){
        return this.filehandler;
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

            animal.addObserver((IPositionChangeObserver) this.map);

            animals.add(animal);
            map.place(animal);
        }
    }

    public void buttonClicked() {
        if(this.executor == null || this.executor.isShutdown())
            this.start();
        else
            this.stop();
    }

    private void start() {
        System.out.println("tick");
        try {
            this.simulationTick = new SimulationTick(animals, map, energyHealthyStatus, this.chosenAnimal);
            this.simulationTick.addObserver(this.simulationScene);
            this.simulationTick.setEngine(this);
            this.executor = Executors.newSingleThreadScheduledExecutor();

            this.executor.scheduleAtFixedRate(this.simulationTick, 500, 500, TimeUnit.MILLISECONDS);

        } catch (Throwable e) {
            System.out.println(e);
        }

        this.passedTicks++;
    }

    @Override
    public void animalChosen(Animal animal) {
        this.chosenAnimal = animal;
        this.executor.shutdown();

        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.simulationTick = new SimulationTick(animals, map, energyHealthyStatus, this.chosenAnimal);
        this.simulationTick.setEngine(this);
        this.simulationTick.addObserver(this.simulationScene);
        this.executor.scheduleWithFixedDelay(this.simulationTick, 500, 500, TimeUnit.MILLISECONDS);
    }


    public IMap getMap() {
        return this.map;
    }

    public void stop() {
        if(this.executor != null)
            this.executor.shutdown();
    }

    public void addDead(Animal dead) {

        this.deads.add(dead.getAge());
    }

    public SimulationStats getStats() {
        ArrayList<Animal> animalArrayList = new ArrayList<>(this.animals);

        return new SimulationStats(this.map, animalArrayList, this.deads);
    }

}
