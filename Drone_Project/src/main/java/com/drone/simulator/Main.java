package com.drone.simulator;

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
            System.out.println("3. Mode Serveur (Réseau)");
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
                    new DroneServer().start();
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
