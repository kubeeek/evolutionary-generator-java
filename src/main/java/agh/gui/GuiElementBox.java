package agh.gui;

import agh.Animal;
import agh.IGameObject;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class GuiElementBox {
    private final Image image;
    private final ImageView imageView;
//    private final Label label;
//    public final VBox vBox;

    public GuiElementBox(IGameObject mapElement) throws FileNotFoundException {
        getClass().getResourceAsStream(mapElement.toImagePath());
        this.image = new Image(new FileInputStream(mapElement.toImagePath()));
        this.imageView = new ImageView(image);


//        if(mapElement instanceof Animal)
//            this.label = new Label("Z(%d, %d)".formatted(mapElement.getPosition().x, mapElement.getPosition().y));
//        else
//            this.label = new Label("Trawa");

//        this.vBox = new VBox(this.imageView, this.label);
//        this.vBox.setAlignment(Pos.CENTER);

        this.setup();
    }

    public GuiElementBox() throws FileNotFoundException {
        this.image = new Image(new FileInputStream("src/main/resources/ziemia.png"));
        this.imageView = new ImageView(image);

        this.setup();
    }

    public GuiElementBox(Image image) {
        this.image = image;
        this.imageView = new ImageView(image);

        this.setup();
    }

    private void setup() {
        imageView.setFitWidth(WindowConstant.SPRITE_WIDTH);
        imageView.setFitHeight(WindowConstant.SPRITE_HEIGHT);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Image getImage() {
        return this.image;
    }
}