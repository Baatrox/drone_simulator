package com.drone.simulator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class SimulatorMapTest {

    @Test
    void constructorInitializesAllCellsToDot() {
        SimulatorMap map = new SimulatorMap(5, 3);
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 5; x++) {
                assertEquals('.', map.getGrid()[y][x]);
            }
        }
    }

    @Test
    void getWidthAndHeight() {
        SimulatorMap map = new SimulatorMap(7, 11);
        assertEquals(7, map.getWidth());
        assertEquals(11, map.getHeight());
    }

    @Test
    void wrapCoordinatePositiveInBounds() {
        SimulatorMap map = new SimulatorMap(10, 10);
        assertEquals(5, map.wrapCoordinate(5, 10));
    }

    @Test
    void wrapCoordinateNegative() {
        SimulatorMap map = new SimulatorMap(10, 10);
        assertEquals(9, map.wrapCoordinate(-1, 10));
    }

    @Test
    void wrapCoordinateExceedsMax() {
        SimulatorMap map = new SimulatorMap(10, 10);
        assertEquals(0, map.wrapCoordinate(10, 10));
    }

    @Test
    void wrapCoordinateMultipleWraps() {
        SimulatorMap map = new SimulatorMap(10, 10);
        assertEquals(5, map.wrapCoordinate(25, 10));
    }

    @Test
    void wrapCoordinateNegativeMultiple() {
        SimulatorMap map = new SimulatorMap(10, 10);
        assertEquals(5, map.wrapCoordinate(-5, 10));
    }

    @Test
    void wrapCoordinateZeroIsZero() {
        SimulatorMap map = new SimulatorMap(10, 10);
        assertEquals(0, map.wrapCoordinate(0, 10));
    }

    @Test
    void isBlockedReturnsTrueForObstacle() {
        SimulatorMap map = new SimulatorMap(10, 10);
        map.getGrid()[2][3] = '#';
        assertTrue(map.isBlocked(3, 2));
    }

    @Test
    void isBlockedReturnsFalseForEmpty() {
        SimulatorMap map = new SimulatorMap(10, 10);
        assertFalse(map.isBlocked(3, 2));
    }

    @Test
    void isBlockedWrapsCoordinates() {
        SimulatorMap map = new SimulatorMap(10, 10);
        map.getGrid()[0][0] = '#';
        assertTrue(map.isBlocked(10, 0));
        assertTrue(map.isBlocked(0, 10));
    }

    @Test
    void addObstaclesDoesNotPlaceOnDroneStartCells() {
        SimulatorMap map = new SimulatorMap(100, 100);
        List<Drone> drones = new ArrayList<>();
        drones.add(new Drone(50, 50, 'N', ""));
        map.addObstacles(drones);
        assertNotEquals('#', map.getGrid()[50][50]);
    }

    @Test
    void placeDroneSetsDisplayChar() {
        SimulatorMap map = new SimulatorMap(10, 10);
        Drone d = new Drone(3, 4, 'N', "");
        map.placeDrone(d);
        assertEquals('^', map.getGrid()[4][3]);
    }

    @Test
    void removeDroneClearsCell() {
        SimulatorMap map = new SimulatorMap(10, 10);
        Drone d = new Drone(3, 4, 'N', "");
        map.placeDrone(d);
        map.removeDrone(d);
        assertEquals('.', map.getGrid()[4][3]);
    }

    @Test
    void addObstaclesPlacesCorrectPercentage() {
        SimulatorMap map = new SimulatorMap(20, 20);
        map.addObstacles(new ArrayList<>());
        int count = 0;
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                if (map.getGrid()[y][x] == '#') count++;
            }
        }
        assertEquals(80, count);
    }
}
