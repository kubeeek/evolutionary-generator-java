import agh.Animal;
import agh.Genotype;
import agh.RandomGenPicker;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GenotypeTest {
    @Test
    public void testGenotypeConstructorNoArgs() {
        Genotype genotype = new Genotype(8);
        assertEquals(8, genotype.genotype.size());
        for (int i : genotype.genotype) {
            assertTrue(i >= 0 && i <= 7);
        }
    }

    @Test
    public void testMutateRandom() {
        //Test został zrobiony na seedzie 12345, przy argumentach mutationMax=4 i mutationMin=1
        ArrayList<Integer> genotypeInput = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
        Genotype genotype = new Genotype(genotypeInput);
        genotype.random.setSeed(1234);
        genotype.mutateRandom(1,4);
        ArrayList<Integer> testGenomes = new ArrayList<>(Arrays.asList(1, 6, 7, 3, 2, 5, 6, 7));
        assertEquals(testGenomes, genotype.getGenotype());
    }

    @Test
    public void testMutatePlusOne() {
        //Test został zrobiony na seedzie 1234, przy argumentach mutationMax=4 i mutationMin=1
        ArrayList<Integer> genotypeInput = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
        Genotype genotype = new Genotype(genotypeInput);
        genotype.random.setSeed(1234);
        genotype.mutatePlusOne(1,4);
        ArrayList<Integer> testGenomes = new ArrayList<>(Arrays.asList(0, 0, 3, 2, 4, 5, 5, 6));
        assertEquals(testGenomes, genotype.getGenotype());
    }


}
