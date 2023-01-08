package agh;

import java.util.ArrayList;

public interface IGenePicker {

    int getNextGene();

    void setGenotype(ArrayList<Integer> genotype);
    public int getCurrentGeneIndex();
    public void setRandomCurrentIndex();
}
