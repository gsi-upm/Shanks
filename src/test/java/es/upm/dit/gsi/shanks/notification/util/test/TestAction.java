package es.upm.dit.gsi.shanks.notification.util.test;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.event.agent.Action;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

public class TestAction extends Action{

    public TestAction(String id, Steppable launcher) {
        super(id, launcher);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void addPossibleAffected() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addAffectedScenario(Scenario scen) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void interactWithNE() {
        // TODO Auto-generated method stub
        
    }

}
