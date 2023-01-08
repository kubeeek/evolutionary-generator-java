package agh.world;

import agh.Grass;
import agh.Vector2d;

import java.security.InvalidParameterException;
import java.util.Random;

public class EquatorGrassGenerator implements IGrassGenerator
{
    Random randomizer;
    int mapWidth = 0;
    int mapHeight = 0;
    int equator;
    int equatorHeight;

    public EquatorGrassGenerator(int equatorHeight) {
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

        return new Grass(new Vector2d(xPos, yPos));
    }

    @Override
    public void setUp(AbstractMap map) {
        this.mapHeight = map.getHeight();
        this.mapWidth = map.getWidth();

        this.equator = mapHeight/2;

        if(this.equator - this.equatorHeight < 0)
            throw new InvalidParameterException("Equator height is too large. Exceeds the lower limit of 0.");

    }
}
