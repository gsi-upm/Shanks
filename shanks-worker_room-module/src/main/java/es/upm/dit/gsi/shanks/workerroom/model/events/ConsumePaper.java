package es.upm.dit.gsi.shanks.workerroom.model.events;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.event.networkelement.PeriodicNetworkElementEvent;
import es.upm.dit.gsi.shanks.workerroom.model.element.device.Printer;

public class ConsumePaper extends PeriodicNetworkElementEvent{

	int paper;
	
	public ConsumePaper(Steppable generator) {
        super(ConsumePaper.class.getName(), generator, 500);
        paper = 500;
    }
	@Override
	public void addPossibleAffected() {
		this.addPossibleAffectedProperties(Printer.class, Printer.PROPERTY_PAPER, paper-20);
		
	}

	@Override
	public void interactWithNE() {
		
	}

}
