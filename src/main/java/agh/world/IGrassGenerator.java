package agh.world;

import agh.Grass;

public interface IGrassGenerator {
    Grass getNewGrass();

    void setUp(AbstractMap abstractMap);
}
