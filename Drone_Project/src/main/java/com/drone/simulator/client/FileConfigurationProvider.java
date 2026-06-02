package com.drone.simulator.client;

import com.drone.simulator.ConfigFileReader;
import com.drone.simulator.Drone;
import java.io.IOException;
import java.util.List;

public class FileConfigurationProvider implements ConfigurationProvider {

    private int width;
    private int height;
    private List<Drone> drones;

    public FileConfigurationProvider(String filename) throws IOException {
        ConfigFileReader reader = new ConfigFileReader();
        ConfigFileReader.SimulationConfig config = reader.readConfiguration(filename);
        this.width = config.getWidth();
        this.height = config.getHeight();
        this.drones = config.getDrones();
    }

    @Override
    public int getMapWidth() {
        return width;
    }

    @Override
    public int getMapHeight() {
        return height;
    }

    @Override
    public List<Drone> getDrones() {
        return drones;
    }
}
