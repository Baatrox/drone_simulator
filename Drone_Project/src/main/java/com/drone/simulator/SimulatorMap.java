package com.drone.simulator;

import java.util.List;
import java.util.Random;

public class SimulatorMap {

    private int width;
    private int height;
    private char[][] grid;

    public SimulatorMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new char[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = '.';
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public char[][] getGrid() {
        return grid;
    }

    public void addObstacles(List<Drone> drones) {
        Random random = new Random();
        int totalCells = width * height;
        int obstacleCount = (int) (totalCells * 0.2);

        boolean[][] occupied = new boolean[height][width];
        for (Drone drone : drones) {
            int dx = drone.getX();
            int dy = drone.getY();
            dx = wrapCoordinate(dx, width);
            dy = wrapCoordinate(dy, height);
            occupied[dy][dx] = true;
        }

        int placed = 0;
        while (placed < obstacleCount) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            if (!occupied[y][x]) {
                grid[y][x] = '#';
                occupied[y][x] = true;
                placed++;
            }
        }
    }

    public void displayMap() {
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                System.out.print(grid[y][x] + " ");
            }
            System.out.println();
        }
    }

    public boolean isBlocked(int x, int y) {
        x = wrapCoordinate(x, width);
        y = wrapCoordinate(y, height);
        return grid[y][x] == '#';
    }

    public void placeDrone(Drone drone) {
        int px = wrapCoordinate(drone.getX(), width);
        int py = wrapCoordinate(drone.getY(), height);
        grid[py][px] = drone.getDisplayChar();
    }

    public void removeDrone(Drone drone) {
        int px = wrapCoordinate(drone.getX(), width);
        int py = wrapCoordinate(drone.getY(), height);
        char c = grid[py][px];
        if (c == '^' || c == 'v' || c == '>' || c == '<') {
            grid[py][px] = '.';
        }
    }

    public int wrapCoordinate(int coord, int max) {
        return ((coord % max) + max) % max;
    }
}
