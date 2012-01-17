package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import sim.field.network.Network;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

public abstract class ScenarioPortrayal {
    
    private Scenario scenario;
    
    /**
     * @param scenario
     */
    public ScenarioPortrayal(Scenario scenario) {
        this.scenario = scenario;
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
    abstract public Object getDevices();
    
    /**
     * @return the SparseGridPortrayal2D or the ContinuousPortrayal3D object
     */
    abstract public Object getDevicesPortrayal();
    
    /**
     * @return the Network object with all links
     */
    abstract public Network getLinks();
    
    /**
     * @return the NetworkPortrayal2D or the NetworkPortrayal3D object
     */
    abstract public Object getLinksPortrayal();
    
    /**
     * To setup all portrayals of the simulation
     */
    abstract public void setupPortrayals();

}
