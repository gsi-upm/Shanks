package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import sim.display.Display2D;
import sim.field.continuous.Continuous2D;
import sim.field.network.Edge;
import sim.field.network.Network;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.portrayal.network.SpatialNetwork2D;
import sim.util.Double2D;
import es.upm.dit.gsi.shanks.exception.DuplictaedDisplayIDException;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplictedFrameIDException;

/**
 * @author a.carrera
 * 
 */
public abstract class Scenario2DPortrayal extends ScenarioPortrayal {

    private Continuous2D devices;
    private Network links;
    private SpatialNetwork2D deviceLinkNetwork;

    private HashMap<String, Display2D> displayList;
    private HashMap<String, JFrame> frameList;

    public static final String MAIN_DISPLAY_ID = "MainDisplay";

    /**
     * The constructor needs the scenario and the size of the simulation
     * 
     * @param scenario
     * @param width
     * @param height
     * @throws DuplicatedPortrayalIDException
     */
    public Scenario2DPortrayal(Scenario scenario, int width, int height)
            throws DuplicatedPortrayalIDException {
        super(scenario);
        this.devices = new Continuous2D(5, width, height);
        this.links = new Network();
        this.deviceLinkNetwork = new SpatialNetwork2D(this.devices, this.links);
        this.displayList = new HashMap<String, Display2D>();
        this.frameList = new HashMap<String, JFrame>();
        ContinuousPortrayal2D devicesPortrayal = new ContinuousPortrayal2D();
        NetworkPortrayal2D linksPortrayal = new NetworkPortrayal2D();

        devicesPortrayal.setField(this.devices);
        linksPortrayal.setField(this.deviceLinkNetwork);

        this.addPortrayal(Scenario2DPortrayal.MAIN_DISPLAY_ID,
                ScenarioPortrayal.DEVICES_PORTRAYAL, devicesPortrayal);
        this.addPortrayal(Scenario2DPortrayal.MAIN_DISPLAY_ID,
                ScenarioPortrayal.LINKS_PORTRAYAL, linksPortrayal);

        this.addPortrayals();
        this.placeElements();

    }

    /**
     * Add extra portrayals to one display of the simulation
     */
    abstract public void addPortrayals();

    /**
     * @return Map with key: Display name and value: Display2D
     */
    public HashMap<String, Display2D> getDisplays() {
        return displayList;
    }

    /**
     * @param displayList
     */
    public void setDisplayList(HashMap<String, Display2D> displayList) {
        this.displayList = displayList;
    }

    /**
     * @param displayID
     * @param display
     * @throws DuplictaedDisplayIDException
     */
    public void addDisplay(String displayID, Display2D display)
            throws DuplictaedDisplayIDException {
        if (this.displayList.containsKey(displayID)) {
            throw new DuplictaedDisplayIDException(displayID);
        }
        this.displayList.put(displayID, display);
    }

    /**
     * @param displayID
     * @return Display2D object
     */
    public Display2D getDisplay(String displayID) {
        return this.displayList.get(displayID);
    }

    /**
     * @param displayID
     */
    public void removeDisplay(String displayID) {
        this.displayList.remove(displayID);
    }

    /**
     * @return Map with key: JFrame name and value: JFrame
     */
    public HashMap<String, JFrame> getFrameList() {
        return frameList;
    }

    /**
     * @param frameList
     */
    public void setFrameList(HashMap<String, JFrame> frameList) {
        this.frameList = frameList;
    }

    /**
     * @param frameID
     * @param frame
     * @throws DuplictedFrameIDException 
     */
    public void addFrame(String frameID, JFrame frame) throws DuplictedFrameIDException {
        if (this.frameList.containsKey(frameID)) {
            throw new DuplictedFrameIDException(frameID);
        } else {
            this.frameList.put(frameID, frame);            
        }
    }

    /**
     * @param frameID
     */
    public void removeFrame(String frameID) {
        this.frameList.remove(frameID);
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
    public void situateDevice(Device d, double x, double y) {
        devices.setObjectLocation(d, new Double2D(x, y));
    }

    /**
     * To draw a link
     * 
     * @param link
     */
    public void drawLink(Link link) {
        List<Device> linkedDevices = link.getLinkedDevices();
        for (int i = 0; i < linkedDevices.size(); i++) {
            Device from = linkedDevices.get(i);
            for (int j = i + 1; j < linkedDevices.size(); j++) {
                Device to = linkedDevices.get(j);
                Edge e = new Edge(from, to, link);
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
    public Continuous2D getPlacedDevices() {
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
