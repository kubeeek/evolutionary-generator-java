package agh.world;

import agh.Grass;
import agh.Vector2d;

public class ToxicGravesGrassGenerator implements IGrassGenerator {
    private GraveyardTracker graveyardTracker;

    @Override
    public Grass getNewGrass() {
        var leastDeathsMapPosition = this.graveyardTracker.getLeastGravePosition();

        return new Grass(new Vector2d(leastDeathsMapPosition.x, leastDeathsMapPosition.y));
    }

    @Override
    public void setUp(AbstractMap abstractMap) {
        this.graveyardTracker = abstractMap.graveyardTracker;
    }
}
