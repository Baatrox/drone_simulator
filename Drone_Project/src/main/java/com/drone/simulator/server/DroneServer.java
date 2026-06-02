package com.drone.simulator.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DroneServer {

    public static final int PORT = 5000;

    public static void main(String[] args) {
        DroneServer server = new DroneServer();
        server.start();
    }

    public void start() {
        System.out.println("Serveur Drone démarré sur le port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientSocket.setSoTimeout(30000);
                System.out.println("Nouveau client connecté : " + clientSocket.getInetAddress());
                ServerConnectionHandler handler = new ServerConnectionHandler(clientSocket);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.out.println("Erreur du serveur : " + e.getMessage());
        }
    }
}
