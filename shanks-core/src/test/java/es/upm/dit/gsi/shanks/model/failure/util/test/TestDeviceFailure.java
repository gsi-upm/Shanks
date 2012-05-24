package es.upm.dit.gsi.shanks.model.failure.util.test;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.event.failiure.Failure;

public class TestDeviceFailure extends Failure {

    /**
     * Mark the complexity of the ScenarioFailure in order to test properly the
     * Failure class.
     * 
     * 0: Default: The failure change the property FOUNDATION_PROPERTY of
     * FailureTestDevice to NOK. 1:
     */
    private int configuration;
    public static final int DEFAULT_CONF = 0;

    public TestDeviceFailure(String id, Steppable generator, double prob) {
        super(id, generator, prob);
        this.configuration = 0;
    }

    public TestDeviceFailure(String id, Steppable generator, double prob,
            int configuration) {
        super(id, generator, prob);
        this.configuration = configuration;
    }

    @Override
    public void changeOtherFields()
            throws UnsupportedNetworkElementFieldException {
        // TODO Auto-generated method stub
    }

    @Override
    public void addPossibleAffected() {
        switch (this.configuration) {
        default:
            this.addPossibleAffectedElementProperty(FailureTestDevice.class,
                    FailureTestDevice.FUNDATIONS_PROPERTY,
                    FailureTestDefinitions.NOK);
        }

    }

    @Override
    public void interactWithNE() {
        // TODO Auto-generated method stub
    }

}
