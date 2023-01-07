package agh.world;

public class ToxicGravesGrassGenerator implements IGrassGenerator {
    private DeathTracker deathTracker;

    @Override
    public Grass getNewGrass() {
        var sorted = this.deathTracker.getLeastGravePositions();
        var leastDeathsMapPosition = sorted.get(0);

        return new Grass(leastDeathsMapPosition.x, leastDeathsMapPosition.y);
    }

    @Override
    public void setUp(AbstractMap abstractMap) {
        this.deathTracker = abstractMap.deathTracker;
    }
}
