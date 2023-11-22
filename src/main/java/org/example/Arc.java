package org.example;

import java.awt.*;
import java.awt.geom.Line2D;

public class Arc
{
    private Node start;
    private Node end;

    private Color color;
    private int val;

    public int GetVal()
    {
        return val;
    }
    public Node GetStart()
    {
        return start;
    }

    public Node GetEnd()
    {
        return end;
    }

    public Arc(Node start, Node end, int val,Color color)
    {
        this.color = color;
        this.start = start;
        this.end = end;
        this.val = val;
    }

    public void drawArc(Graphics g)
    {
        if (start != null)
        {
            g.setColor(color);
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(new Line2D.Double(start.getCoordX(),  start.getCoordY(), end.getCoordX(),  end.getCoordY()));
        }
    }
}
