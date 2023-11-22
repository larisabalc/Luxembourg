package org.example;

import java.awt.*;
import java.awt.geom.Ellipse2D;


public class Node
{
    Color color;

    private double coordX;
    private double coordY;
    private int number;

    public Node(double coordX, double coordY, int number, Color color)
    {
        this.color = color;
        this.coordX = coordX;
        this.coordY = coordY;
        this.number = number;
    }

    public void SetColor(Color color)
    {
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
    public void DrawingMethod(double longitude, double latitude, Graphics gg)
    {
        gg.setColor(color);
        Graphics2D g = (Graphics2D) gg;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        Ellipse2D.Double shape = new Ellipse2D.Double(longitude, latitude, 0.5, 0.5);
        g.draw(shape);
    }
    public void drawNodeMaxSize(Graphics g, int node_diam) {
       DrawingMethod(coordX,coordY,g);
    }
}

