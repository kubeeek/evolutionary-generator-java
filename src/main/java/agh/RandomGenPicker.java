package agh;

import java.util.ArrayList;
import java.util.Random;

public class RandomGenPicker extends AbstractGenePicker {

    @Override
    public int getNextGene() {
        int rand = random.nextInt(100);
        int nextGeneIndex;
        if (rand < 20) {
            nextGeneIndex = random.nextInt(genotype.size());
            while (nextGeneIndex == currentGeneIndex) {
                nextGeneIndex = random.nextInt(genotype.size());
            }
        } else {
           nextGeneIndex = currentGeneIndex+1;
        }
        currentGeneIndex=nextGeneIndex;
        return genotype.get(nextGeneIndex);
    }
}
