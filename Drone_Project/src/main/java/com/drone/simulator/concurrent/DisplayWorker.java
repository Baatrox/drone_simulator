package com.drone.simulator.concurrent;

import com.drone.simulator.Drone;
import com.drone.simulator.SimulatorMap;
import java.util.List;

public class DisplayWorker extends Thread {

    private SimulatorMap map;
    private List<Drone> drones;
    private volatile boolean running = true;
    private long lastDisplayTime = 0;

    public DisplayWorker(SimulatorMap map, List<Drone> drones) {
        this.map = map;
        this.drones = drones;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            long now = System.currentTimeMillis();
            if (now - lastDisplayTime >= 500) {
                synchronized (map) {
                    clearScreen();
                    map.displayMap();
                    displayDroneStatus();
                }
                lastDisplayTime = now;
            }
        }
    }

    private void displayDroneStatus() {
        System.out.println();
        synchronized (drones) {
            for (int i = 0; i < drones.size(); i++) {
                Drone d = drones.get(i);
                System.out.print("Drone " + i + ": (" + d.getX() + "," + d.getY() + ") " + d.getOrientation());
                if (i < drones.size() - 1) {
                    System.out.print("  |  ");
                }
            }
        }
        System.out.println();
    }

    public void stopDisplay() {
        running = false;
        interrupt();
    }

    private void clearScreen() {
        if (System.console() != null) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }
}
