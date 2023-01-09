package agh.world;

import agh.Grass;
import agh.Vector2d;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ToxicGravesGrassGeneratorTest {

    private IGrassGenerator grassGenerator;
    private GraveyardTracker graveyardTracker;

    @BeforeEach
    public void setUp() {
        grassGenerator = new ToxicGravesGrassGenerator();
        graveyardTracker = Mockito.mock(GraveyardTracker.class);
        AbstractMap abstractMap = Mockito.mock(AbstractMap.class);

        abstractMap.graveyardTracker = graveyardTracker;

        grassGenerator.setUp(abstractMap);
    }

    @Test
    public void testGetNewGrass() {
        var sortedLeastDeathPositions = Mockito.mock(ArrayList.class);
        Mockito.when(sortedLeastDeathPositions.get(0)).thenReturn(new Vector2d(0, 0)).thenReturn(new Vector2d(1, 1));

        Mockito.when(graveyardTracker.getLeastGravePositions()).thenReturn(sortedLeastDeathPositions);
        Mockito.when(graveyardTracker.getLeastGravePosition()).thenReturn(new Vector2d(0, 0));

        Grass grass = grassGenerator.getNewGrass();
        Assertions.assertEquals(new Vector2d(0, 0), grass.getPosition());
    }
}