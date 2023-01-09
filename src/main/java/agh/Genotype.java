package agh;

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
            for (int i = 0; i <= lengthOfGenotype; i++) {
                if (stop >= 0)
                    this.genotype.set(i, strongerAnimalGenotype.get(i));
                if (8 - stop >= 0)
                    this.genotype.set(i, weakerAnimalGenotype.get(i));
            }
        } else {
            stop = (weakerAnimal.getEnergy() / parentsEnergy) * lengthOfGenotype;
            for (int i = 0; i <= lengthOfGenotype; i++) {
                if (stop >= 0)
                    this.genotype.set(i, weakerAnimalGenotype.get(i));
                if (8 - stop >= 0)
                    this.genotype.set(i, strongerAnimalGenotype.get(i));
            }
        }
    }

    public void mutateRandom(int mutationMin, int mutationMax) {  //mutowanie losowe z przedzialu 0-7
        ArrayList<Integer> indexes = new ArrayList<>();
        int mutation = random.nextInt(mutationMax + 1) + mutationMin;
        for (int i = 0; i < mutation; i++) {
            int index1 = random.nextInt(lengthOfGenotype);
            int value = random.nextInt(8);
            while (indexes.contains(index1) || value == genotype.get(index1)) {
                index1 = random.nextInt(lengthOfGenotype);
                value = random.nextInt(8);
            }
            indexes.add(index1);
            this.genotype.set(index1, value);
        }
    }

    public void mutatePlusOne(int mutationMin, int mutationMax) {
        ArrayList<Integer> indexes = new ArrayList<>();
        int mutation = random.nextInt(mutationMax + 1) + mutationMin;
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

            this.genotype.set(index1, value);
        }
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
