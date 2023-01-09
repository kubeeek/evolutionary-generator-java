package agh.simulation;

import agh.Animal;
import agh.Grass;
import agh.world.IMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class DinnerConflictResolver {

    private final IMap map;
    private final CopyOnWriteArrayList<Animal> animals;

    public DinnerConflictResolver(IMap map, CopyOnWriteArrayList<Animal> animals) {

        this.map = map;
        this.animals = animals;
    }

    public void resolve() {
        for (var animal : animals) {
            var neighbors = this.map.objectsAt(animal.getPosition());
            var grass = neighbors.stream().filter(e -> e instanceof Grass).toList();

            if (!grass.isEmpty()) {
                var conflictParticipants = neighbors.stream().filter(e -> e instanceof Animal).map(e -> (Animal) e).collect(Collectors.toList());
                resolveConflict(conflictParticipants, (Grass) grass.get(0));
            }
        }
    }

    private void resolveConflict(List<Animal> conflictParticipants, Grass grass) {
        var winner = conflictParticipants.stream().sorted(Collections.reverseOrder()).toList().get(0);

        winner.eat(grass);
    }
}
