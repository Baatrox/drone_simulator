package com.drone.simulator.server;

import com.drone.simulator.Drone;
import com.drone.simulator.SimulatorMap;
import com.drone.simulator.concurrent.DisplayWorker;
import com.drone.simulator.concurrent.DroneWorker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ServerConnectionHandler implements Runnable {

    private Socket clientSocket;

    public ServerConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String line = in.readLine();
            if (line == null) {
                out.println("ERROR");
                return;
            }
            String[] dims = line.trim().split("\\s+");
            int width = Integer.parseInt(dims[0]);
            int height = Integer.parseInt(dims[1]);

            line = in.readLine();
            if (line == null) {
                out.println("ERROR");
                return;
            }
            int numDrones = Integer.parseInt(line.trim());

            List<Drone> drones = Collections.synchronizedList(new ArrayList<>());
            for (int i = 0; i < numDrones; i++) {
                line = in.readLine();
                if (line == null) {
                    break;
                }
                String[] parts = line.trim().split("\\s+");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                char orientation = parts[2].charAt(0);
                String commands = parts[3];
                drones.add(new Drone(x, y, orientation, commands));
            }

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

            out.println("SUCCESS");
            for (Drone drone : drones) {
                out.println(drone.getPosition());
            }

            out.println("MAP");
            char[][] grid = map.getGrid();
            for (int y = height - 1; y >= 0; y--) {
                StringBuilder row = new StringBuilder();
                for (int x = 0; x < width; x++) {
                    if (x > 0) row.append(' ');
                    row.append(grid[y][x]);
                }
                out.println(row);
            }

        } catch (IOException e) {
            System.out.println("Erreur de communication : " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erreur de format des données : " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }
}
