package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import sim.field.continuous.Continuous3D;
import sim.field.network.Network;
import sim.util.Double3D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

/**
 * @author a.carrera
 * 
 */
public abstract class Scenario3DPortrayal extends ScenarioPortrayal{

    private Continuous3D elements;
    private Network links;

    /**
     * The constructor needs the size of the simulation
     * 
     * @param length
     * @param width
     * @param height
     */
    public Scenario3DPortrayal(Scenario scenario, long width, long height, long length) {
        super(scenario);
        this.elements = new Continuous3D(5, width, height, length);
    }

    /**
     * To place a device in the simulation
     * 
     * @param d
     * @param x
     * @param y
     * @param z
     */
    public void situateDevice(Device d, double x, double y, double z) {
        elements.setObjectLocation(d, new Double3D(x, y, z));
    }
    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#getElements()
     */
    public Continuous3D getElements() {
        return this.elements;
    }
    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#getLinks()
     */
    public Network getLinks() {
        return this.links;
    }

}
