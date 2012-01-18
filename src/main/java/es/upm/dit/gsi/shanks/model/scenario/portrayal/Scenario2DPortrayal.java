package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import java.util.List;

import sim.field.grid.SparseGrid2D;
import sim.field.network.Edge;
import sim.field.network.Network;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.portrayal.network.SpatialNetwork2D;
import sim.util.Int2D;
import sim.util.MutableDouble;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

/**
 * @author a.carrera
 * 
 */
public abstract class Scenario2DPortrayal extends ScenarioPortrayal {

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

        this.placeElements();

    }

    /**
     * Draw all elements of the simulation
     */
    abstract public void placeElements();

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

    /**
     * To draw a link
     * 
     * @param link
     */
    public void drawLink(Link link) {
        List<Device> linkedDevices = link.getLinkedDevices();
        for (int i = 0; i<linkedDevices.size(); i++) {
            Device from = linkedDevices.get(i);
            for (int j = i+1 ; j<linkedDevices.size(); j++) {
                Device to = linkedDevices.get(j);
//                Edge e = new Edge(from, to, new MutableDouble(link.getLinkedDevices().size())); //TOTEST check if this size is resizable (redimensionable) during the simulation
                Edge e = new Edge(from, to, new MutableDouble(link.getCapacity()));
                links.addEdge(e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#getElements
     * ()
     */
    public SparseGrid2D getPlacedDevices() {
        return this.devices;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#getLinks
     * ()
     */
    public Network getLinks() {
        return this.links;
    }
}
