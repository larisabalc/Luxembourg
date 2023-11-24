package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

public class XmlDeserializer extends MyPanel{
    static double minLong = Double.MAX_VALUE;
    static double minLat = Double.MAX_VALUE;
    static double maxLong = Double.MIN_VALUE;
    static double maxLat = Double.MIN_VALUE;
    public void deserialize(String path, HashMap<Integer, Node> nodeList, Vector<Arc> archList) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document document;
        try {
            File file = new File(path);
            document = builder.parse(file);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        document.getDocumentElement().normalize();
        NodeList nodes = document.getElementsByTagName("nodes");

        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                NodeList nodeDetails = node.getChildNodes();

                for (int j = 0; j < nodeDetails.getLength(); j++) {
                    org.w3c.dom.Node detail = nodeDetails.item(j);
                    if (detail.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
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
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                NodeList nodeDetails = node.getChildNodes();

                for (int j = 0; j < nodeDetails.getLength(); j++) {
                    org.w3c.dom.Node detail = nodeDetails.item(j);
                    if (detail.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
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
        NodeList arcs = document.getElementsByTagName("arcs");
        for (int i = 0; i < arcs.getLength(); i++) {
            org.w3c.dom.Node node = arcs.item(i);
            if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                NodeList nodeDetails = node.getChildNodes();

                for (int j = 0; j < nodeDetails.getLength(); j++) {
                    org.w3c.dom.Node detail = nodeDetails.item(j);
                    if (detail.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                        Element detailElement = (Element) detail;
                        String from = detailElement.getAttribute("from");
                        String to = detailElement.getAttribute("to");

                        org.example.Node start = nodeList.get(Integer.parseInt(from));
                        org.example.Node end = nodeList.get(Integer.parseInt(to));

                        String length = detailElement.getAttribute("length");
                        archList.add(new Arc(start, end, Integer.parseInt(length), Color.GREEN));
                    }
                }
            }
        }
    }
}
