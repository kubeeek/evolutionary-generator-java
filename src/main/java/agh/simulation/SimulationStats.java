package agh.simulation;

import agh.Animal;
import agh.Grass;
import agh.world.IMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimulationStats {

    private final ArrayList<Animal> animals;
    private final IMap map;
    List<Integer> agesOfDeadAnimals;

    SimulationStats(IMap map, ArrayList<Animal> animals, ArrayList<Integer> dead) {
        this.map = map;
        this.animals = animals;
        this.agesOfDeadAnimals = dead;
    }

    public String getAverageEnergy() {
        int averageEnergy = 0;
        if (this.animals.size() != 0) {
            for (Animal animal : this.animals) {
                averageEnergy += animal.getEnergy();
            }
            averageEnergy = averageEnergy / this.animals.size();
        }
        return "" + averageEnergy;
    }

    public String getAmountOfAnimals() {
        return "" + animals.size();
    }

    public String getAmountOfGrass() {
        int grassCount = 0;

        var entries = this.map.getMapObjects().entrySet();
        for (var entry : entries) {
            var occupants = entry.getValue();
            var isThereGrass = occupants.stream().anyMatch(e -> e instanceof Grass);
            if (isThereGrass)
                grassCount += 1;

        }

        return "" + grassCount;
    }

    public String getAmountOfFreeSpaces() {
        var filteredTakenPositions = map.getMapObjects()
                .entrySet()
                .stream()
                .filter(e -> e.getValue().size() > 0)
                .collect(Collectors.toList());

        int freeSpaces = map.getHeight() * map.getWidth() - filteredTakenPositions.size();

        return ""+freeSpaces;
    }

    public String getAverageAgeOfDeadAnimals() {
        if (agesOfDeadAnimals.size() == 0)
            return "" + 0;

        int totalAge = 0;
        for (var age : agesOfDeadAnimals) {
            totalAge += age;
        }

        int averageAge = totalAge / agesOfDeadAnimals.size();

        return "" + averageAge;
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
        return "" + mostFrequentList;
    }
}
