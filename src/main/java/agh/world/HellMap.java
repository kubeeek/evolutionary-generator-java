package agh.world;

import agh.Animal;
import agh.IGameObject;
import agh.Vector2d;

public class HellMap extends AbstractMap {
    public HellMap(int width, int height, int startPlantCount, int dailyPlantGrowth, IGrassGenerator grassGenerator) {
        super(width, height, startPlantCount, dailyPlantGrowth, grassGenerator);
    }

    @Override
    public void positionChanged(IGameObject object, Vector2d oldPosition, Vector2d newPosition) {
        if(newPosition.x >= this.getWidth() || newPosition.x < 0)
            newPosition = this.getRandomPosition();

        if(newPosition.y >= this.getHeight() || newPosition.y < 0)
            newPosition = this.getRandomPosition();

        var animal = (Animal) object;
        animal.setEnergy(animal.getEnergy() - animal.getEnergyNeededToBorn());

        super.positionChanged(object, oldPosition, newPosition);
    }
}
