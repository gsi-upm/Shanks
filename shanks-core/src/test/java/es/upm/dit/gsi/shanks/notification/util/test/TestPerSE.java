package es.upm.dit.gsi.shanks.notification.util.test;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.event.scenario.PeriodicScenarioEvent;

public class TestPerSE extends PeriodicScenarioEvent{

    public TestPerSE(String name, Steppable generator, int period) {
        super(name, generator, period);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void addPossibleAffected() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void interactWithNE() {
        // TODO Auto-generated method stub
        
    }

}
