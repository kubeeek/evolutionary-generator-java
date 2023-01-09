package agh.simulation;

import agh.*;
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

    private int passedTicks = 0;
    private Animal chosenAnimal;
    private SimulationTick simulationTick;
    private ScheduledExecutorService executor;

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
            this.grassGenerator = new EquatorGrassGenerator(this.mapHeight * 1/3);

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

            animal.addObserver((IPositionChangeObserver) this.map);

            animals.add(animal);
            map.place(animal);
        }
    }

    public void start() {
        loop();
    }

    private void loop() {
        System.out.println("tick");
        try {
            this.simulationTick = new SimulationTick(animals, map, energyHealthyStatus, this.chosenAnimal);
            this.simulationTick.addObserver(this.simulationScene);
            this.executor = Executors.newSingleThreadScheduledExecutor();

            this.executor.scheduleAtFixedRate(this.simulationTick, 1500, 1500, TimeUnit.MILLISECONDS);
        } catch (Throwable e) {
            System.out.println(e);
        }

        this.passedTicks++;
    }

    public synchronized IMap getMap() {
        return this.map;
    }

    @Override
    public void animalChosen(Animal animal) {
        this.chosenAnimal = animal;
        this.executor.shutdown();

        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.simulationTick = new SimulationTick(animals, map, energyHealthyStatus, this.chosenAnimal);
        this.executor.scheduleWithFixedDelay(this.simulationTick, 500, 500, TimeUnit.MILLISECONDS);
    }
//        if(this.paused)
//            return;
//        System.out.println("tick");
//
//        SimulationTick tick = new SimulationTick(animals, map, heatlhyStatus);
//        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//
//        executorService.shutdown();
//        executorService.schedule(tick, delay, TimeUnit.SECONDS);
////        this.paused = true;
//        this.passedTicks++;
//
//        loop();
    }

    public IMap getMap() {
        return this.map;
    }

    public String getAverageEnergy(){
        int averageEnergy=0;
        for (Animal animal : this.animals) {
            averageEnergy += animal.getEnergy();
        }
        return "Average energy: " + averageEnergy/this.animals.size();
    }
    public String getAmountOfAnimals(){
        return "Animals: "+animals.size();
    }
    public String getAmountOfGrass(){
        int grassCount = 0;
        for (var entry:
                this.map.getMapObjects().entrySet()) {
            var occupants = entry.getValue();
            var occupantsList = occupants.stream().filter(e -> e instanceof Grass).toList();
            grassCount += occupantsList.size();
        }
        return "grasses: "+grassCount;
    }
    public String getAmountOfFreeSpaces(){
        int freeSpaces=map.getHeight()*map.getWidth()-map.getMapObjects().entrySet().size();
        return "free spaces: "+ freeSpaces;
    }
    public String getAverageAgeOfDeadAnimals(){
        int sumAges=0;
        for(int age: this.agesOfDeadAnimals){
            sumAges+=age;
        }
        return  "Average life: "+sumAges/this.agesOfDeadAnimals.size();
    }
    public String getMostFrequentGenotype(){
        ArrayList<ArrayList<Integer>> gens = new ArrayList<>();
        for(Animal animal: animals){
            gens.add(animal.getGenotype());
        }
        Map<List<Integer>, Integer> listCounts = new HashMap<>();
        for (List<Integer> gensList : gens) {
            int count = listCounts.getOrDefault(gensList, 0);
            listCounts.put(gensList, count + 1);
        }

        List<Integer> mostFrequentList = null;
        int maxCount = 0;
        for (Map.Entry<List<Integer>, Integer> entry : listCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostFrequentList = entry.getKey();
                maxCount = entry.getValue();
            }
        }
        return "Most frequent genotype: " + mostFrequentList;
    }
}
