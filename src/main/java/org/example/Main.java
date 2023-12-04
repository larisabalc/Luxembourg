package org.example;

import javax.swing.*;

public class Main extends MyPanel {
    public static void main(String[] args) {
        XmlDeserializer xmlDeserializer = new XmlDeserializer();
        xmlDeserializer.deserialize("hartaLuxembourg.xml", nodeList, archList);

        JFrame frame = new JFrame();
        MyPanel panel = new MyPanel();
        frame.add(panel);
        frame.setSize(1500, 1500);
        frame.setVisible(true);
    }

}