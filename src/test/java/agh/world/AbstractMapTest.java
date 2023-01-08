package agh.world;

import agh.Animal;
import agh.Grass;
import agh.Vector2d;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AbstractMapTest {

    public AbstractMap testInstance;

    public IGrassGenerator grassGeneratorMock;

    private Animal animal1;
    private Animal animal2;
    private Animal animal3;

    @BeforeEach
    public void setup() {
        // mock dependencies
        ArrayList<Grass> mockedGrasses = new ArrayList<>();

        this.grassGeneratorMock = Mockito.mock(IGrassGenerator.class);
        var mockedGrass = Mockito.mock(Grass.class, withSettings().useConstructor(new Vector2d(5, 5)));
        mockedGrasses.add(mockedGrass);
        Mockito.when(mockedGrass.getPosition()).thenReturn(new Vector2d(5, 5));

        mockedGrass = Mockito.mock(Grass.class, withSettings().useConstructor(new Vector2d(10, 5)));
        mockedGrasses.add(mockedGrass);
        Mockito.when(mockedGrass.getPosition()).thenReturn(new Vector2d(10, 5));

        Mockito.when(grassGeneratorMock.getNewGrass())
                .thenReturn(mockedGrasses.get(0))
                .thenReturn(mockedGrasses.get(1))
                .thenReturn(null);

        this.testInstance = new GlobeMap(50, 50, 2, 10, this.grassGeneratorMock);

        this.animal1 = mock(Animal.class);
        this.animal2 = mock(Animal.class);
        this.animal3 = mock(Animal.class);

        when(animal1.getPosition()).thenReturn(new Vector2d(2, 2));
        when(animal2.getPosition()).thenReturn(new Vector2d(2, 2));
        when(animal3.getPosition()).thenReturn(new Vector2d(2, 3));
    }


    @Test
    void populateGrass() {
        assertEquals(2, this.testInstance.mapObjects.size());
    }

    @Test
    void canMoveTo() {
        var result = this.testInstance.canMoveTo(new Vector2d(2, 2));

        assertTrue(result);
    }

    @Test
    void place() {
        var result = this.testInstance.place(animal1);
        this.testInstance.place(animal2);
        this.testInstance.place(animal3);

        assertTrue(result);

        var list = this.testInstance.objectsAt(new Vector2d(2, 2));
        assertEquals(2, list.size());
        assertTrue(this.testInstance.objectsAt(new Vector2d(2, 3)).size() > 0);
    }

    @Test
    void isOccupied() {
        var animal = mock(Animal.class);
        when(animal.getPosition()).thenReturn((new Vector2d(2, 2)));

        var result = this.testInstance.place(animal);

        assertTrue(result);
        assertFalse(this.testInstance.isOccupied(new Vector2d(2, 3)));
    }

    @Test
    void objectsAt() {
        this.testInstance.place(animal1);
        this.testInstance.place(animal2);

        var result = this.testInstance.objectsAt(new Vector2d(2, 2));

        assertEquals(2, result.size());
        assertEquals(animal1, result.get(0));

        result = this.testInstance.objectsAt(new Vector2d(2, 3));
        assertEquals(0, result.size());
    }

    @Test
    void placeAt() {
        this.testInstance.placeAt(new Vector2d(2, 2), animal1);

        assertEquals(animal1, this.testInstance.objectsAt(new Vector2d(2, 2)).get(0));
    }

    @Test
    void deleteAt() {
        this.testInstance.place(animal1);

        var result = this.testInstance.deleteAt(new Vector2d(2, 2), animal1);

        assertTrue(result);
        assertEquals(0, this.testInstance.objectsAt(animal1.getPosition()).size());
    }
}
