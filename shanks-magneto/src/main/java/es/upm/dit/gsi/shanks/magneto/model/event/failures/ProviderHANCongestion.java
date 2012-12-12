package es.upm.dit.gsi.shanks.magneto.model.event.failures;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.magneto.model.element.device.ServerGateway;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.event.failiure.Failure;

public class ProviderHANCongestion extends Failure{

	public ProviderHANCongestion(Steppable generator) {
		super(ProviderHANCongestion.class.getName(), generator, 1);
	}

	@Override
	public void changeOtherFields()
			throws UnsupportedNetworkElementFieldException {
		
	}

	@Override
	public void addPossibleAffected() {
		this.addPossibleAffectedElementProperty(ServerGateway.class, ServerGateway.PROPERTY_PROVIDERHAN, 1);
	}

	@Override
	public void interactWithNE() {
		
	}

}
