package es.upm.dit.gsi.shanks.model.event.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.event.OneShotEvent;

public abstract class Action extends OneShotEvent{

    private HashMap <Class<? extends NetworkElement>, HashMap<String, Object>> possibleAffected;
    private List<NetworkElement> affectedElements;
    private HashMap<String, Object> properties;
    private HashMap<String, Object> status;
    
    public Action(String id, Steppable launcher) {
        super(id, launcher);
        
        this.possibleAffected = new HashMap<Class<? extends NetworkElement>, HashMap<String,Object>>();
        this.affectedElements = new ArrayList<NetworkElement>();
        this.properties = new HashMap<String, Object>();
        this.status = new HashMap<String, Object>();
        
        this.addPossibleAffected();
    }
    
    public abstract void addPossibleAffected();
    
    public void changeStatus() throws UnsupportedNetworkElementFieldException{
        List<? extends NetworkElement> elements = this.affectedElements;

        for(NetworkElement el : elements){
            for(Class<?> c : possibleAffected.keySet()){
                if(c.equals(el.getClass())){  
                    for(String s : possibleAffected.get(c).keySet()){
                        if(el.getStatus().containsKey(s)){
                            el.updateStatusTo(s, (Boolean) possibleAffected.get(c).get(s));
                        }
                    }
                }   
            }
        }
    }
    
    public void changeProperties() throws UnsupportedNetworkElementFieldException{
        List<? extends NetworkElement> elements = this.affectedElements;
        for(NetworkElement el : elements){
            for(Class<?> c : possibleAffected.keySet()){
                if(c.equals(el.getClass())){                        
                    for(String s : possibleAffected.get(c).keySet()){
                        if(el.getProperties().containsKey(s)){
                            el.updatePropertyTo(s, possibleAffected.get(c).get(s));
                        }
                    }
                }
            }   
        }
    }
    
    public List<NetworkElement> getCurrentAffectedElements() {
        return affectedElements;
    }



    public void addAffectedElement(NetworkElement el){
        affectedElements.add(el);
    }
    
    public void removeAffectedElement(NetworkElement element) {
        this.affectedElements.remove(element);
    }

    /**
     * @return the possibleAffectedElements
     */
    public HashMap<Class<? extends NetworkElement>, HashMap<String, Object>> getPossibleAffectedElements() {
        return possibleAffected;
    }

    /**
     * @param c
     */
    public void addPossibleAffectedProperties(Class<? extends NetworkElement> c, String property, Object value) {
        this.properties.put(property, value);
        this.possibleAffected.put(c, properties);
    }
    
    public void addPossibleAffectedStatus(Class<? extends NetworkElement> c, String state, Boolean value){
        this.status.put(state, value);
        this.possibleAffected.put(c, status);
    }

 
    /**
     * @param elementClass
     */
    public void removePossibleAffectedElements(Class<? extends NetworkElement> elementClass) {
        if (this.possibleAffected.containsKey(elementClass)) {
            this.possibleAffected.remove(elementClass);
        }
    }
    

    public List<NetworkElement> getAffected() {
        return this.getCurrentAffectedElements();
    }
    

}
