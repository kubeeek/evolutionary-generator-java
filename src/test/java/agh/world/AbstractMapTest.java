package agh.world;

import agh.Vector2d;
import agh.world.AbstractMap;
import agh.world.Animal;
import agh.world.GlobeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractMapTest {

    public AbstractMap testInstance;

    @BeforeEach
    void setUp() {
        this.testInstance = new GlobeMap(50, 50, 10, 10);
    }


    @Test
    void populateGrass() {
        // 10 is init grass amount
        assertEquals(10, this.testInstance.mapObjects.size());
    }

    @Test
    void canMoveTo() {
        var result = this.testInstance.canMoveTo(new Vector2d(2, 2));

        assertTrue(result);
    }

    @Test
    void place() {
        var result = this.testInstance.place(new Animal(2, 2));
        this.testInstance.place(new Animal(2, 2));
        this.testInstance.place(new Animal(2, 3));

        assertTrue(result);

        var list = this.testInstance.objectsAt(new Vector2d(2, 2));
        assertEquals(2, list.size());
        assertEquals(2, this.testInstance.objectsAt(new Vector2d(2, 2)).size());
        assertTrue(this.testInstance.objectsAt(new Vector2d(2, 3)).size() > 0);

    }

    @Test
    void isOccupied() {
        var result = this.testInstance.place(new Animal(2, 2));

        assertTrue(result);
        assertFalse(this.testInstance.isOccupied(new Vector2d(2, 3)));
    }

    @Test
    void objectsAt() {
        var ref = new Animal(2, 2);
        this.testInstance.place(ref);
        this.testInstance.place(new Animal(2, 2));

        var result = this.testInstance.objectsAt(new Vector2d(2, 2));

        assertEquals(2, result.size());
        assertEquals(ref, result.get(0));

        result = this.testInstance.objectsAt(new Vector2d(2, 3));
        assertEquals(0, result.size());
    }

    @Test
    void placeAt() {
        var ref = new Animal(2, 2);
        this.testInstance.placeAt(new Vector2d(2, 2), ref);

        assertEquals(ref, this.testInstance.objectsAt(new Vector2d(2, 2)).get(0));
    }

    @Test
    void deleteAt() {
        var ref = new Animal(2, 2);
        this.testInstance.place(ref);

        var result = this.testInstance.deleteAt(new Vector2d(2, 2), ref);

        assertTrue(result);
    }
}
