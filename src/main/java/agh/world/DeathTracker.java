package agh.world;

import agh.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DeathTracker {
    private final HashMap<Vector2d, Integer> deathsOnMap;

    DeathTracker(int mapHeight, int mapWidth) {
        this.deathsOnMap = new HashMap<>();

        for(int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                this.deathsOnMap.put(new Vector2d(x, y), 0);
            }
        }
    }

    void countAnimal(Animal deadAnimal) {
        if (!deadAnimal.isDead)
            throw new IllegalArgumentException("Dead animal is not dead.");

        addGrave(deadAnimal.getPosition());
    }

    void addGrave(Vector2d position) {
        if(!deathsOnMap.containsKey(position))
            deathsOnMap.put(position, 0);

        deathsOnMap.put(position, deathsOnMap.get(position) + 1);
    }

    ArrayList<Vector2d> getLeastGravePositions() {
     var sorted = deathsOnMap
             .entrySet()
             .stream()
             .sorted(Map.Entry.comparingByValue())
             .map(Map.Entry::getKey)
             .collect(Collectors.toList());

        return (ArrayList<Vector2d>) sorted;
    }
}
