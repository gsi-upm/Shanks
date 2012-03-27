package es.upm.dit.gsi.shanks.model.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import sim.engine.Steppable;

public abstract class ProbabilisticScenarioEvent extends ProbabilisticEvent{
    
    private List<Scenario> affectedScenarios;
    private HashMap<Class<? extends Scenario>, String> possibleAffected;



    public ProbabilisticScenarioEvent(String name, Steppable generator,
            double prob) {
        super(name, generator, prob);
        
        this.affectedScenarios = new ArrayList<Scenario>();
        this.possibleAffected = new HashMap<Class<? extends Scenario>, String>();

        this.addPossibleAffected();

    }

 
    public abstract void addPossibleAffected();



    public void changeProperties()
            throws UnsupportedNetworkElementStatusException {

        
    }

    @Override
    public void changeStatus() throws UnsupportedNetworkElementStatusException, UnsupportedScenarioStatusException {
        List<? extends Scenario> scenarios = this.affectedScenarios;
        for(Scenario scen : scenarios){
            for(Class<?> c : possibleAffected.keySet()){
                if(c.equals(scen.getClass())){  
                    scen.setCurrentStatus(possibleAffected.get(c));
                }
            }
        }
        
    }

    
    public abstract void interactWithNE();

    
    public void addAffectedScenario(Scenario s){
        affectedScenarios.add(s);
    }
    
    public void addAffectedElement(NetworkElement ne){
        
    }
    
    /**
     * @return the currentAffectedElements if the failure is active, null if not
     */
    public List<Scenario> getCurrentAffectedScenarios() {
            return affectedScenarios;
    }
    
    /**
     * Remove this element, but not modify the status. When the failure will be
     * deactive, this removed element will keep the actual status
     * 
     * @param element
     */
    public void removeAffectedElement(Scenario scen) {
        this.affectedScenarios.remove(scen);
    }

    /**
     * @return the possibleAffectedElements
     */
    public HashMap<Class<? extends Scenario>, String> getPossibleAffectedScenarios() {
        return possibleAffected;
    }


    
    public void addPossibleAffectedStatus(Class<? extends Scenario> c, String state){
        this.possibleAffected.put(c, state);
    }

 
    /**
     * @param elementClass
     */
    public void removePossibleAffectedElements(Class<? extends NetworkElement> elementClass) {
        if (this.possibleAffected.containsKey(elementClass)) {
            this.possibleAffected.remove(elementClass);
        }
    }


}
