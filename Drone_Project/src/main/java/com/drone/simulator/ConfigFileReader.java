package com.drone.simulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigFileReader {

    public static class SimulationConfig {

        private int width;
        private int height;
        private List<Drone> drones;

        public SimulationConfig(int width, int height, List<Drone> drones) {
            this.width = width;
            this.height = height;
            this.drones = drones;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public List<Drone> getDrones() {
            return drones;
        }
    }

    public SimulationConfig readConfiguration(String filename) throws IOException {
        List<Drone> drones = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            if (line == null) {
                throw new IOException("Fichier vide");
            }
            int[] dims = parseMapDimensions(line);
            int width = dims[0];
            int height = dims[1];

            line = reader.readLine();
            if (line == null) {
                throw new IOException("Nombre de drones manquant");
            }
            int numDrones = Integer.parseInt(line.trim());

            for (int i = 0; i < numDrones; i++) {
                line = reader.readLine();
                if (line == null) {
                    throw new IOException("Données du drone " + i + " manquantes");
                }
                drones.add(parseDroneData(line));
            }

            return new SimulationConfig(width, height, drones);
        }
    }

    public int[] parseMapDimensions(String line) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Dimensions de carte invalides : " + line);
        }
        try {
            int width = Integer.parseInt(parts[0]);
            int height = Integer.parseInt(parts[1]);
            return new int[]{width, height};
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Nombre invalide dans les dimensions : " + line, e);
        }
    }

    public Drone parseDroneData(String line) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length < 4) {
            throw new IllegalArgumentException("Données de drone invalides : " + line);
        }
        try {
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            char orientation = parts[2].charAt(0);
            String commands = parts[3];
            return new Drone(x, y, orientation, commands);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Nombre invalide dans les données du drone : " + line, e);
        }
    }
}
