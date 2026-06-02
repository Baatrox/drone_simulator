package com.drone.simulator.concurrent;

import com.drone.simulator.SimulatorMap;

public class DisplayWorker extends Thread {

    private SimulatorMap map;
    private volatile boolean running = true;
    private long lastDisplayTime = 0;

    public DisplayWorker(SimulatorMap map) {
        this.map = map;
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
                }
                lastDisplayTime = now;
            }
        }
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
