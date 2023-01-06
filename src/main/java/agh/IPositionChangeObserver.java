package agh;

public interface IPositionChangeObserver {
    void positionChanged(IGameObject object, Vector2d oldPosition, Vector2d newPosition);
}
