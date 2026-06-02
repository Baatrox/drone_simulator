package com.drone.simulator;

import java.io.IOException;
import java.util.List;

public class SimulatorBasic {

    public static void main(String[] args) {
        SimulatorBasic simulator = new SimulatorBasic();
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
        List<Drone> drones = config.getDrones();

        System.out.println("Résultats du Simulateur Basique");
        System.out.println("================================");
        System.out.println("Initialisation de la carte (" + width + "x" + height + ")...\n");

        SimulatorMap map = new SimulatorMap(width, height);
        map.addObstacles(drones);

        for (int i = 0; i < drones.size(); i++) {
            Drone drone = drones.get(i);
            String commands = drone.getCommands();

            System.out.println("Drone " + i + " : Position initiale : "
                    + drone.getX() + " " + drone.getY() + " "
                    + drone.getOrientation() + " | Commandes : " + commands);
            System.out.println("===========================================");

            map.placeDrone(drone);
            map.displayMap();
            System.out.println();

            for (int c = 0; c < commands.length(); c++) {
                char command = commands.charAt(c);
                drone.move(command, map);
                System.out.println("Position du drone " + i + " : " + drone.getPosition());
            }

            map.displayMap();
            map.removeDrone(drone);
            System.out.println();
        }

        System.out.println("Positions finales des drones:");
        for (int i = 0; i < drones.size(); i++) {
            System.out.println("Drone " + i + " : " + drones.get(i).getPosition());
        }
    }
}
