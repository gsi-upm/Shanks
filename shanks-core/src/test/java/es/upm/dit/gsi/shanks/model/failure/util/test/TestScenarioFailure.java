package es.upm.dit.gsi.shanks.model.failure.util.test;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.event.failiure.Failure;

public class TestScenarioFailure extends Failure {
    
    /**
     * Mark the complexity of the ScenarioFailure in order to test properly the Failure class. 
     * 
     * 0 - Default: The failure change the state of FailureTestScenario to NOK. 
     * 1 -   
     */
    private int configuration;

    public TestScenarioFailure(String id, Steppable generator, double prob) {
        super(id, generator, prob);
        this.configuration = 0;
    }
    
    public TestScenarioFailure(String id, Steppable generator, double prob, int configuration) {
        super(id, generator, prob);
        this.configuration = configuration;
    }

    @Override
    public void changeOtherFields()
            throws UnsupportedNetworkElementFieldException {
    }

    @Override
    public void addPossibleAffected() {
        switch (this.configuration) {
        default:
            this.addPossibleAffectedScenarioState(FailureTestScenario.class,
                    FailureTestScenario.NOK_STATE, true);
        }
    }

    @Override
    public void interactWithNE() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isResolved() {
        // TODO Auto-generated method stub
        return false;
    }

}
