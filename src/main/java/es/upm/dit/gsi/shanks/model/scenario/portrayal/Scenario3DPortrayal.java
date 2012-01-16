package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import sim.field.continuous.Continuous3D;
import sim.util.Double3D;
import es.upm.dit.gsi.shanks.model.element.device.Device;

/**
 * @author a.carrera
 * 
 */
public abstract class Scenario3DPortrayal {

    private int gridLength;
    private int gridWidth;
    private int gridHeight;

    private Continuous3D elements;

    public Scenario3DPortrayal(int length, int width, int height) {
        this.gridLength = length;
        this.gridWidth = width;
        this.gridHeight = height;
    }

    public void situateDevice(Device d, double x, double y, double z) {
        elements.setObjectLocation(d, new Double3D(x, y, z));
    }

}
