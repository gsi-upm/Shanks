package es.upm.dit.gsi.shanks.model.event.test;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.event.networkelement.PeriodicNetworkElementEvent;

public class MyPeriodicNetElementEvent extends PeriodicNetworkElementEvent{

    public MyPeriodicNetElementEvent(String name, Steppable generator, int period) {
        super(MyPeriodicNetElementEvent.class.getName(), generator, 500);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void addPossibleAffected() {
        this.addPossibleAffectedProperties(MyDevice.class, MyDevice.TEMPERATURE_PROPERTY, 150);
       
    }

    @Override
    public void interactWithNE() {
        // TODO Auto-generated method stub
        
    }

}
