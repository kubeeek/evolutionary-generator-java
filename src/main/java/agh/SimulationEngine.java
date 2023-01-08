package agh;

import agh.world.IMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationEngine {

    IMap map;
    int startEnergy;
    int moveEnergy;
    int plantEnergy;
    int delay;
    Random random=new Random();
    IGenePicker iGenePicker;
    ArrayList<Animal> animals = new ArrayList<>();

    public SimulationEngine(IMap map, int animalsAmount, int startEnergy, int moveEnergy, int plantEnergy, int delay,IGenePicker iGenePicker) {
        this.map = map;
        this.iGenePicker=iGenePicker;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.delay = delay;

        for (int i = 0; i < animalsAmount; i++) {
            Vector2d position =  new Vector2d(random.nextInt(this.map.getWidth()+ 1),
                    random.nextInt(this.map.getHeight()+ 1));
            Animal animal = new Animal(this.map, this.startEnergy,position,iGenePicker);
            this.map.place(animal);
            this.animals.add(animal);
        }
    }
}
