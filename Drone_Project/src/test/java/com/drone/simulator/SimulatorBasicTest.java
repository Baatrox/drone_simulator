package com.drone.simulator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class SimulatorBasicTest {

    @Test
    void executeSimulationPrintsExpectedHeaders() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        try {
            SimulatorBasic simulator = new SimulatorBasic();
            simulator.executeSimulation();
            String output = out.toString();
            assertTrue(output.contains("Initialisation de la carte"));
            assertTrue(output.contains("Positions finales des drones"));
        } finally {
            System.setOut(System.out);
        }
    }

    @Test
    void executeSimulationPrintsEachDronePosition() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        try {
            SimulatorBasic simulator = new SimulatorBasic();
            simulator.executeSimulation();
            String output = out.toString();
            assertTrue(output.contains("Position du drone 0"));
            assertTrue(output.contains("Position du drone 1"));
        } finally {
            System.setOut(System.out);
        }
    }

    @Test
    void executeSimulationFinalPositionsAreFormatted() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        try {
            SimulatorBasic simulator = new SimulatorBasic();
            simulator.executeSimulation();
            String output = out.toString();
            String[] lines = output.split("\n");
            boolean foundDrone0 = false;
            boolean foundDrone1 = false;
            for (String line : lines) {
                if (line.matches("Drone 0 : \\d+ \\d+ [NESW]")) foundDrone0 = true;
                if (line.matches("Drone 1 : \\d+ \\d+ [NESW]")) foundDrone1 = true;
            }
            assertTrue(foundDrone0, "Drone 0 final position not found in expected format");
            assertTrue(foundDrone1, "Drone 1 final position not found in expected format");
        } finally {
            System.setOut(System.out);
        }
    }
}
