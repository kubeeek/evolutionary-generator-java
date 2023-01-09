package agh;

public interface IPositionChangeObserver {
    public void positionChanged(IGameObject object, Vector2d oldPosition, Vector2d newPosition);
}
