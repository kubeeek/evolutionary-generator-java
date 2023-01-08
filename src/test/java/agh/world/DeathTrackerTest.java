package agh.world;

import agh.Animal;
import agh.Vector2d;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeathTrackerTest {
    @Test
    @DisplayName("Test the constructor to ensure it correctly initializes the deathsOnMap field with the correct number of keys")
    public void testConstructor() {
        DeathTracker tracker = new DeathTracker(10, 20);
        assertEquals(tracker.deathsOnMap.size(), 200);
    }

    @Test
    @DisplayName("Test the countAnimal method to ensure it correctly increments the death count for the animal's position")
    public void testCountAnimal() {
        DeathTracker tracker = new DeathTracker(10, 20);
        Animal a = Mockito.mock(Animal.class);
        Mockito.when(a.getPosition()).thenReturn(new Vector2d(5, 5));
        Mockito.when(a.isDead()).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> tracker.countAnimal(a));

        Mockito.when(a.isDead()).thenReturn(true);
        tracker.countAnimal(a);
        assertEquals(tracker.deathsOnMap.get(a.getPosition()), 1);
    }

    @Test
    @DisplayName("Test the `addGrave` method to ensure it correctly increments the death count for the specified position")
    public void testAddGrave() {
        DeathTracker tracker = new DeathTracker(10, 20);
        Vector2d pos = new Vector2d(5, 5);
        tracker.addGrave(pos);
        assertEquals(tracker.deathsOnMap.get(pos), 1);
    }

    @Test
    @DisplayName("Test the getLeastGravePositions method to ensure it returns the positions with the lowest death count in ascending order")
    public void testGetLeastGravePositions() {
        DeathTracker tracker = new DeathTracker(10, 20);
        Vector2d pos1 = new Vector2d(6, 5);
        Vector2d pos2 = new Vector2d(7, 5);

        tracker.addGrave(pos1);
        tracker.addGrave(pos2);
        tracker.addGrave(pos2);

        ArrayList<Vector2d> leastGravePositions = tracker.getLeastGravePositions();
        assertEquals(pos1, leastGravePositions.get(leastGravePositions.size() - 2));
        assertEquals(pos2, leastGravePositions.get(leastGravePositions.size() - 1));
    }
}