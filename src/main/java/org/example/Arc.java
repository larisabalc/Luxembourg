package org.example;

import java.awt.*;
import java.awt.geom.Line2D;

public class Arc
{
    private final Node startNode;
    private final Node endNode;
    private final Color color;
    private final int archCost;

    public int getArchCost()
    {
        return archCost;
    }
    public Node getStartNode()
    {
        return startNode;
    }
    public Node getEndNode()
    {
        return endNode;
    }

    public Arc(Node startNode, Node endNode, int archCost, Color color)
    {
        this.color = color;
        this.startNode = startNode;
        this.endNode = endNode;
        this.archCost = archCost;
    }

    public void drawArc(Graphics g)
    {
        if (startNode != null)
        {
            g.setColor(color);
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(new Line2D.Double(startNode.getCoordX(),  startNode.getCoordY(), endNode.getCoordX(),  endNode.getCoordY()));
        }
    }
}
