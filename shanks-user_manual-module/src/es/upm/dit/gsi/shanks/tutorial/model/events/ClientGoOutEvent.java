package es.upm.dit.gsi.shanks.tutorial.model.events;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.event.networkelement.ProbabilisticNetworkElementEvent;
import es.upm.dit.gsi.shanks.tutorial.model.element.devices.Computer;

/**
 * 
 * @author Daniel Lara
 * 
 * This class represent an event that ocurr when a client go out from de LAN
 *
 */

public class ClientGoOutEvent extends ProbabilisticNetworkElementEvent{

	public ClientGoOutEvent(Steppable generator) {
		super(ClientGoOutEvent.class.getName(), generator, 0.01);
		
	}

	@Override
	public void changeOtherFields()
			throws UnsupportedNetworkElementFieldException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPossibleAffected() {
        this.addPossibleAffectedElementProperty(Computer.class, Computer.PROPERTY_POWER, false);

		
	}

	@Override
	public void interactWithNE() {
		// TODO Auto-generated method stub
		
	}

}
