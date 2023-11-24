package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.util.List;


public class MyPanel extends JPanel {

    private Node startNode;
    static public Node endNode;
    private boolean existsPath = false;
    public static HashMap<Integer, Node> nodeList = new HashMap<>();
    public static Vector<Arc> archList = new Vector<>();
    public static Vector<Arc> archPath = new Vector<>();
    private int nodesClicked = 0;

    public MyPanel() {
        repaint();
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (nodesClicked < 2) {
                    if (nodesClicked == 0) {
                        startNode = getClosestNode(e.getPoint().getX(), e.getPoint().getY());
                        nodesClicked += 1;
                        startNode.SetColor(Color.RED);
                    } else if (nodesClicked == 1) {
                        endNode = getClosestNode(e.getPoint().getX(), e.getPoint().getY());
                        nodesClicked += 1;
                        endNode.SetColor(Color.RED);
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (nodesClicked == 2) {
                    DijkstraShortestPath graph = new DijkstraShortestPath(nodeList.size());
                    for (Arc arc : archList)
                        graph.addEdge(arc.getStartNode().getNumber(), arc.getEndNode().getNumber(), arc.getArchCost());
                    List<Integer> path = graph.reconstructPath(startNode.getNumber(), endNode.getNumber());
                    for (int i = 0; i < path.size(); i++) {
                        nodeList.get(i).SetColor(Color.RED);
                    }
                    archPath = new Vector<>();
                    existsPath = true;
                    for (int i = 0; i < path.size() - 1; i++) {
                        Node currentNode = nodeList.get(path.get(i));
                        Node nextNode = nodeList.get(path.get(i + 1));
                        archPath.add(new Arc(currentNode, nextNode, 0, Color.RED));
                    }
                    repaint();
                    nodesClicked = 0;
                }
            }
        });

    }

    private Node getClosestNode(double x, double y) {
        double minDistance = Double.MAX_VALUE;
        Node closestNode = null;
        for (Map.Entry<Integer, Node> nod : nodeList.entrySet()) {
            double distance = Math.pow(nod.getValue().getCoordX() - x, 2) + Math.pow(nod.getValue().getCoordY() - y, 2);
            if (distance < minDistance) {
                minDistance = distance;
                closestNode = nod.getValue();
            }
        }
        return closestNode;
    }


    public static void addNode(double x, double y, int nodeNr) {
        Node node = new Node(x, y, nodeNr, Color.BLACK);
            nodeList.put(nodeNr, node);
    }


    public void paint(Graphics graphics) {
        super.paint(graphics);

        for (Arc arc : archList) {
            arc.drawArc(graphics);
        }

        if (existsPath) for (Arc arc : archPath) {
            arc.drawArc(graphics);
        }
        for (Map.Entry<Integer, Node> nod : nodeList.entrySet()) {
            nod.getValue().drawNodeMaxSize(graphics);
        }
    }
}

