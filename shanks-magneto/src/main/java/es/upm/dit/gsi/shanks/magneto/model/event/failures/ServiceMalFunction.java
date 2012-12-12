package es.upm.dit.gsi.shanks.magneto.model.event.failures;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.magneto.model.element.device.Server;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.event.failiure.Failure;

public class ServiceMalFunction extends Failure{

	public ServiceMalFunction(Steppable generator) {
		super(ServiceMalFunction.class.getName(), generator, 1);
	}

	@Override
	public void changeOtherFields()
			throws UnsupportedNetworkElementFieldException {
		
	}

	@Override
	public void addPossibleAffected() {
		this.addPossibleAffectedElementProperty(Server.class, Server.PROPERTY_SERVER_CPU_LOAD, 2);
	}

	@Override
	public void interactWithNE() {
		
	}

}
