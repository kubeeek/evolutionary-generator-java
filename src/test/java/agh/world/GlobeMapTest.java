package agh.world;

import agh.world.AbstractMapTest;
import agh.world.GlobeMap;
import org.junit.jupiter.api.BeforeEach;

public class GlobeMapTest extends AbstractMapTest {
    @BeforeEach
    void setUp() {
        this.testInstance = new GlobeMap(50, 50, 10, 10);
    }
}