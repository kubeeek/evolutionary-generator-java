package agh;

import agh.simulation.config.SimulationConfigVariant;
import agh.world.IMap;

import java.util.*;

public class Animal extends AbstractGameObject implements Comparable<Animal> {
    int age = 0;
    int countEatenGrass = 0;
    private Directions direction = Directions.getRandom();
    private final ArrayList<IPositionChangeObserver> observers = new ArrayList<>();
    IMap map;
    int energyUsedToReproduce;
    int energyUsedToMove = 1;
    int energyGainedFromEating;
    int mutationMax;
    int mutationMin;
    int deathDate = -1;
    int energyNeededToBorn;
    SimulationConfigVariant.Mutation mutationType;
    private final Genotype genotype;
    private int children = 0;
    private final IGenePicker genePicker;

    //Animal przy inicjacji
    public Animal(
            IMap map,
            int energy,
            Vector2d position,
            IGenePicker genePicker,
            int lengthOfGenotype,
            int energyGainedFromEating,
            int energyUsedToReproduce,
            int mutationMin,
            int mutationMax,
            int energyNeededToBorn,
            SimulationConfigVariant.Mutation mutationType
    ) {
        this.energy = energy;
        this.energyUsedToReproduce = energyUsedToReproduce;
        this.energyGainedFromEating = energyGainedFromEating;
        this.energyNeededToBorn=energyNeededToBorn;


        this.genotype = new Genotype(lengthOfGenotype);
        this.genePicker = genePicker;
        this.genePicker.setGenotype(this.genotype.getGenotype());

        this.mutationMin = mutationMin;
        this.mutationMax = mutationMax;
        this.mutationType = mutationType;

        this.position = position;
        this.map = map;
    }

    //Animal Dziecko
    public Animal(Animal mom, Animal dad) {
        //Energia
        this.energyGainedFromEating = dad.energyGainedFromEating;
        this.energyUsedToReproduce = dad.energyUsedToReproduce;
        this.energyNeededToBorn=dad.energyNeededToBorn;
        this.energy = getEnergyFromParents(dad, mom);
        //Mutacja
        this.mutationMin = dad.mutationMin;
        this.mutationMax = dad.mutationMax;
        this.mutationType = dad.mutationType;
        mutateGene();
        //Genotyp
        this.genotype = new Genotype(mom, dad);
        this.genePicker = dad.genePicker;
        this.genePicker.setGenotype(this.genotype.getGenotype());
        this.genePicker.setRandomCurrentIndex();
        //Polozenie
        this.map = dad.map;
        this.position = dad.getPosition();

        dad.addChild();
        mom.addChild();
    }

    //Animal - dodanie observera
    public Animal(
            IMap map,
            int startEnergy,
            Vector2d position,
            IGenePicker genePicker,
            int lengthOfGenotype,
            int energyGainedFromEating,
            int energyUsedToReproduce,
            int mutationMin,
            int mutationMax,
            int energyNeededToBorn,
            SimulationConfigVariant.Mutation mutationType,
            IPositionChangeObserver observer
            ) {
        this(map, startEnergy, position, genePicker, lengthOfGenotype, energyGainedFromEating
                , energyUsedToReproduce, mutationMin, mutationMax,energyNeededToBorn, mutationType);
        addObserver(observer);
    }

    public void mutateGene() {
        if (this.mutationType == SimulationConfigVariant.Mutation.CORRECTED) {
            this.genotype.mutatePlusOne(this.mutationMin, this.mutationMax);
        } else {
            this.genotype.mutateRandom(this.mutationMin, this.mutationMax);
        }
    }

    public void addObserver(IPositionChangeObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        this.observers.remove(observer);
    }

    public void decreaseEnergy() {
        this.energy -= energyUsedToMove;
    }

    public void increaseEnergy() {
        this.energy += energyGainedFromEating;
    }

    private int getEnergyFromParents(Animal dad, Animal mom) {
        int parentsEnergy = dad.getEnergy() + mom.getEnergy();
        int dadEnergyUsed = (int) Math.ceil(energyUsedToReproduce * (1.0 * dad.getEnergy() / parentsEnergy)); // 1.0 is used to cast dad's energy into double
        int momEnergyUsed = energyUsedToReproduce * (mom.getEnergy() / parentsEnergy);

        dad.setEnergy(dad.getEnergy() - dadEnergyUsed);
        mom.setEnergy(mom.getEnergy() - momEnergyUsed);

        return momEnergyUsed + dadEnergyUsed;
    }

    public void addAge() {
        this.age++;
    }

    public int getEnergy() {
        return this.energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public boolean isDead() {
        return getEnergy() <= 0;
    }

    public int getCountEatenGrass() {
        return this.countEatenGrass;
    }

    public void addToCountEatenGrass() {
        this.countEatenGrass++;
    }

    public void addChild() {
        this.children++;
    }

    public int getChildrenAmount() {
        return children;
    }

    public void move() {
        addAge();
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


        var oldPosition = this.getPosition();
        var newPosition = this.position.add(direction.toUnitVector());

        positionChanged(oldPosition, newPosition);

        decreaseEnergy();
    }

    public int currentActiveGene() {
        return genePicker.getCurrentGeneIndex();
    }

    public ArrayList<Integer> getGenotype() {
        return this.genotype.getGenotype();
    }

    public void die(int day) {
//        this.cell.removeElement(this);
//        this.map.removeElement(this);
        this.deathDate = day;
    }

    public int getAge() {
        return this.age;
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : this.observers) {
            observer.positionChanged(this, oldPosition, newPosition);
        }
    }
    public boolean isReadyToBorn(){
        return getEnergy()>=this.energyNeededToBorn;
    }
    public String toString(){
        return "kierunek "+direction.toString()+" pozycja "+getPosition().toString()+" genotyp "+genotype.toString();
    }
    public int getEnergyNeededToBorn(){
        return this.energyNeededToBorn;
    }


    @Override
    public int compareTo(Animal o) {
        return Comparator.comparing(Animal::getEnergy)
                .thenComparing(Animal::getAge)
                .thenComparing(Animal::getChildrenAmount)
                .compare(this, o);
    }

    public void eat(Grass grass) {
        this.map.deleteAt(grass.getPosition(), grass);

        this.setEnergy(this.energy + this.energyGainedFromEating);
    }
}
