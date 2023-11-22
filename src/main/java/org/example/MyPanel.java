package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.util.List;


public class MyPanel extends JPanel {

    private Graphics g;
    private Node start;
    static public Node end;

    private int okDijkstra = 0;

    private int node_diam = 5;

    public static Map<Integer,Node> listaNoduri;
    public static Vector<Arc> listaArce;

    public static Vector<Arc> newlistaArce;

    private int cnt = 0;
    public MyPanel() {
        repaint();
            addMouseListener(new MouseAdapter() {
                //evenimentul care se produce la apasarea mousse-ului
                public void mousePressed(MouseEvent e) {
                    if(cnt < 2) {
                        if(cnt == 0) {
                            start = verificareArc(e.getPoint().getX(), e.getPoint().getY());
                            cnt++;
                            start.SetColor(Color.RED);
                            System.out.println("1");
                            //repaint();
                        }
                        else if(cnt == 1)
                        {
                            end = verificareArc(e.getPoint().getX(), e.getPoint().getY());
                            cnt++;
                            end.SetColor(Color.RED);
                            System.out.println("2");
                            //repaint();
                        }
                    }
                }
                public void mouseReleased(MouseEvent e) {
                    if(cnt == 2)
                    {
                     DijkstrasShortestPathAdjacencyList graph = new DijkstrasShortestPathAdjacencyList(listaNoduri.size());
                     for(int i=0;i<listaArce.size();i++)
                         graph.addEdge(listaArce.get(i).GetStart().getNumber(),listaArce.get(i).GetEnd().getNumber(),listaArce.get(i).GetVal());
                     List<Integer> path = graph.reconstructPath(start.getNumber(), end.getNumber());
                     for(int i=0;i<path.size();i++)
                     {
                         listaNoduri.get(path.get(i)).SetColor(Color.RED);
                         System.out.println(path.get(i));
                     }
                     newlistaArce = new Vector<>();
                     okDijkstra=1;
                        for(int i=0;i<path.size()-1;i++)
                        {
                            Node st = listaNoduri.get(path.get(i));
                            Node dr = listaNoduri.get(path.get(i+1));
                           newlistaArce.add(new Arc(st,dr,0,Color.RED));
                        }
                     repaint();
                     cnt=0;
                    }
                }
            });

    }
    private Node verificareArc(double x, double y) {
        double minim = Double.MAX_VALUE;
        Node nodeMin = null;
        for(Map.Entry<Integer,Node> nod : listaNoduri.entrySet())
        {
            double dist = Math.pow(nod.getValue().getCoordX()-x,2)+Math.pow(nod.getValue().getCoordY()-y,2);
            if(dist <minim) {
                minim = dist;
                nodeMin = nod.getValue();
            }
        }
        return nodeMin;
    }


    public static void addNode(double x, double y, int nodeNr) {
        Node node = new Node(x, y, nodeNr,Color.BLACK);
        listaNoduri.put(nodeNr,node);
    }


    //se executa atunci cand apelam repaint()
    public void paint(Graphics g) {
        this.g = g;
        super.paint(g);//apelez metoda paintComponent din clasa de baza

        for (int i = 0; i < listaArce.size(); i++) {
            listaArce.elementAt(i).drawArc(g);
        }

        if(okDijkstra==1)
        for (int i = 0; i < newlistaArce.size(); i++) {
            newlistaArce.elementAt(i).drawArc(g);
        }
        for(Map.Entry<Integer,Node> nod : listaNoduri.entrySet())
        {
              nod.getValue().drawNodeMaxSize(g,node_diam);
        }
    }
}

