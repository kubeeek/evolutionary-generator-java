package agh.simulation;

import agh.Animal;
import agh.world.IMap;

public interface ISimulationChange {

    void simulationChanged(IMap map, Animal chosenAnimal);
}
