package es.upm.dit.gsi.shanks.model.event;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import sim.engine.Steppable;

public class OneShotEvent extends Event{

    public OneShotEvent(String id, Steppable launcher) {
        super(id, launcher);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void addPossibleAffected() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void changeProperties()
            throws UnsupportedNetworkElementStatusException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void changeStatus() throws UnsupportedNetworkElementStatusException,
            UnsupportedScenarioStatusException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void interactWithNE() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addAffectedElement(NetworkElement ne) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addAffectedScenario(Scenario scen) {
        // TODO Auto-generated method stub
        
    }

}
