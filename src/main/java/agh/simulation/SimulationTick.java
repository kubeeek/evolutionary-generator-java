package agh.simulation;

import agh.Animal;
import agh.world.IMap;

import java.util.concurrent.CopyOnWriteArrayList;

public class SimulationTick {

    final IMap map;
    private final int healthyStatus;
    private final Animal chosenAnimal;
    CopyOnWriteArrayList<Animal> animals;
    private CopyOnWriteArrayList<ISimulationChange> observers = new CopyOnWriteArrayList<>();

    SimulationTick(CopyOnWriteArrayList<Animal> animals, IMap map, int healthyStatus, Animal chosenAnimal) {
        this.animals = animals;
        this.map = map;
        this.healthyStatus = healthyStatus;
        this.chosenAnimal = chosenAnimal;
    }

    public void notifyObservers() {
        for (var observer :
                observers) {
            observer.simulationChanged(this.map);
        }
    }

    /**
     *
     */

    public void run() {
        System.out.println("ticked");

        if (animals.size() > 0) {
            for (var animal :
                    animals) {
                //System.out.println(animal);
                // skip them if dead
                if (animal.isDead()) {
                    this.map.deleteAt(animal.getPosition(), animal);
                    animals.remove(animal);

                    System.out.println("zdechł");
                    continue;
                } else {
                    // rotate them & make a move
                    animal.move();
                    //System.out.println(animal);

                }
            }
        }

        // eat
        (new DinnerConflictResolver(map, animals)).resolve();

        // make children
        (new ChildMakingResolver(map, animals, healthyStatus)).resolve();

        // grow new plants
        this.map.populateGrass();
        notifyObservers();
        System.out.println("------------------");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void addObserver(ISimulationChange observer) {
        this.observers.add(observer);
    }
}
