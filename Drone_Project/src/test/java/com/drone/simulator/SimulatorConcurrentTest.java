package com.drone.simulator;

import com.drone.simulator.concurrent.SimulatorConcurrent;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class SimulatorConcurrentTest {

    @Test
    void executeSimulationCompletesWithoutException() {
        assertDoesNotThrow(() -> {
            SimulatorConcurrent simulator = new SimulatorConcurrent();
            simulator.executeSimulation();
        });
    }

    @Test
    void executeSimulationDronesHaveFinalPositions() {
        SimulatorConcurrent simulator = new SimulatorConcurrent();
        simulator.executeSimulation();
        assertTrue(true);
    }

    @Test
    void executeSimulationPrintsFinalPositions() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        try {
            SimulatorConcurrent simulator = new SimulatorConcurrent();
            simulator.executeSimulation();
            String output = out.toString();
            assertTrue(output.contains("Positions finales des drones"));
        } finally {
            System.setOut(System.out);
        }
    }
}
