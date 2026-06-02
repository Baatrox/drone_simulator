package com.drone.simulator.concurrent;

import com.drone.simulator.Drone;
import com.drone.simulator.SimulatorMap;

public class DroneWorker extends Thread {

    private Drone drone;
    private SimulatorMap map;

    public DroneWorker(Drone drone, SimulatorMap map) {
        this.drone = drone;
        this.map = map;
    }

    @Override
    public void run() {
        String commands = drone.getCommands();
        for (int i = 0; i < commands.length(); i++) {
            char command = commands.charAt(i);

            drone.move(command, map);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
