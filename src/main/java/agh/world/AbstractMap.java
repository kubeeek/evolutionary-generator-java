package agh.world;

import agh.Animal;
import agh.Grass;
import agh.IGameObject;
import agh.Vector2d;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

public abstract class AbstractMap implements IMap, PropertyChangeListener {
    final int width;
    final int height;
    private final int dailyPlantGrowth;
    final DeathTracker deathTracker;

    HashMap<Vector2d, LinkedHashSet<IGameObject>> mapObjects = new HashMap<>();
    LinkedHashSet<IGameObject> defaultValue = new LinkedHashSet<>();

    IGrassGenerator grassGenerator;

    AbstractMap(int width, int height, int startPlantCount, int dailyPlantGrowth, IGrassGenerator grassGenerator) {
        this.width = width;
        this.height = height;

        this.dailyPlantGrowth = dailyPlantGrowth;
        this.grassGenerator = grassGenerator;
        this.grassGenerator.setUp(this);

        this.populateGrass(startPlantCount);

        this.deathTracker = new DeathTracker(this.height, this.width);
    }

    public void populateGrass(int amount) {
        for (int i = 0; i < amount; i++) {
            Grass newGrass = this.grassGenerator.getNewGrass();
            while (this.hasGrassAt(newGrass.getPosition())) {
                newGrass = this.grassGenerator.getNewGrass();
            }

            this.place(newGrass);
        }
    }

    private void initializeKey(Vector2d key) {
        mapObjects.put(key, new LinkedHashSet<>());
    }

    private boolean hasGrassAt(Vector2d position) {
        var objectList = this.objectsAt(position);

        return !objectList.stream().filter(listObject -> listObject instanceof Grass).toList().isEmpty();
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    @Override
    public boolean place(IGameObject gameObject) throws InvalidParameterException {
        var position = gameObject.getPosition();

        if (position == null)
            throw new InvalidParameterException("Position cannot be null");

        return this.placeAt(position, gameObject);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        var objectList = this.objectsAt(position);

        return !objectList.stream().filter(listObject -> listObject instanceof Animal).toList().isEmpty();
    }

    @Override
    public ArrayList<IGameObject> objectsAt(Vector2d position) {
        return new ArrayList<>(mapObjects.getOrDefault(position, this.defaultValue).stream().toList());
    }

    @Override
    public boolean placeAt(Vector2d position, IGameObject gameObject) {
        if (mapObjects.get(position) == null)
            this.initializeKey(position);

        return mapObjects.get(position).add(gameObject);
    }

    @Override
    public boolean deleteAt(Vector2d position, IGameObject gameObject) {
        return mapObjects.getOrDefault(position, this.defaultValue).remove(gameObject);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
