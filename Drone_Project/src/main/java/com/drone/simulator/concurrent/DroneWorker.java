package com.drone.simulator.concurrent;

import com.drone.simulator.Drone;
import com.drone.simulator.SimulatorMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DroneWorker extends Thread {

    private Drone drone;
    private SimulatorMap map;
    private ReentrantLock lock;
    private Condition condition;

    public DroneWorker(Drone drone, SimulatorMap map, ReentrantLock lock, Condition condition) {
        this.drone = drone;
        this.map = map;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        String commands = drone.getCommands();
        for (int i = 0; i < commands.length(); i++) {
            char command = commands.charAt(i);

            lock.lock();
            try {
                drone.move(command, map);
                condition.signalAll();
            } finally {
                lock.unlock();
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
