package util;

import javax.swing.*;
import java.awt.*;

/**
 * this class represents an whiteboard object in the system
 */
public class MyObj {

    private Color color;
    private Shape shape;
    private JTextField text;

    public MyObj(Color color, Shape shape, JTextField text) {
        this.color = color;
        this.shape = shape;
        this.text = text;
    }

    public Color getColor() {
        return color;
    }

    public Shape getShape() {
        return shape;
    }

    public JTextField getText() {
        return text;
    }
}
