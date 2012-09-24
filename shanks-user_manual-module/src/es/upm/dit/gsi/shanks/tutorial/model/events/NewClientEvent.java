package es.upm.dit.gsi.shanks.tutorial.model.events;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.event.networkelement.ProbabilisticNetworkElementEvent;
import es.upm.dit.gsi.shanks.tutorial.model.element.devices.Computer;


/**
 * 
 * @author Daniel Lara
 * 
 * This represent that a new client has entered in the LAN
 *
 */
public class NewClientEvent extends ProbabilisticNetworkElementEvent{

	public NewClientEvent(Steppable generator) {
		super(NewClientEvent.class.getName(), generator, 0.02);
		System.out.println("------->" + this.getID());
	
	}

	@Override
	public void changeOtherFields()
			throws UnsupportedNetworkElementFieldException {
		
		
	}

	@Override
	public void addPossibleAffected() {
        this.addPossibleAffectedElementProperty(Computer.class, Computer.PROPERTY_POWER, true);

		
	}

	@Override
	public void interactWithNE() {
		
		
	}

}
