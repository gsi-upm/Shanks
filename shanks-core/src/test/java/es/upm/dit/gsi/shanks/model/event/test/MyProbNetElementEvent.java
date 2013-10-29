package es.upm.dit.gsi.shanks.model.event.test;

import java.util.logging.Logger;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.event.networkelement.ProbabilisticNetworkElementEvent;

public class MyProbNetElementEvent extends ProbabilisticNetworkElementEvent{

    static Logger logger = Logger.getLogger(MyProbNetElementEvent.class.getName());
    
    public MyProbNetElementEvent(Steppable generator) {
        super(MyProbNetElementEvent.class.getName(), generator, 0.50, logger);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void addPossibleAffected() {
        this.addPossibleAffectedElementProperty(MyDevice.class, MyDevice.TEMPERATURE_PROPERTY, 80);
        
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
