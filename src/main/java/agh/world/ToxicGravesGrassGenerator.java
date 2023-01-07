package agh.world;

import agh.Grass;
import agh.Vector2d;

public class ToxicGravesGrassGenerator implements IGrassGenerator {
    private DeathTracker deathTracker;

    @Override
    public Grass getNewGrass() {
        var sorted = this.deathTracker.getLeastGravePositions();
        var leastDeathsMapPosition = sorted.get(0);

        return new Grass(new Vector2d(leastDeathsMapPosition.x, leastDeathsMapPosition.y));
    }

    @Override
    public void setUp(AbstractMap abstractMap) {
        this.deathTracker = abstractMap.deathTracker;
    }
}
