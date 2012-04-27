package es.upm.dit.gsi.shanks.model.workerroom.events;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.event.networkelement.PeriodicNetworkElementEvent;
import es.upm.dit.gsi.shanks.model.workerroom.element.device.Printer;

public class ConsumeInk extends PeriodicNetworkElementEvent{

	double ink;
	
	public ConsumeInk(Steppable generator) {
        super(ConsumeInk.class.getName(), generator, 500);
        ink = 1.0;
    }
	
	@Override
	public void addPossibleAffected() {
		this.addPossibleAffectedProperties(Printer.class, Printer.PROPERTY_INK, ink-0.10);
		
	}

	@Override
	public void interactWithNE() {

		
	}

}
