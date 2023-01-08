package agh.world;

import agh.Grass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class EquatorGrassGeneratorTest {

    @Test
    @DisplayName("Test the setUp method to ensure it correctly sets the mapWidth and mapHeight field")
    public void testSetUp() {
        EquatorGrassGenerator generator = new EquatorGrassGenerator(10);
        AbstractMap map = Mockito.mock(AbstractMap.class);

        Mockito.when(map.getWidth()).thenReturn(100);
        Mockito.when(map.getHeight()).thenReturn(200);

        generator.setUp(map);

        assertEquals(generator.mapWidth, 100);
        assertEquals(generator.mapHeight, 200);
    }

    @Test
    @DisplayName("Test the getNewGrass method to ensure it correctly generates grass in the full height of the map with a probability of 20%")
    public void testGetNewGrass() {
        EquatorGrassGenerator generator = new EquatorGrassGenerator(10);
        AbstractMap map = Mockito.mock(AbstractMap.class);

        Mockito.when(map.getWidth()).thenReturn(100);
        Mockito.when(map.getHeight()).thenReturn(200);
        generator.setUp(map);

        generator.randomizer = new Random(1L);
        Grass grass = generator.getNewGrass();
        assertTrue(grass.getPosition().y >= 90 && grass.getPosition().y <= 110);

        grass = generator.getNewGrass();
        assertTrue(grass.getPosition().y >= 0 && grass.getPosition().y <= 200);
    }
}