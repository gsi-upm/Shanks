package es.upm.dit.gsi.shanks.model.event;

import java.util.List;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.notification.NotificationManager;

/**
 * Make the possible events
 * 
 * @author Daniel Lara
 * 
 */

public abstract class Event {

    private String id;
    private Steppable launcher;
    
    /**
     * Constructor of the class
     * 
     * @param id
     * @param launcher
     */
    public Event(String id, Steppable launcher) {
        this.id = id;
        this.launcher = launcher;     
    }
    
    public Event() {
    }

    /**
     * Add classes using addPossibleAffectedElements method
     */
    public abstract void addPossibleAffected();

    /**
     * @return the id
     */
    public String getID(){
        return id;
    }

    /**
     * @return the occurrenceProbability
     */
    public Steppable getLauncher(){
        return launcher;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLauncher(Steppable launcher) {
        this.launcher = launcher;
    }



    /**
     * Used to generate the event.
     * 
     * @throws UnsupportedNetworkElementStatusException 
     * @throws UnsupportedScenarioStatusException 
     * 
     */
    public void launchEvent() throws UnsupportedNetworkElementStatusException, UnsupportedScenarioStatusException {
        this.changeProperties();
        this.changeStatus();
        this.interactWithNE();
        this.generateNotification();
    }

    private void generateNotification() {
        NotificationManager.addNotification(this, this.getClass().getName());
    }
    
    public abstract void addAffectedElement(NetworkElement ne);
    
    public abstract void addAffectedScenario(Scenario scen);
    
    public abstract void changeProperties() throws UnsupportedNetworkElementStatusException;
    
    public abstract void changeStatus() throws UnsupportedNetworkElementStatusException, UnsupportedScenarioStatusException;
    
    public abstract void interactWithNE();

    public abstract List<?> getAffected();


}

