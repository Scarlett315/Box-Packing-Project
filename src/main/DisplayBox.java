package main;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;
import javafx.scene.shape.DrawMode;

public class DisplayBox{
    private Box box = new Box();
    public DisplayBox(CalcBox initBox) {
        box.setWidth(initBox.getWidth());
        box.setHeight(initBox.getHeight());
        box.setDepth(initBox.getLength());
        box.setDrawMode(DrawMode.LINE);

        Translate translate = new Translate();

        // adding 0.5 * l/w/h to each translation because javafx moves based on center point
        translate.setX(initBox.getStartPoint().getX() + initBox.getWidth() * 0.5);
        translate.setY(-initBox.getStartPoint().getZ() - initBox.getHeight()* 0.5);
        translate.setZ(initBox.getStartPoint().getY() + initBox.getLength() * 0.5);

        box.getTransforms().addAll(translate);
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }
}

