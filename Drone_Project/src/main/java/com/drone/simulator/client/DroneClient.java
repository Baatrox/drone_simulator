package com.drone.simulator.client;

import com.drone.simulator.Drone;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class DroneClient {

    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        DroneClient client = new DroneClient();
        client.run();
    }

    public void run() {
        try {
            FileConfigurationProvider configProvider = new FileConfigurationProvider("drones.txt");

            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            socket.setSoTimeout(30000);
            try (socket;
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(socket.getInputStream()))) {

                out.println(configProvider.getMapWidth() + " " + configProvider.getMapHeight());
                List<Drone> drones = configProvider.getDrones();
                out.println(drones.size());

                for (Drone drone : drones) {
                    out.println(drone.getX() + " " + drone.getY() + " "
                            + drone.getOrientation() + " " + drone.getCommands());
                }

                String response = in.readLine();
                if ("SUCCESS".equals(response)) {
                    System.out.println("Réponse du serveur :");
                    for (int i = 0; i < drones.size(); i++) {
                        String position = in.readLine();
                        if (position != null) {
                            System.out.println("Drone " + i + " : " + position);
                        }
                    }

                    String mapHeader = in.readLine();
                    if ("MAP".equals(mapHeader)) {
                        int height = configProvider.getMapHeight();
                        System.out.println("\nCarte des positions finales :");
                        for (int y = 0; y < height; y++) {
                            String row = in.readLine();
                            if (row != null) {
                                System.out.println(row);
                            }
                        }
                    }
                } else {
                    System.out.println("Erreur retournée par le serveur.");
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
    }
}
