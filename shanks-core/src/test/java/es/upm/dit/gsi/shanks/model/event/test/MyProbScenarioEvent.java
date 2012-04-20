package es.upm.dit.gsi.shanks.model.event.test;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.event.scenario.ProbabilisticScenarioEvent;
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;

public class MyProbScenarioEvent extends ProbabilisticScenarioEvent{

    public MyProbScenarioEvent(String name, Steppable generator, double prob) {
        super(MyProbScenarioEvent.class.getName(), generator, 0.50);
       
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
