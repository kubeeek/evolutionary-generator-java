package agh.world;

import agh.Animal;
import agh.Vector2d;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

public class GraveyardTracker {
    final ConcurrentHashMap<Vector2d, Integer> deathsOnMap;
    final CopyOnWriteArraySet<Vector2d> visitedPositions = new CopyOnWriteArraySet<>();

    GraveyardTracker(int mapHeight, int mapWidth) {
        this.deathsOnMap = new ConcurrentHashMap<>();

        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                this.deathsOnMap.put(new Vector2d(x, y), 0);
            }
        }
    }

    void countAnimal(Animal deadAnimal) {
        if (!deadAnimal.isDead())
            throw new IllegalArgumentException("Dead animal is not dead.");

        addGrave(deadAnimal.getPosition());
    }

    synchronized void addGrave(Vector2d position) {
        if (!deathsOnMap.containsKey(position))
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

    Vector2d getLeastGravePosition() {
        var sorted = this.getLeastGravePositions();

        var max = (sorted.size() - 1) * 2/3;

        Random random = new Random();
        var indexFromLowerHalf = random.nextInt(0, max);

        var object = sorted.get(indexFromLowerHalf);

        return object;
    }
}
