package agh.simulation;

import agh.Animal;
import agh.Grass;
import agh.world.IMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationStats {

    private final ArrayList<Animal> animals;
    private final IMap map;
    List<Integer> agesOfDeadAnimals = new ArrayList<>();

    SimulationStats(IMap map, ArrayList<Animal> animals) {
        this.map = map;
        this.animals = animals;

    }

    public String getAverageEnergy() {
        int averageEnergy = 0;
        for (Animal animal : this.animals) {
            averageEnergy += animal.getEnergy();
        }
        return "Average energy: " + averageEnergy / this.animals.size();
    }

    public String getAmountOfAnimals() {
        return "Animals: " + animals.size();
    }

    public String getAmountOfGrass() {
        int grassCount = 0;
        for (var entry :
                this.map.getMapObjects().entrySet()) {
            var occupants = entry.getValue();
            var occupantsList = occupants.stream().filter(e -> e instanceof Grass).toList();
            grassCount += occupantsList.size();
        }
        return "grasses: " + grassCount;
    }

    public String getAmountOfFreeSpaces() {
        int freeSpaces = map.getHeight() * map.getWidth() - map.getMapObjects().entrySet().size();
        return "free spaces: " + freeSpaces;
    }

    public String getAverageAgeOfDeadAnimals() {
        int sumAges = 0;
        for (int age : this.agesOfDeadAnimals) {
            sumAges += age;
        }
        return "Average life: " + sumAges / this.agesOfDeadAnimals.size();
    }

    public String getMostFrequentGenotype() {
        ArrayList<ArrayList<Integer>> gens = new ArrayList<>();
        for (Animal animal : animals) {
            gens.add(animal.getGenotype());
        }
        HashMap<List<Integer>, Integer> listCounts = new HashMap<>();
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
