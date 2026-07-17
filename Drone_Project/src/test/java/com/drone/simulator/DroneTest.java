package com.drone.simulator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DroneTest {

    @Test
    void turnLeftFromNorthGoesWest() {
        Drone d = new Drone(0, 0, 'N', "");
        d.turnLeft();
        assertEquals('W', d.getOrientation());
    }

    @Test
    void turnLeftFromWestGoesSouth() {
        Drone d = new Drone(0, 0, 'W', "");
        d.turnLeft();
        assertEquals('S', d.getOrientation());
    }

    @Test
    void turnLeftFromSouthGoesEast() {
        Drone d = new Drone(0, 0, 'S', "");
        d.turnLeft();
        assertEquals('E', d.getOrientation());
    }

    @Test
    void turnLeftFromEastGoesNorth() {
        Drone d = new Drone(0, 0, 'E', "");
        d.turnLeft();
        assertEquals('N', d.getOrientation());
    }

    @Test
    void turnRightFromNorthGoesEast() {
        Drone d = new Drone(0, 0, 'N', "");
        d.turnRight();
        assertEquals('E', d.getOrientation());
    }

    @Test
    void turnRightFromEastGoesSouth() {
        Drone d = new Drone(0, 0, 'E', "");
        d.turnRight();
        assertEquals('S', d.getOrientation());
    }

    @Test
    void turnRightFromSouthGoesWest() {
        Drone d = new Drone(0, 0, 'S', "");
        d.turnRight();
        assertEquals('W', d.getOrientation());
    }

    @Test
    void turnRightFromWestGoesNorth() {
        Drone d = new Drone(0, 0, 'W', "");
        d.turnRight();
        assertEquals('N', d.getOrientation());
    }

    @Test
    void moveForwardEastIncrementsX() {
        SimulatorMap map = new SimulatorMap(10, 10);
        Drone d = new Drone(0, 0, 'E', "");
        d.moveForward(map);
        assertEquals(1, d.getX());
        assertEquals(0, d.getY());
    }

    @Test
    void moveForwardWestDecrementsX() {
        SimulatorMap map = new SimulatorMap(10, 10);
        Drone d = new Drone(1, 0, 'W', "");
        d.moveForward(map);
        assertEquals(0, d.getX());
        assertEquals(0, d.getY());
    }

    @Test
    void moveForwardNorthIncrementsY() {
        SimulatorMap map = new SimulatorMap(10, 10);
        Drone d = new Drone(0, 0, 'N', "");
        d.moveForward(map);
        assertEquals(0, d.getX());
        assertEquals(1, d.getY());
    }

    @Test
    void moveForwardSouthDecrementsY() {
        SimulatorMap map = new SimulatorMap(10, 10);
        Drone d = new Drone(0, 1, 'S', "");
        d.moveForward(map);
        assertEquals(0, d.getX());
        assertEquals(0, d.getY());
    }

    @Test
    void moveBackwardNorthDecrementsY() {
        SimulatorMap map = new SimulatorMap(10, 10);
        Drone d = new Drone(0, 1, 'N', "");
        d.moveBackward(map);
        assertEquals(0, d.getX());
        assertEquals(0, d.getY());
    }

    @Test
    void moveBackwardEastDecrementsX() {
        SimulatorMap map = new SimulatorMap(10, 10);
        Drone d = new Drone(1, 0, 'E', "");
        d.moveBackward(map);
        assertEquals(0, d.getX());
        assertEquals(0, d.getY());
    }

    @Test
    void moveBackwardWestIncrementsX() {
        SimulatorMap map = new SimulatorMap(10, 10);
        Drone d = new Drone(0, 0, 'W', "");
        d.moveBackward(map);
        assertEquals(1, d.getX());
        assertEquals(0, d.getY());
    }

    @Test
    void moveBackwardSouthIncrementsY() {
        SimulatorMap map = new SimulatorMap(10, 10);
        Drone d = new Drone(0, 0, 'S', "");
        d.moveBackward(map);
        assertEquals(0, d.getX());
        assertEquals(1, d.getY());
    }

    @Test
    void moveForwardWrapsAtEastEdge() {
        SimulatorMap map = new SimulatorMap(10, 10);
        Drone d = new Drone(9, 0, 'E', "");
        d.moveForward(map);
        assertEquals(0, d.getX());
    }

    @Test
    void moveForwardWrapsAtWestEdge() {
        SimulatorMap map = new SimulatorMap(10, 10);
        Drone d = new Drone(0, 0, 'W', "");
        d.moveForward(map);
        assertEquals(9, d.getX());
    }

    @Test
    void moveForwardWrapsAtNorthEdge() {
        SimulatorMap map = new SimulatorMap(10, 10);
        Drone d = new Drone(0, 9, 'N', "");
        d.moveForward(map);
        assertEquals(0, d.getY());
    }

    @Test
    void moveForwardWrapsAtSouthEdge() {
        SimulatorMap map = new SimulatorMap(10, 10);
        Drone d = new Drone(0, 0, 'S', "");
        d.moveForward(map);
        assertEquals(9, d.getY());
    }

    @Test
    void moveForwardBlockedByObstacleStaysInPlace() {
        SimulatorMap map = new SimulatorMap(10, 10);
        map.getGrid()[0][1] = '#';
        Drone d = new Drone(0, 0, 'E', "");
        d.moveForward(map);
        assertEquals(0, d.getX());
        assertEquals(0, d.getY());
    }

    @Test
    void moveBackwardBlockedByObstacleStaysInPlace() {
        SimulatorMap map = new SimulatorMap(10, 10);
        map.getGrid()[0][0] = '#';
        Drone d = new Drone(1, 0, 'E', "");
        d.moveBackward(map);
        assertEquals(1, d.getX());
        assertEquals(0, d.getY());
    }

    @Test
    void getPositionReturnsFormattedString() {
        Drone d = new Drone(3, 7, 'N', "");
        assertEquals("3 7 N", d.getPosition());
    }

    @Test
    void getDisplayCharNorth() {
        Drone d = new Drone(0, 0, 'N', "");
        assertEquals('^', d.getDisplayChar());
    }

    @Test
    void getDisplayCharSouth() {
        Drone d = new Drone(0, 0, 'S', "");
        assertEquals('v', d.getDisplayChar());
    }

    @Test
    void getDisplayCharEast() {
        Drone d = new Drone(0, 0, 'E', "");
        assertEquals('>', d.getDisplayChar());
    }

    @Test
    void getDisplayCharWest() {
        Drone d = new Drone(0, 0, 'W', "");
        assertEquals('<', d.getDisplayChar());
    }

    @Test
    void executeTurnLeftCommand() {
        SimulatorMap map = new SimulatorMap(10, 10);
        Drone d = new Drone(0, 0, 'N', "");
        d.move('L', map);
        assertEquals('W', d.getOrientation());
    }

    @Test
    void executeTurnRightCommand() {
        SimulatorMap map = new SimulatorMap(10, 10);
        Drone d = new Drone(0, 0, 'N', "");
        d.move('R', map);
        assertEquals('E', d.getOrientation());
    }

    @Test
    void executeMoveForwardCommand() {
        SimulatorMap map = new SimulatorMap(10, 10);
        Drone d = new Drone(0, 0, 'E', "");
        d.move('M', map);
        assertEquals(1, d.getX());
    }

    @Test
    void executeMoveBackwardCommand() {
        SimulatorMap map = new SimulatorMap(10, 10);
        Drone d = new Drone(1, 0, 'E', "");
        d.move('B', map);
        assertEquals(0, d.getX());
    }

    @Test
    void constructorStoresCommands() {
        Drone d = new Drone(0, 0, 'N', "LMR");
        assertEquals("LMR", d.getCommands());
    }

    @Test
    void gettersReturnInitialValues() {
        Drone d = new Drone(5, 8, 'S', "M");
        assertEquals(5, d.getX());
        assertEquals(8, d.getY());
        assertEquals('S', d.getOrientation());
    }
}
