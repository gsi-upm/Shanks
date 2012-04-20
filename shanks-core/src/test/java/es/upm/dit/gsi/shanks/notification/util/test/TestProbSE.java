package es.upm.dit.gsi.shanks.notification.util.test;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.event.scenario.ProbabilisticScenarioEvent;

public class TestProbSE extends ProbabilisticScenarioEvent{

    public TestProbSE(String name, Steppable generator, double prob) {
        super(name, generator, prob);
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
