package org.example;

import org.geotools.data.FeatureSource;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

public class ReadXMLFile extends MyPanel {

    static double  minLong = Double.MAX_VALUE;
    static double minLat = Double.MAX_VALUE;
    static double maxLong = Double.MIN_VALUE;
    static double maxLat = Double.MIN_VALUE;

    public static void main(String[] args) {
        listaNoduri = new HashMap<>();
        listaArce = new Vector<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document document = null;
        try {
            File file = new File("C:\\Users\\larisabalc\\Desktop\\Luxembourg\\hartaLuxembourg.xml");
            document = builder.parse(file);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MapContent map = new MapContent();
        map.setTitle("Quickstart");

        document.getDocumentElement().normalize();
        NodeList nodes = document.getElementsByTagName("nodes");

        for(int i=0;i< nodes.getLength();i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                NodeList nodeDetails = node.getChildNodes();

                for (int j = 0; j < nodeDetails.getLength(); j++) {
                    Node detail = nodeDetails.item(j);
                    if (detail.getNodeType() == Node.ELEMENT_NODE) {
                        Element detailElement = (Element) detail;
                        String longitude = detailElement.getAttribute("longitude");
                        String latitude = detailElement.getAttribute("latitude");

                        if (Double.parseDouble(longitude) > maxLong)
                            maxLong = Double.parseDouble(longitude);
                        if (Double.parseDouble(longitude) < minLong)
                            minLong = Double.parseDouble(longitude);
                        if (Double.parseDouble(latitude) < minLat)
                            minLat = Double.parseDouble(latitude);
                        if (Double.parseDouble(latitude) > maxLat)
                            maxLat = Double.parseDouble(latitude);
                    }
                }
            }
        }
        for(int i=0;i< nodes.getLength();i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                NodeList nodeDetails = node.getChildNodes();

                for (int j = 0; j < nodeDetails.getLength(); j++) {
                    Node detail = nodeDetails.item(j);
                    if (detail.getNodeType() == Node.ELEMENT_NODE) {
                        Element detailElement = (Element) detail;
                        String longitude = detailElement.getAttribute("longitude");
                        String latitude = detailElement.getAttribute("latitude");
                        int mapWidth = 1250;
                        int mapHeight = 600;

                        double x = (Double.parseDouble(longitude) - minLong - 1) / (maxLong - minLong);
                        double y = (Double.parseDouble(latitude) - minLat - 1) / (maxLat - minLat);
                        addNode(x * mapWidth, y * mapHeight, Integer.parseInt(detailElement.getAttribute("id")));

                    }
                }
            }
        }
        NodeList arcs =  document.getElementsByTagName("arcs");
        for(int i=0;i< arcs.getLength();i++)
        {
            Node node = arcs.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                NodeList nodeDetails = node.getChildNodes();

                for(int j=0;j<nodeDetails.getLength();j++)
                {
                    Node detail = nodeDetails.item(j);
                    if(detail.getNodeType() == Node.ELEMENT_NODE) {
                        Element detailElement = (Element) detail;
                        org.example.Node start = null;
                        org.example.Node end = null;
                        String from = detailElement.getAttribute("from");
                        String to = detailElement.getAttribute("to");

                       start = listaNoduri.get(Integer.parseInt(from));
                       end = listaNoduri.get(Integer.parseInt(to));

                        String length = detailElement.getAttribute("length");
                        listaArce.add(new Arc(start, end,Integer.parseInt(length),Color.GREEN));

                    }
                }
            }
        }
        JFrame frame = new JFrame();
        MyPanel panel = new MyPanel();
        frame.add(panel);
        frame.setSize(1500, 1500);
        frame.setVisible(true);
    }

}