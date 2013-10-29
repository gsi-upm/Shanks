package es.upm.dit.gsi.shanks.notification.util.test;

import java.util.logging.Logger;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.event.networkelement.ProbabilisticNetworkElementEvent;

public class TestProbabilisticNetworkElementEvent extends ProbabilisticNetworkElementEvent{

    public TestProbabilisticNetworkElementEvent(Steppable generator, Logger logger) {
        super(TestDefinitions.EVENT_ID+TestProbabilisticNetworkElementEvent.class, 
                generator, TestDefinitions.EVENT_PROBABILITY, logger);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void addPossibleAffected() {
        this.addPossibleAffectedElementState(TestDevice.class, null, false);
        
    }

    @Override
    public void interactWithNE() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void changeOtherFields()
            throws UnsupportedNetworkElementFieldException {
        // TODO Auto-generated method stub
        
    }

}
