package agh;

public interface IGameObject {
    Vector2d getPosition();
    void setPosition(Vector2d position);
    boolean isAt(Vector2d position);
}
