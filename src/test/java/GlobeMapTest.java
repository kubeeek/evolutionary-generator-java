import agh.Vector2d;
import agh.world.Animal;
import agh.world.GlobeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GlobeMapTest extends AbstractMapTest {
    @BeforeEach
    void setUp() {
        this.testInstance = new GlobeMap(50, 50, 10, 10);
    }
}