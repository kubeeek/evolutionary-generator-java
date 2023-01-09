package agh.simulation;

import agh.Animal;
import agh.world.IMap;

import java.util.ArrayList;

public class SimulationTick implements Runnable {

    private final ArrayList<Animal> animals;
    private final IMap map;
    private final int healthyStatus;

    SimulationTick(ArrayList<Animal> animals, IMap map, int healthyStatus) {
        this.animals = animals;
        this.map = map;
        this.healthyStatus = healthyStatus;
    }

    /**
     *
     */
    @Override
    public void run() {
        System.out.println("ticked");

        // for each simulation entity which is animal:
        for (var animal :
                animals) {
            // remove them if dead
            if (animal.isDead()) {
                this.map.deleteAt(animal.getPosition(), animal);
                this.animals.remove(animal);

                continue;
            }

            // rotate them & make a move
            animal.move();

        }

        // eat
        (new DinnerConflictResolver(map, animals)).resolve();

        // make children
        (new ChildMakingResolver(map, animals, healthyStatus)).resolve();

        // grow new plants
        this.map.populateGrass();
    }
}
