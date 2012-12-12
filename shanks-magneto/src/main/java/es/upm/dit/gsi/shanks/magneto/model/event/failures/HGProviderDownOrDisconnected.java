package es.upm.dit.gsi.shanks.magneto.model.event.failures;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.magneto.model.element.device.ISPGateway;
import es.upm.dit.gsi.shanks.magneto.model.element.device.UserGateway;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.event.failiure.Failure;

public class HGProviderDownOrDisconnected extends Failure{

	public HGProviderDownOrDisconnected(Steppable generator) {
		super(HGProviderDownOrDisconnected.class.getName(), generator, 1);
	}

	@Override
	public void changeOtherFields()
			throws UnsupportedNetworkElementFieldException {
		
	}

	@Override
	public void addPossibleAffected() {
		this.addPossibleAffectedElementProperty(ISPGateway.class, ISPGateway.PROPERTY_CONNECTION_TO_PROVIDER, 1);
		this.addPossibleAffectedElementProperty(UserGateway.class, UserGateway.PROPERTY_CONNECTION_TO_PROVIDER, 1);
	}

	@Override
	public void interactWithNE() {
		
	}

}
