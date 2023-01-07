package agh;

import java.util.ArrayList;

public class DeterministicGenePicker extends AbstractGenePicker{

    @Override
    public int getNextGene() {
        this.currentGeneIndex = (currentGeneIndex + 1) % genotype.size();
        return genotype.get(currentGeneIndex);
    }
}
