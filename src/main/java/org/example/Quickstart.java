package org.example;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
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
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.locationtech.jts.awt.PointShapeFactory;
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

/**
 * Prompts the user for a shapefile and displays the contents on the screen in a map frame.
 *
 * <p>This is the GeoTools Quickstart application used in documentationa and tutorials. *
 */
public class Quickstart {

    static Layer addPoint(double latitude, double longitude) {
        SimpleFeatureTypeBuilder b = new SimpleFeatureTypeBuilder();

        b.setName("MyFeatureType");
        b.setCRS(DefaultGeographicCRS.WGS84);
        b.add("location", Point.class);
        // building the type
        final SimpleFeatureType TYPE = b.buildFeatureType();

        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
       org.locationtech.jts.geom.Point point = geometryFactory.createPoint(new Coordinate( latitude, longitude));
        featureBuilder.add(point);
        SimpleFeature feature = featureBuilder.buildFeature(null);
        DefaultFeatureCollection featureCollection = new DefaultFeatureCollection("internal", TYPE);
        featureCollection.add(feature);
        Style style = SLD.createPointStyle("square", Color.red, Color.red, 1.0f, 8.0f);

        Layer layer = new FeatureLayer(featureCollection, style);
        return layer;
    }
    private static final Logger LOGGER = org.geotools.util.logging.Logging.getLogger(Quickstart.class);
    /**
     * GeoTools Quickstart demo application. Prompts the user for a shapefile and displays its
     * contents on the screen in a map frame
     */
    public static void main(String[] args) throws Exception {
        // display a data store file chooser dialog for shapefiles
        LOGGER.info( "Quickstart");
        LOGGER.config( "Welcome Developers");
        LOGGER.info("java.util.logging.config.file="+System.getProperty("java.util.logging.config.file"));
        File file = JFileDataStoreChooser.showOpenFile("shp", null);
        if (file == null) {
            return;
        }
        LOGGER.config("File selected "+file);

        FileDataStore store = FileDataStoreFinder.getDataStore(file);
        SimpleFeatureSource featureSource = store.getFeatureSource();

        // Create a map content and add our shapefile to it
        MapContent map = new MapContent();
        map.setTitle("Quickstart");





        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document document = null;
        try {
            File file1 = new File("C:\\Users\\larisabalc\\Desktop\\tutorial\\hartaLuxembourg.xml");
            document = builder.parse(file1);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        document.getDocumentElement().normalize();
        NodeList nodes = document.getElementsByTagName("nodes");
        for(int i=0;i< nodes.getLength();i++)
        {
            org.w3c.dom.Node node = nodes.item(i);
            if(node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                NodeList nodeDetails = node.getChildNodes();


                for(int j=0;j<nodeDetails.getLength();j++)
                {
                    org.w3c.dom.Node detail = nodeDetails.item(j);
                    if(detail.getNodeType() == Node.ELEMENT_NODE) {
                        Element detailElement = (Element) detail;
                        System.out.println("id: "+detailElement.getAttribute("id") +" longitude: "+detailElement.getAttribute("longitude") +" latitude: "+detailElement.getAttribute("latitude"));
                        String longitude = new StringBuilder(detailElement.getAttribute("longitude")).insert(detailElement.getAttribute("longitude").length() - 5, '.').toString();
                        String latitude = new StringBuilder(detailElement.getAttribute("latitude")).insert(detailElement.getAttribute("latitude").length() - 5, '.').toString();
                        Layer pLayer = addPoint(Double.parseDouble(longitude),Double.parseDouble(latitude));
                        map.addLayer(pLayer);
                       // addNode(point.getX(),point.getY(),Integer.parseInt(detailElement.getAttribute("id")));
                    }


                }
            }
        }
        Style style = SLD.createSimpleStyle(featureSource.getSchema());
        Layer layer = new FeatureLayer(featureSource, style);

        map.addLayer(layer);
        // Now display the map
        JMapFrame.showMap(map);



    }
}
