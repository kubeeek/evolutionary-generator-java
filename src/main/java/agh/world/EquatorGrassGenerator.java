package agh.world;

import agh.Grass;
import agh.Vector2d;

import java.util.Random;

public class EquatorGrassGenerator implements IGrassGenerator
{
    private final Random randomizer;
    private int mapWidth = 0;
    private int mapHeight = 0;
    int equator;
    int equatorHeight;

    EquatorGrassGenerator(int equatorHeight) {
        this.randomizer = new Random();
        this.equatorHeight = equatorHeight;

    }

    @Override
    public Grass getNewGrass() {
        float randomValue = this.randomizer.nextFloat();
        int xPos, yPos;

        xPos = randomizer.nextInt(0, mapWidth);
        if(randomValue > 0.2) {
             yPos = randomizer.nextInt(equator - equatorHeight, equator + equatorHeight);
        } else {
            yPos = randomizer.nextInt(0, mapHeight);
        }

        return new Grass(new Vector2d(2, 2));
    }

    @Override
    public void setUp(AbstractMap map) {
        this.mapHeight = map.height;
        this.mapWidth = map.width;

        this.equator = mapHeight/2;

    }
}
