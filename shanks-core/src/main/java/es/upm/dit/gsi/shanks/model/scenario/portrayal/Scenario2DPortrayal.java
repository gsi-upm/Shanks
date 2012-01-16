package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.util.Double3D;
import es.upm.dit.gsi.shanks.model.element.device.Device;

/**
 * @author a.carrera
 *
 */
public abstract class Scenario2DPortrayal {

    private int gridWidth;
    private int gridHeight;
    
    private SparseGridPortrayal2D elements = new SparseGridPortrayal2D();
    private NetworkPortrayal2D links = new NetworkPortrayal2D();
    private SparseGridPortrayal2D failures = new SparseGridPortrayal2D();

    public void situateDevice(Device d, double x, double y) {
        elements.setObjectLocation(d, new Double3D(x, y, z));
        devicesnetwork.add(d);
    }
}
