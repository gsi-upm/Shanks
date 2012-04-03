package es.upm.dit.gsi.shanks.notification.test;

import java.util.List;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.event.Event;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;

public class TestEvent extends Event {

    public TestEvent(String id, Steppable launcher) {
        super(id, launcher);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void addPossibleAffected() {
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
    public List<?> getAffected() {
        // TODO Auto-generated method stub
        return null;
    }

}
