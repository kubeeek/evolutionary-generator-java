package agh.world;

import agh.IGameObject;
import agh.Vector2d;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.InvalidParameterException;
import java.util.*;

public abstract class AbstractMap implements IMap, PropertyChangeListener {
    HashMap<Vector2d, LinkedHashSet<IGameObject>> mapObjects = new HashMap<>();
    LinkedHashSet<IGameObject> defaultValue = new LinkedHashSet<>();

    private final int width;
    private final int height;
    private final int dailyPlantGrowth;

    Vector2d lowerLeft = new Vector2d(10, 10);
    Vector2d upperRight = new Vector2d(10, 10);


    AbstractMap(int width, int height, int startPlantCount, int dailyPlantGrowth) {
        this.width = width;
        this.height = height;

        this.dailyPlantGrowth = dailyPlantGrowth;

        this.populateGrass(startPlantCount);
    }

    private void populateGrass(int amount) {
        for(int i = 0; i < amount; i++) {
            this.createGrass();
        }
    }

    private void initializeKey(Vector2d key) {
        mapObjects.put(key, new LinkedHashSet<>());
    }

    private void createGrass() {
        Random random = new Random();
        Vector2d randomPosition = new Vector2d(random.nextInt(this.width), random.nextInt(this.height));

        while (this.hasGrassAt(randomPosition)) {
            randomPosition = new Vector2d(random.nextInt(this.width), random.nextInt(this.height));
        }

        this.place(new Grass(randomPosition));
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

        if(position == null)
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
        if(mapObjects.get(position) == null)
            this.initializeKey(position);

        return mapObjects.get(position).add(gameObject);
    }

    @Override
    public boolean deleteAt(Vector2d position, IGameObject gameObject) {
       return mapObjects.getOrDefault(position, this.defaultValue).remove(gameObject);
    }

    @Override
    public Vector2d getLowerLeft() {
        return this.lowerLeft;
    }

    @Override
    public Vector2d getUpperRight() {
        return this.upperRight;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
