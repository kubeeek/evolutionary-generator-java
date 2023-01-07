package agh;

import agh.world.IMap;

import java.util.*;

public class Animal {
    int age = 0;
    private Directions direction = Directions.getRandom();
    int energy;
    IMap map;
    int death = -1;
    private Genotype genotype;
    private int children = 0;
    private IGenePicker genePicker;
    public Vector2d position;
//    private List<IAnimalObserver> observers = new ArrayList<>();

    public Animal(IMap map, int energy, IGenePicker genePicker) {
        this.energy = energy;
        this.map = map;
        this.genotype = new Genotype();
        this.genePicker = genePicker;
        this.genePicker.setGenotype(this.genotype.getGenotype());
        Random random = new Random();
        this.position = new Vector2d(random.nextInt(this.map.getUpperRight().x + 1),
                random.nextInt(this.map.getUpperRight().y + 1));
    }

    public int getEnergy() {
        return energy;
    }

    public void decreaseEnergy(int energy) {
        this.energy -= energy;
    }

    public void increaseEnergy(int energy) {
        this.energy += energy;
    }

    public void age() {
        age++;
    }

    public void addChild() {
        children++;
    }

    public int getChildrenAmount() {
        return children;
    }

    public void move() {
        this.age += 1;
        int gene = this.genePicker.getNextGene();
        switch (gene) {
            case 1 -> this.direction = this.direction.next();
            case 2 -> this.direction = this.direction.next().next();
            case 3 -> this.direction = this.direction.next().next().next();
            case 4 -> this.direction = this.direction.next().next().next().next();
            case 5 -> this.direction = this.direction.previous().previous().previous();
            case 6 -> this.direction = this.direction.previous().previous();
            case 7 -> this.direction = this.direction.previous();
            default -> {
            }
        }

        position.add(direction.toUnitVector());
    }

    public ArrayList<Integer> getGenotype() {
        return this.genotype.getGenotype();
    }

    public Vector2d getPosition() {
        return this.position;
    }

//    private void changePosition(Vector2d oldPosition, Vector2d newPosition) {
//        for (IAnimalObserver observer : this.observers) {
//            observer.positionChanged(this, oldPosition, newPosition);
//        }
//    }


}
