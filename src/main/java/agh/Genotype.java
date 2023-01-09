package agh;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Genotype {
    public int lengthOfGenotype;
    public ArrayList<Integer> genotype = new ArrayList<>();
    public final Random random = new Random();

    public Genotype(int lengthOfGenotype) {
        this.lengthOfGenotype=lengthOfGenotype;
        for (int i = 0; i < this.lengthOfGenotype; i++) {
            this.genotype.add(random.nextInt(8));
        }
    }

    public Genotype(ArrayList<Integer> genotype) {
        this.lengthOfGenotype=genotype.size();
        this.genotype = genotype;
    }

    public Genotype(Animal firstParent, Animal secondParent) {
        this.genotype = new ArrayList<>(firstParent.getGenotype());
        this.lengthOfGenotype=genotype.size();
        int side = this.random.nextInt(2); // wylosowanie z której strony bedzie genom silniejszego zwierzaka
        int stop; //miejsce podziału genomu
        int parentsEnergy = firstParent.getEnergy() + secondParent.getEnergy();
        Animal strongerAnimal;
        Animal weakerAnimal;
        ArrayList<Integer> strongerAnimalGenotype;
        ArrayList<Integer> weakerAnimalGenotype;


        weakerAnimal = firstParent.getEnergy() < secondParent.getEnergy() ? firstParent : secondParent; //porównanie w celu znalezienia zwierzaka z wieksza energia
        strongerAnimal = firstParent.getEnergy() >= secondParent.getEnergy() ? secondParent : firstParent; // -||- z mniejsza energia

        strongerAnimalGenotype = strongerAnimal.getGenotype();
        weakerAnimalGenotype = weakerAnimal.getGenotype();

        if (side == 0) {
            stop = (strongerAnimal.getEnergy() / parentsEnergy) * lengthOfGenotype; //udział rodzica razy dlugosc genomu
            for (int i = 0; i < lengthOfGenotype; i++) {
                if (stop >= 0)
                    this.genotype.set(i, strongerAnimalGenotype.get(i));
                if (lengthOfGenotype - stop >= 0)
                    this.genotype.set(i, weakerAnimalGenotype.get(i));
            }
        } else {
            stop = (weakerAnimal.getEnergy() / parentsEnergy) * lengthOfGenotype;
            for (int i = 0; i < lengthOfGenotype; i++) {
                if (stop >= 0)
                    this.genotype.set(i, weakerAnimalGenotype.get(i));
                if (lengthOfGenotype - stop >= 0)
                    this.genotype.set(i, strongerAnimalGenotype.get(i));
            }
        }
    }

    public void mutateRandom(int mutationMin, int mutationMax, ArrayList<Integer> genotype) {  //mutowanie losowe z przedzialu 0-7
        ArrayList<Integer> genome = new ArrayList<>(genotype);
        this.lengthOfGenotype=genome.size();
        ArrayList<Integer> indexes = new ArrayList<>();
        int mutation = random.nextInt(mutationMax-mutationMin+1) + mutationMin;
        for (int i = 0; i <= mutation; i++) {
            int index1 = random.nextInt(lengthOfGenotype);
            int value = random.nextInt(8);
            while (indexes.contains(index1) || value == genotype.get(index1)) {
                index1 = random.nextInt(lengthOfGenotype);
                value = random.nextInt(8);
            }
            indexes.add(index1);
            genome.set(index1, value);
        }
        this.genotype=genome;
    }

    public void mutatePlusOne(int mutationMin, int mutationMax, ArrayList<Integer> genotype) {
        ArrayList<Integer> genome = new ArrayList<>(genotype);
        this.lengthOfGenotype=genome.size();
        ArrayList<Integer> indexes = new ArrayList<>();
        int mutation = random.nextInt(mutationMax-mutationMin+1) + mutationMin;
        for (int i = 0; i <= mutation; i++) {
            int sign = this.random.nextInt(2); //losowanie czy gen+1 lub gen-1
            int index1 = random.nextInt(lengthOfGenotype);

            while (indexes.contains(index1))
                index1 = random.nextInt(lengthOfGenotype);

            indexes.add(index1);
            int value = this.genotype.get(index1);
            if (sign == 1) {
                value += 1;
            } else {
                value -= 1;
            }
            if (value == 8) {
                value = 0;
            }
            if (value == -1) {
                value = 7;
            }

            genome.set(index1, value);
        }
        this.genotype=genome;
    }

    public ArrayList<Integer> getGenotype() {
        return this.genotype;
    }

    @Override
    public String toString() {
        return genotype.stream().map(String::valueOf)
                .collect(Collectors.joining(","));
    }
}