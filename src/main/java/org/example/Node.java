package org.example;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Node {
    private Color color;
    private final double coordX;
    private final double coordY;
    private final int number;

    public Node(double coordX, double coordY, int number, Color color) {
        this.color = color;
        this.coordX = coordX;
        this.coordY = coordY;
        this.number = number;
    }

    public Color getColor() {
        return color;
    }

    public void SetColor(Color color) {
        this.color = color;
    }

    public double getCoordX() {
        return coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    public int getNumber() {
        return number;
    }

    public void DrawingMethod(double longitude, double latitude, Graphics graphics) {
        graphics.setColor(color);
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        Ellipse2D.Double shape = new Ellipse2D.Double(longitude, latitude, 0.5, 0.5);
        graphics2D.draw(shape);
    }

    public void drawNodeMaxSize(Graphics graphics) {
        DrawingMethod(coordX, coordY, graphics);
    }
}

