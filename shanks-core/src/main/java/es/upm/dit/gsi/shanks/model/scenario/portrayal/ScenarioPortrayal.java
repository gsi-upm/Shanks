package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import java.util.HashMap;

import sim.field.network.Network;
import sim.portrayal.Portrayal;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

public abstract class ScenarioPortrayal {
    
    private Scenario scenario;
    private HashMap<String, Portrayal> portrayals;
    
    public static final String DEVICES_PORTRAYAL = "Devices";
    public static final String LINKS_PORTRAYAL = "Links";
    
    /**
     * @param scenario
     */
    public ScenarioPortrayal(Scenario scenario) {
        this.scenario = scenario;
        this.portrayals = new HashMap<String, Portrayal>();
    }
    
    /**
     * @return
     */
    public Scenario getScenario() {
        return this.scenario;
    }
    
    /**
     * @return the SparseGrid2D or the Continuous3D object
     */
    abstract public Object getPlacedDevices();
    
    /**
     * @return the Network object with all links
     */
    abstract public Network getLinks();
    
    /**
     * To setup all portrayals of the simulation
     */
    abstract public void setupPortrayals();

    /**
     * @return the portrayals
     */
    public HashMap<String, Portrayal> getPortrayals() {
        return portrayals;
    }

    /**
     * @param portrayals the portrayals to set
     */
    public void setPortrayals(HashMap<String, Portrayal> portrayals) {
        this.portrayals = portrayals;
    }
    
    /**
     * @param portrayalName
     * @param portrayal
     */
    public void addPortrayal(String portrayalName, Portrayal portrayal) {
        this.portrayals.put(portrayalName, portrayal);
    }
    
    /**
     * @param portrayalName
     */
    public void removePortrayal(String portrayalName) {
        this.portrayals.remove(portrayalName);
    }

}
