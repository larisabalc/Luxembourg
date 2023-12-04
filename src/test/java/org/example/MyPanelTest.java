package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

import static org.junit.Assert.*;

public class MyPanelTest {

    private MyPanel myPanel;
    private Robot robot;

    @Before
    public void setUp() throws AWTException {
        myPanel = new MyPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(myPanel);
        frame.setSize(400, 400);
        frame.setVisible(true);

        MyPanel.nodeList.clear();
        MyPanel.archList.clear();
        MyPanel.archPath.clear();
        MyPanel.existsPath = false;
        MyPanel.nodesClicked = 0;

        robot = new Robot();
    }

    @Test
    public void testAddNode() {
        myPanel.addNode(50, 50, 1);
        assertEquals(1, MyPanel.nodeList.size());
        assertNotNull(MyPanel.nodeList.get(1));
    }

    @Test
    public void testGetClosestNode() {
        myPanel.addNode(50, 50, 1);
        myPanel.addNode(100, 100, 2);

        Node closestNode = myPanel.getClosestNode(70, 70);

        assertNotNull(closestNode);
        assertEquals(1, closestNode.getNumber());
    }

    @Test
    public void testMousePressed() {
        myPanel.addNode(50, 50, 1);
        simulateMousePress(myPanel, 50, 50);

        assertEquals(1, MyPanel.nodesClicked);
    }

    @Test
    public void testMouseReleased() {
        myPanel.addNode(50, 50, 1);
        myPanel.addNode(100, 100, 2);
        myPanel.addNode(150, 150, 3);
        MyPanel.archList.add(new Arc(MyPanel.nodeList.get(1), MyPanel.nodeList.get(2), 0, Color.BLACK));

        simulateMousePress(myPanel, 50, 50);
        simulateMouseRelease(myPanel, 50, 50);
        simulateMousePress(myPanel, 100, 100);
        simulateMouseRelease(myPanel, 150, 150);

        assertTrue(MyPanel.existsPath);
        assertEquals(0, MyPanel.nodesClicked);
        assertEquals(Color.RED, MyPanel.startNode.getColor());
        assertEquals(Color.RED, MyPanel.endNode.getColor());
        assertEquals(1, MyPanel.archPath.size());
    }

    private void simulateMousePress(Component component, int x, int y) {
        Point componentLocation = component.getLocationOnScreen();
        robot.mouseMove(componentLocation.x + x, componentLocation.y + y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(1000);
    }

    private void simulateMouseRelease(Component component, int x, int y) {
        Point componentLocation = component.getLocationOnScreen();
        robot.mouseMove(componentLocation.x + x, componentLocation.y + y);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(1000);
    }
}
