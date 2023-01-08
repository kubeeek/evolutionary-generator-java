package agh.world;

import agh.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Random;

public abstract class AbstractMap implements IMap, IPositionChangeObserver {
    final int width;
    final int height;
    private final int dailyPlantGrowth;
    DeathTracker deathTracker;

    HashMap<Vector2d, LinkedHashSet<IGameObject>> mapObjects = new HashMap<>();
    LinkedHashSet<IGameObject> defaultValue = new LinkedHashSet<>();

    IGrassGenerator grassGenerator;
    Random random = new Random();

    AbstractMap(int width, int height, int startPlantCount, int dailyPlantGrowth, IGrassGenerator grassGenerator) {
        this.width = width;
        this.height = height;

        this.dailyPlantGrowth = dailyPlantGrowth;
        this.grassGenerator = grassGenerator;
        this.grassGenerator.setUp(this);

        this.populateGrass(startPlantCount);

        this.deathTracker = new DeathTracker(this.height, this.width);
    }

    public void populateGrass() {
        this.populateGrass(this.dailyPlantGrowth);
    }

    protected void populateGrass(int amount) {
        for (int i = 0; i < amount; i++) {

            if (!hasFreeSpaceForGrass())
                return;

            Grass newGrass = this.grassGenerator.getNewGrass();
            while (this.hasGrassAt(newGrass.getPosition())) {
                newGrass = this.grassGenerator.getNewGrass();
            }

            this.place(newGrass);
        }
    }

    protected boolean hasFreeSpaceForGrass() {
        if (this.mapObjects.size() < this.height * this.width)
            return true;

        for (var entry :
                this.mapObjects.entrySet()) {
            var positionKey = entry.getKey();

            if (!hasGrassAt(positionKey))
                return true;
        }

        return false;
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
        if (gameObject instanceof Animal && ((Animal) gameObject).isDead())
            deathTracker.countAnimal((Animal) gameObject);

        return mapObjects.getOrDefault(position, this.defaultValue).remove(gameObject);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    @Override
    public Vector2d getRandomPosition() {
        return new Vector2d(random.nextInt(this.width), random.nextInt(this.height));
    }

    @Override
    public void positionChanged(IGameObject object, Vector2d oldPosition, Vector2d newPosition) {
        this.deleteAt(oldPosition, object);

        object.setPosition(newPosition);
        this.place(object);

        // handle eat & birth
    }
}
