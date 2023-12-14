package org.example;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Vector;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XmlDeserializeTest
{
    @ParameterizedTest
    @MethodSource("xmlFilePathProvider")
    public void testXmlDeserialization(String xmlFilePath) {
        assertTrue(isValidXmlFilePath(xmlFilePath), "Invalid XML file path: " + xmlFilePath);

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

    private static Stream<Arguments> xmlFilePathProvider() {
        return Stream.of(
                Arguments.of("hartaLuxembourg.xml"),
                Arguments.of("MARIAN")
        );
    }

    private static boolean isValidXmlFilePath(String xmlFilePath) {
        return Files.exists(Path.of(xmlFilePath));
    }
}
