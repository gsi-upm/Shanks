package es.upm.dit.gsi.shanks.model.event;

import java.util.HashMap;
import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;


/**
 * @author dlara
 *
 */

public abstract class Event {
    
    private String name;
    private Steppable generator;
    
    private HashMap<String, Object> propertiesOfElementToChange;
    private HashMap<String, Boolean> statusOfElementToChange;
    private HashMap<String, Object> propertiesOfScenarioToChange;
    
    public boolean launch;
    
    /**
     * @param name
     * @param generator
     */
    public Event(String name, Steppable generator){
        this.name = name;
        this.generator = generator;
        
        this.propertiesOfElementToChange = new HashMap<String, Object>();
        this.statusOfElementToChange = new HashMap<String, Boolean>();
        this.propertiesOfScenarioToChange = new HashMap<String, Object>();
        
        launch = false;
    }
    
    /**
     * @param gen
     */
    public void setGenerator(Steppable gen){
        this.generator = gen;
    }
    
    /**
     * @return generator
     */
    public Steppable getGenerator(){
        return this.generator;
    }
    
    /**
     * @return name
     */
    public String getName(){
        return this.name;
    }
    
    
    /**
     * @return propertiesOfElementToChange
     */
    public HashMap<String, Object> getPropertiesAffectedOfElement(){
        return this.propertiesOfElementToChange;
    }
    
    
    
    /**
     * @return statusOfElementToChange
     */
    public HashMap<String, Boolean> getStatusAffectedOfElement(){
        return this.statusOfElementToChange;
    }
    
    /**
     * @return propertiesOfScenarioToChange
     */
    public HashMap<String, Object> getPropertiesAffectedOfScenario(){
        return this.propertiesOfScenarioToChange;
    }
    
    /**
     * @return launch (if the event is already launched)
     */
    public boolean isLaunched(){
        return launch;
    }
    
    /**
     * This method active the event
     */
    public void launchEvent(){
        this.launch = true;
    }
    
    
    
    /**
     * This method change the properties of a scenario when the event is launched
     * 
     * @param scenario
     */
    public void changePropertiesOfScenario(Scenario scenario){
        if(this.launch){
            for(String property : propertiesOfScenarioToChange.keySet()){
                if(scenario.getProperties().containsKey(property)){
                    scenario.getProperties().put(property, 
                            propertiesOfScenarioToChange.get(property));
                }
            }
        }
    }
    
    /**
     * This method change the status of a network element when the event is launched
     * 
     * @param element
     * @throws UnsupportedNetworkElementStatusException
     */
    public void changeStatus(NetworkElement element) throws UnsupportedNetworkElementStatusException{
        if(this.launch){
            for(String state : statusOfElementToChange.keySet()){
                if(element.getStatus().containsKey(state)){
                    element.getStatus().put(state, statusOfElementToChange.get(state));
                }
            }
        }
    }
    
    /**
     * This method change the properties of a network element when the event is launched
     * 
     * @param element
     * @throws UnsupportedNetworkElementStatusException
     */
    public void changePropertiesOfNetworkElement(NetworkElement element) throws UnsupportedNetworkElementStatusException{
        if(this.launch){
            for(String prop : propertiesOfElementToChange.keySet()){
                if(element.getProperties().containsKey(prop)){
                    element.getProperties().put(prop, propertiesOfElementToChange.get(prop));
                }
            } 
        }
    }
    
    /**
     * Add the properties of a scenario that the event will change
     * 
     * @param property
     * @param value
     */
    public void addAffectedPropertiesOfScenario(String property, Object value){
        propertiesOfScenarioToChange.put(property, value);
    }
    
    
    
    /**
     * Add the properties of a network element that the event will change
     * 
     * @param property
     * @param value
     */
    public void addAffectedPropertiesOfElement(String property, Object value){
        propertiesOfElementToChange.put(property, value);
    }
    
    
    
    /**
     * Add the states of a network element that the event will change
     * 
     * @param state
     * @param value
     */
    public void addAffectedStatesOfElement(String state, Boolean value){
        statusOfElementToChange.put(state, value);
    }
    
    
    
    public abstract void addChanges();
    
    
}