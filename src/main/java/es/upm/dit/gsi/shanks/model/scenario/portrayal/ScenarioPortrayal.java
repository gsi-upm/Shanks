package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import java.util.HashMap;

import sim.field.network.Network;
import sim.portrayal.Portrayal;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

public abstract class ScenarioPortrayal {
    
    private Scenario scenario;
    private HashMap<String,HashMap<String, Portrayal>> portrayals; // <displayID - <portrayalID, portrayal>>
    
    public static final String DEVICES_PORTRAYAL = "Devices";
    public static final String LINKS_PORTRAYAL = "Links";
    
    /**
     * @param scenario
     */
    public ScenarioPortrayal(Scenario scenario) {
        this.scenario = scenario;
        this.portrayals = new HashMap<String,HashMap<String, Portrayal>>();
    }
    
    /**
     * @return Scenario of this portrayal
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
    public HashMap<String,HashMap<String, Portrayal>> getPortrayals() {
        return portrayals;
    }

    /**
     * @param portrayals the portrayals to set
     */
    public void setPortrayals(HashMap<String,HashMap<String, Portrayal>> portrayals) {
        this.portrayals = portrayals;
    }
    
    /**
     * @param displayID
     * @param portrayalID
     * @param portrayal
     * @throws DuplicatedPortrayalIDException 
     */
    public void addPortrayal(String displayID, String portrayalID, Portrayal portrayal) throws DuplicatedPortrayalIDException {
        if (!this.portrayals.containsKey(displayID)) {
            this.portrayals.put(displayID, new HashMap<String,Portrayal>());
        }
        HashMap<String,Portrayal> displayPortrayals = this.portrayals.get(displayID);
        if (!displayPortrayals.containsKey(portrayalID)) {
            displayPortrayals.put(portrayalID, portrayal);
        } else {
            throw new DuplicatedPortrayalIDException(displayID,portrayalID);
        }
    }
    
    /**
     * @param displayID
     * @param portrayalID
     */
    public void removePortrayal(String displayID, String portrayalID) {
        if (this.portrayals.containsKey(displayID)) {
            this.portrayals.get(displayID).remove(portrayalID);   
        }
    }

}
