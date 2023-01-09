package agh.world;

import agh.IGameObject;
import agh.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo
 *
 */
public interface IMap {
    /**
     * Indicate if any object can move to the given position.
     *
     * @param position The position checked for the movement possibility.
     * @return True if the object can move to that position.
     */
    boolean canMoveTo(Vector2d position);

    /**
     * Place a animal on the map.
     *
     * @param animal The animal to place on the map.
     * @return True if the animal was placed. The animal cannot be placed if the map is already occupied.
     */
    boolean place(IGameObject animal);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an object at a given position.
     *
     * @param position The position of the object.
     * @return Array of objects or empty if the position is not occupied.
     */
    ArrayList<IGameObject> objectsAt(Vector2d position);

    /**
     * Place an object at a given position.
     *
     * @param position The position of the object.
     * @return Object or null if the position is not occupied.
     */
    boolean placeAt(Vector2d position, IGameObject gameObject);

    /**
     * Delete an object at a given position.
     *
     * @param position The position of the object.
     * @return Object or null if the position is not occupied.
     */
    boolean deleteAt(Vector2d position, IGameObject gameObject);

    int getHeight();

    int getWidth();

    Vector2d getRandomPosition();

    void populateGrass();
     HashMap<Vector2d, LinkedHashSet<IGameObject>> getMapObjects();


}