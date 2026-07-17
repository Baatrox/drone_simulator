package com.drone.simulator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;

class ConfigFileReaderTest {

    @Test
    void readConfigurationParsesValidFile() throws IOException {
        ConfigFileReader reader = new ConfigFileReader();
        ConfigFileReader.SimulationConfig config = reader.readConfiguration("src/test/resources/drones.txt");
        assertEquals(10, config.getWidth());
        assertEquals(10, config.getHeight());
        assertEquals(2, config.getDrones().size());
    }

    @Test
    void readConfigurationParsesFirstDroneCorrectly() throws IOException {
        ConfigFileReader reader = new ConfigFileReader();
        ConfigFileReader.SimulationConfig config = reader.readConfiguration("src/test/resources/drones.txt");
        Drone d = config.getDrones().get(0);
        assertEquals(0, d.getX());
        assertEquals(0, d.getY());
        assertEquals('E', d.getOrientation());
        assertEquals("MMMM", d.getCommands());
    }

    @Test
    void readConfigurationParsesSecondDroneCorrectly() throws IOException {
        ConfigFileReader reader = new ConfigFileReader();
        ConfigFileReader.SimulationConfig config = reader.readConfiguration("src/test/resources/drones.txt");
        Drone d = config.getDrones().get(1);
        assertEquals(5, d.getX());
        assertEquals(5, d.getY());
        assertEquals('N', d.getOrientation());
        assertEquals("MMLMM", d.getCommands());
    }

    @Test
    void readConfigurationThrowsOnMissingFile() {
        ConfigFileReader reader = new ConfigFileReader();
        assertThrows(IOException.class, () -> reader.readConfiguration("nonexistent.txt"));
    }

    @Test
    void readConfigurationThrowsOnInvalidFormat() {
        ConfigFileReader reader = new ConfigFileReader();
        assertThrows(IllegalArgumentException.class,
            () -> reader.readConfiguration("src/test/resources/drones_invalid.txt"));
    }
}
