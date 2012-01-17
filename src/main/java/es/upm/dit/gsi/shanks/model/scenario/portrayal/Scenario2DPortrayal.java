package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import sim.field.grid.SparseGrid2D;
import sim.field.network.Network;
import sim.util.Int2D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

/**
 * @author a.carrera
 * 
 */
public abstract class Scenario2DPortrayal extends ScenarioPortrayal{

    private SparseGrid2D elements;
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
        elements = new SparseGrid2D(width, height);
        links = new Network();
    }

    /**
     * To place a device in the simulation
     * 
     * @param d
     * @param x
     * @param y
     */
    public void situateDevice(Device d, int x, int y) {
        elements.setObjectLocation(d, new Int2D(x, y));
    }

    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#getElements()
     */
    public SparseGrid2D getElements() {
        return this.elements;
    }
    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#getLinks()
     */
    public Network getLinks() {
        return this.links;
    }
}
