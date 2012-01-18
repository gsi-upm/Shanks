package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import sim.field.grid.SparseGrid2D;
import sim.field.network.Network;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.portrayal.network.SpatialNetwork2D;
import sim.util.Int2D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

/**
 * @author a.carrera
 * 
 */
public abstract class Scenario2DPortrayal extends ScenarioPortrayal{

    private SparseGrid2D devices;
    private Network links;

    /**
     * The constructor needs the scenario and the size of the simulation
     * 
     * @param scenario
     * @param width
     * @param height
     */
    public Scenario2DPortrayal(Scenario scenario, int width, int height) {
        super(scenario);
        this.devices = new SparseGrid2D(width, height);
        this.links = new Network();
        SparseGridPortrayal2D devicesPortrayal = new SparseGridPortrayal2D();
        NetworkPortrayal2D linksPortrayal = new NetworkPortrayal2D();
        
        devicesPortrayal.setField(this.devices);
        linksPortrayal.setField(new SpatialNetwork2D(this.devices, this.links));
        
        this.addPortrayal("Devices", devicesPortrayal);
        this.addPortrayal("Links", linksPortrayal);
        
    }

    /**
     * To place a device in the simulation
     * 
     * @param d
     * @param x
     * @param y
     */
    public void situateDevice(Device d, int x, int y) {
        devices.setObjectLocation(d, new Int2D(x, y));
    }

    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#getElements()
     */
    public SparseGrid2D getDevices() {
        return this.devices;
    }
    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#getLinks()
     */
    public Network getLinks() {
        return this.links;
    }
}
