package agh.world;

import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;

public class GlobeMapTest extends AbstractMapTest {

    @BeforeEach
    void setUp() {
        this.testInstance = new GlobeMap(50, 50, 1, 10, this.grassGeneratorMock);
    }
}