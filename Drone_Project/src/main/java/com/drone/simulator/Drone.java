package com.drone.simulator;

public class Drone {

    private int x;
    private int y;
    private char orientation;
    private String commands;

    public Drone(int x, int y, char orientation, String commands) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.commands = commands;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getOrientation() {
        return orientation;
    }

    public String getCommands() {
        return commands;
    }

    public void move(char command, SimulatorMap map) {
        synchronized (map) {
            switch (command) {
                case 'L':
                    turnLeft();
                    break;
                case 'R':
                    turnRight();
                    break;
                case 'M':
                    moveForward(map);
                    return;
                case 'B':
                    moveBackward(map);
                    return;
            }
            map.getGrid()[y][x] = getDisplayChar();
        }
    }

    public void turnLeft() {
        switch (orientation) {
            case 'N':
                orientation = 'W';
                break;
            case 'W':
                orientation = 'S';
                break;
            case 'S':
                orientation = 'E';
                break;
            case 'E':
                orientation = 'N';
                break;
        }
    }

    public void turnRight() {
        switch (orientation) {
            case 'N':
                orientation = 'E';
                break;
            case 'E':
                orientation = 'S';
                break;
            case 'S':
                orientation = 'W';
                break;
            case 'W':
                orientation = 'N';
                break;
        }
    }

    public void moveForward(SimulatorMap map) {
        synchronized (map) {
            int newX = x;
            int newY = y;
            switch (orientation) {
                case 'N':
                    newY = y + 1;
                    break;
                case 'S':
                    newY = y - 1;
                    break;
                case 'E':
                    newX = x + 1;
                    break;
                case 'W':
                    newX = x - 1;
                    break;
            }
            newX = map.wrapCoordinate(newX, map.getWidth());
            newY = map.wrapCoordinate(newY, map.getHeight());

            map.getGrid()[y][x] = '.';

            if (!map.isBlocked(newX, newY) && map.getGrid()[newY][newX] == '.') {
                x = newX;
                y = newY;
            }

            map.getGrid()[y][x] = getDisplayChar();
        }
    }

    public void moveBackward(SimulatorMap map) {
        synchronized (map) {
            int newX = x;
            int newY = y;
            switch (orientation) {
                case 'N':
                    newY = y - 1;
                    break;
                case 'S':
                    newY = y + 1;
                    break;
                case 'E':
                    newX = x - 1;
                    break;
                case 'W':
                    newX = x + 1;
                    break;
            }
            newX = map.wrapCoordinate(newX, map.getWidth());
            newY = map.wrapCoordinate(newY, map.getHeight());

            map.getGrid()[y][x] = '.';

            if (!map.isBlocked(newX, newY) && map.getGrid()[newY][newX] == '.') {
                x = newX;
                y = newY;
            }

            map.getGrid()[y][x] = getDisplayChar();
        }
    }

    public String getPosition() {
        return x + " " + y + " " + orientation;
    }

    public char getDisplayChar() {
        switch (orientation) {
            case 'N':
                return '^';
            case 'S':
                return 'v';
            case 'E':
                return '>';
            case 'W':
                return '<';
            default:
                return '?';
        }
    }
}
