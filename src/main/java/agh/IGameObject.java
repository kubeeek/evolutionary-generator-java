package agh;

public interface IGameObject {
    Vector2d getposition();
    boolean isAt(Vector2d position);
    void SetPosition(Vector2d position);
}

