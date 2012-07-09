package es.upm.dit.gsi.shanks.tutorial.model.events.failures;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.event.failiure.Failure;
import es.upm.dit.gsi.shanks.tutorial.model.element.devices.Computer;

public class ComputerDisconnectedFailure extends Failure{

	public ComputerDisconnectedFailure(Steppable generator) {
		super(ComputerDisconnectedFailure.class.getName(), generator, 0.8);
		System.out.println("FALLO CREADO");
		
	}

	@Override
	public void changeOtherFields()
			throws UnsupportedNetworkElementFieldException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPossibleAffected() {
		this.addPossibleAffectedElementProperty(Computer.class, Computer.PROPERTY_ETHERNET_CONNECTION, false);
		
	}

	@Override
	public void interactWithNE() {
		// TODO Auto-generated method stub
		
	}

}
