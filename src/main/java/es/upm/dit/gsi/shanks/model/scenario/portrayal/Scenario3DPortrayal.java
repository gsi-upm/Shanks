package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import java.util.List;

import sim.field.continuous.Continuous3D;
import sim.field.network.Edge;
import sim.field.network.Network;
import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import sim.portrayal3d.network.NetworkPortrayal3D;
import sim.portrayal3d.network.SpatialNetwork3D;
import sim.util.Double3D;
import es.upm.dit.gsi.shanks.ShanksSimulation3DGUI;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalID;

/**
 * @author a.carrera
 * 
 */
public abstract class Scenario3DPortrayal extends ScenarioPortrayal{

    private Continuous3D devices;
    private Network links;
    private SpatialNetwork3D deviceLinkNetwork;

    /**
     * The constructor needs the size of the simulation
     * 
     * @param length
     * @param width
     * @param height
     * @throws DuplicatedPortrayalID 
     */
    public Scenario3DPortrayal(Scenario scenario, long width, long height, long length) throws DuplicatedPortrayalID {
        super(scenario);
        this.devices = new Continuous3D(5, width, height, length);
        this.links = new Network();
        this.deviceLinkNetwork = new SpatialNetwork3D(this.devices, this.links);
        ContinuousPortrayal3D devicesPortrayal = new ContinuousPortrayal3D();
        NetworkPortrayal3D linksPortrayal = new NetworkPortrayal3D();

        devicesPortrayal.setField(this.devices);
        linksPortrayal.setField(deviceLinkNetwork);

        this.addPortrayal(ShanksSimulation3DGUI.MAIN_DISPLAY, ScenarioPortrayal.DEVICES_PORTRAYAL, devicesPortrayal);
        this.addPortrayal(ShanksSimulation3DGUI.MAIN_DISPLAY, ScenarioPortrayal.LINKS_PORTRAYAL, linksPortrayal);

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
     * @param z
     */
    public void situateDevice(Device d, double x, double y, double z) {
        devices.setObjectLocation(d, new Double3D(x, y, z));
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
                Edge e = new Edge(from, to, link);
                links.addEdge(e);
            }
        }
    }
    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#getElements()
     */
    public Continuous3D getPlacedDevices() {
        return this.devices;
    }
    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#getLinks()
     */
    public Network getLinks() {
        return this.links;
    }

}
