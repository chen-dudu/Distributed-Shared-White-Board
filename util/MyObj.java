package util;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * this class represents an whiteboard object in the system
 */
public class MyObj implements Serializable {

    private Color color;
    private Shape shape;
    private Point textLocation;
    private String text;

    public MyObj() {
        this.color = null;
        this.shape = null;
        this.textLocation = null;
        this.text = null;
    }

    public MyObj(Color color, Shape shape, Point textLocation, String text) {
        this.color = color;
        this.shape = shape;
        this.textLocation = textLocation;
        this.text = text;
    }

    public Color getColor() {
        return color;
    }

    public Shape getShape() {
        return shape;
    }

    public Point getTextLocation() {
        return textLocation;
    }

    public String getText() {
        return text;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void setTextLocation(Point textLocation) {
        this.textLocation = textLocation;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        String output = "";
        if (shape == null) {
            // insert text
            output += "text: " + this.text + " at (" + this.textLocation.getX() + ", " + this.getTextLocation().getY() + ")";
        }
        else {
            // draw shape
            output += "shape: " + this.shape.toString() + " in " + this.color.toString();
        }
        return output;
    }
}
