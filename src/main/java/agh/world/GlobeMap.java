package agh.world;

import java.beans.PropertyChangeEvent;

public class GlobeMap extends AbstractMap {
    public GlobeMap(int width, int height, int startPlantCount, int dailyPlantGrowth, IGrassGenerator grassGenerator) {
        super(width, height, startPlantCount, dailyPlantGrowth, grassGenerator);
    }
}
