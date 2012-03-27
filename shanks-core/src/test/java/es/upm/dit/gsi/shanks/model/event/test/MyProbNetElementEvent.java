package es.upm.dit.gsi.shanks.model.event.test;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.event.ProbabilisticNetworkElementEvent;
import es.upm.dit.gsi.shanks.model.failure.test.MyFailure;

public class MyProbNetElementEvent extends ProbabilisticNetworkElementEvent{

    public MyProbNetElementEvent(Steppable generator) {
        super(MyFailure.class.getName(), generator, 0.50);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void addPossibleAffected() {
        this.addPossibleAffectedProperties(MyDevice.class, MyDevice.TEMPERATURE_PROPERTY, 80);
        
    }

    @Override
    public void interactWithNE() {
        // TODO Auto-generated method stub
        
    }

}
