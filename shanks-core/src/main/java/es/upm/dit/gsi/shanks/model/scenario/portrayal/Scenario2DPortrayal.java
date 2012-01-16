package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import sim.field.grid.SparseGrid2D;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.util.Int2D;
import es.upm.dit.gsi.shanks.model.element.device.Device;

/**
 * @author a.carrera
 * 
 */
public abstract class Scenario2DPortrayal {

    private int gridWidth;
    private int gridHeight;

    private SparseGrid2D elements;
    private NetworkPortrayal2D links;
    private SparseGridPortrayal2D failures;

    public Scenario2DPortrayal(int width, int height) {
        this.gridHeight = height;
        this.gridWidth = width;
        elements = new SparseGrid2D(width, height);
        links = new NetworkPortrayal2D();
        failures = new SparseGridPortrayal2D();
    }

    public void situateDevice(Device d, int x, int y) {
        elements.setObjectLocation(d, new Int2D(x, y));
    }
}
