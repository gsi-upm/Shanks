package es.upm.dit.gsi.shanks.model.event.test;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.event.scenario.PeriodicScenarioEvent;
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;

public class MyPeriodicScenarioEvent extends PeriodicScenarioEvent{

    public MyPeriodicScenarioEvent(Steppable generator) {
        super(MyPeriodicScenarioEvent.class.getName(), generator, 500);
        
    }

    @Override
    public void addPossibleAffected() {
        this.addPossibleAffectedStatus(MyScenario.class, MyScenario.CLOUDY);
        
    }

    @Override
    public void interactWithNE() {
        // TODO Auto-generated method stub
        
    }

}
