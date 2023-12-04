package org.example;

import org.junit.Test;

import java.util.HashMap;
import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class XmlDeserializeTest
{
    @Test
    public void testXmlDeserialization() {
        String xmlFilePath = "hartaLuxembourg.xml";

        double expectedMinLong = 4945029.0;
        double expectedMinLat = 573929.0;
        double expectedMaxLong = 5018275.0;
        double expectedMaxLat = 652685.0;

        int expectedNodeListSize = 42314;
        int expectedArchListSize = 100358;

        XmlDeserializer xmlDeserializer = new XmlDeserializer();

        HashMap<Integer, Node> nodeList = new HashMap<>();
        Vector<Arc> archList = new Vector<>();

        xmlDeserializer.deserialize(xmlFilePath, nodeList, archList);

        assertEquals(expectedMinLong, XmlDeserializer.minLong, 0);
        assertEquals(expectedMinLat, XmlDeserializer.minLat, 0);
        assertEquals(expectedMaxLong, XmlDeserializer.maxLong, 0);
        assertEquals(expectedMaxLat, XmlDeserializer.maxLat, 0);

        assertEquals(expectedNodeListSize, XmlDeserializer.nodeList.size(), 0);
        assertEquals(expectedArchListSize, archList.size(), 0);
    }
}
