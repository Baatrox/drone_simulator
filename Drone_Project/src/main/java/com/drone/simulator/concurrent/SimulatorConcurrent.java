package com.drone.simulator.concurrent;

import com.drone.simulator.ConfigFileReader;
import com.drone.simulator.Drone;
import com.drone.simulator.SimulatorMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SimulatorConcurrent {

    public static void main(String[] args) {
        SimulatorConcurrent simulator = new SimulatorConcurrent();
        simulator.executeSimulation();
    }

    public void executeSimulation() {
        ConfigFileReader reader = new ConfigFileReader();
        ConfigFileReader.SimulationConfig config;
        try {
            config = reader.readConfiguration("drones.txt");
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier : " + e.getMessage());
            return;
        }

        int width = config.getWidth();
        int height = config.getHeight();
        List<Drone> drones = Collections.synchronizedList(config.getDrones());

        SimulatorMap map = new SimulatorMap(width, height);
        map.addObstacles(new ArrayList<>(drones));

        for (Drone drone : drones) {
            map.placeDrone(drone);
        }

        DisplayWorker displayWorker = new DisplayWorker(map, drones);
        displayWorker.start();

        List<DroneWorker> workers = new ArrayList<>();
        for (Drone drone : drones) {
            DroneWorker worker = new DroneWorker(drone, map);
            workers.add(worker);
            worker.start();
        }

        for (DroneWorker worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        displayWorker.stopDisplay();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n\n");

        if (System.console() != null) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        synchronized (map) {
            map.displayMap();
        }

        System.out.println("\n\n\nPositions finales des drones:");
        for (int i = 0; i < drones.size(); i++) {
            System.out.println("Drone " + i + " : " + drones.get(i).getPosition());
        }
    }
}
