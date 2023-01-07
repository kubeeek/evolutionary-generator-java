package agh;

public abstract class AbstractGameObject implements IGameObject{
    int energy;
    public Vector2d position;
    @Override
    public Vector2d getposition() {
        return this.position;
    }

    @Override
    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    @Override
    public void SetPosition(Vector2d position) {
        this.position=position;
    }

}
