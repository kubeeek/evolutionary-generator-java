package agh.world;

import agh.IGameObject;
import agh.Vector2d;

public class GlobeMap extends AbstractMap {
    public GlobeMap(int width, int height, int startPlantCount, int dailyPlantGrowth, IGrassGenerator grassGenerator) {
        super(width, height, startPlantCount, dailyPlantGrowth, grassGenerator);
    }

    @Override
    public synchronized void positionChanged(IGameObject object, Vector2d oldPosition, Vector2d newPosition) {
        if (newPosition.x >= this.getWidth())
            newPosition = new Vector2d(0, newPosition.y);
        else if (newPosition.x < 0)
            newPosition = new Vector2d(this.getWidth() - 1, newPosition.y);

        if (newPosition.y >= this.getHeight())
            newPosition = new Vector2d(newPosition.x, 0);
        else if (newPosition.x < 0)
            newPosition = new Vector2d(newPosition.x, this.getHeight() - 1);


        super.positionChanged(object, oldPosition, newPosition);
    }
}
