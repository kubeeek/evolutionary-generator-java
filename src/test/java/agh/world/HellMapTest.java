package agh.world;

import agh.world.AbstractMapTest;
import agh.world.HellMap;
import org.junit.jupiter.api.BeforeEach;

public class HellMapTest extends AbstractMapTest {
    @BeforeEach
    void setUp() {
        this.testInstance = new HellMap(50, 50, 10, 10);
    }
}
