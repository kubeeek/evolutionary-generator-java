package agh;

import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractGenePicker implements IGenePicker {
    ArrayList<Integer> genotype = new ArrayList<>();
    int currentGeneIndex = -1;
    Random random = new Random();

    @Override
    public void setGenotype(ArrayList<Integer> genotype) {
        this.genotype = genotype;
    }
    @Override
    public int getCurrentGeneIndex(){
        return currentGeneIndex;
    }

    @Override
    public void setRandomCurrentIndex(){
        this.currentGeneIndex=random.nextInt(8);
    }
}
