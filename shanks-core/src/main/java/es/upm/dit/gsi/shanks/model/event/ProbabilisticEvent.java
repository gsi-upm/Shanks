package es.upm.dit.gsi.shanks.model.event;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import sim.engine.Steppable;

public abstract class ProbabilisticEvent extends Event{

    private double prob;
    
    public ProbabilisticEvent(String name, Steppable generator, double prob) {
        super(name, generator);
        this.prob = prob;
    }

    public double getProb(){
        return prob;
    }
    
    public abstract void addPossibleAffected();
    
    public abstract void addAffectedElement(NetworkElement ne);
    
    public abstract void addAffectedScenario(Scenario scen);

    public abstract void changeProperties() throws UnsupportedNetworkElementStatusException;
       
    public abstract void changeStatus() throws UnsupportedNetworkElementStatusException, UnsupportedScenarioStatusException;
          
    public abstract void interactWithNE();
       

}
