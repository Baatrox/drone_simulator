package com.drone.simulator.concurrent;

import com.drone.simulator.SimulatorMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DisplayWorker extends Thread {

    private SimulatorMap map;
    private ReentrantLock lock;
    private Condition condition;
    private volatile boolean running = true;

    public DisplayWorker(SimulatorMap map, ReentrantLock lock, Condition condition) {
        this.map = map;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        while (running) {
            lock.lock();
            try {
                condition.await(100, TimeUnit.MILLISECONDS);
                clearScreen();
                map.displayMap();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } finally {
                lock.unlock();
            }
        }
    }

    public void stopDisplay() {
        running = false;
        interrupt();
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
