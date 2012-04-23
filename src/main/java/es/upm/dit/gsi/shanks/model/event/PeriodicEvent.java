package es.upm.dit.gsi.shanks.model.event;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;

public abstract class PeriodicEvent extends Event{

    private int period;
    
    public PeriodicEvent(String id, Steppable launcher, int period) {
        super(id, launcher);
        this.period = period;
    }

    public int getPeriod(){
        return period;
    }
    public void setPeriod(int period) {
        this.period = period;
    }
    
    public abstract void addPossibleAffected();

    public abstract void addAffectedElement(NetworkElement ne);
    
    public abstract void addAffectedScenario(Scenario scen);

    public abstract void changeProperties() throws UnsupportedNetworkElementStatusException;
 
    public abstract void changeStatus() throws UnsupportedNetworkElementStatusException, UnsupportedScenarioStatusException;

    public abstract void interactWithNE();

}
