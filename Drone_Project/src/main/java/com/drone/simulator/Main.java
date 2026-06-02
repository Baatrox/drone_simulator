package com.drone.simulator;

import com.drone.simulator.client.DroneClient;
import com.drone.simulator.server.DroneServer;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("========================================");
            System.out.println(" Simulateur de Flotte de Drones");
            System.out.println("========================================");
            System.out.println("1. Mode Basique (Séquentiel)");
            System.out.println("2. Mode Concurrent (Threads)");
            System.out.println("3. Mode Réseau (3a=Serveur / 3b=Client)");
            System.out.println("0. Quitter");
            System.out.print("Choisissez un mode : ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    new SimulatorBasic().executeSimulation();
                    break;
                case 2:
                    new com.drone.simulator.concurrent.SimulatorConcurrent().executeSimulation();
                    break;
                case 3:
                    System.out.println("3a. Démarrer le Serveur");
                    System.out.println("3b. Démarrer le Client");
                    System.out.print("Votre choix : ");
                    String subChoice = scanner.nextLine().trim();
                    if (subChoice.equals("3a") || subChoice.equals("a")) {
                        Thread serverThread = new Thread(() -> new DroneServer().start());
                        serverThread.start();
                        System.out.println("Serveur démarré sur le port 5000.");
                        System.out.println("ATTENTION: Ne quittez pas le programme tant que");
                        System.out.println("le serveur est en cours d'exécution (Ctrl+C pour forcer l'arrêt).");
                    } else if (subChoice.equals("3b") || subChoice.equals("b")) {
                        new DroneClient().run();
                    } else {
                        System.out.println("Choix invalide.");
                    }
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }

            if (choice != 0) {
                System.out.println("\nAppuyez sur Entrée pour continuer...");
                scanner.nextLine();
            }

        } while (choice != 0);

        scanner.close();
    }
}
