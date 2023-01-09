package agh;

public class Grass extends AbstractGameObject{
    public Grass(Vector2d position) {
        this.position=position;
    }
    @Override
    public String toString() {
        return "P " + this.position.toString();
    }
    @Override
    public String toImagePath() {
       return "src/main/resources/trawa.png";}

}
