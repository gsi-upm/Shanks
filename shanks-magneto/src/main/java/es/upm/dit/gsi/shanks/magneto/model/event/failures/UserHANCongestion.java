package es.upm.dit.gsi.shanks.magneto.model.event.failures;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.magneto.model.element.device.UserGateway;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.event.failiure.Failure;
import es.upm.dit.gsi.shanks.model.event.networkelement.ProbabilisticNetworkElementEvent;

public class UserHANCongestion extends Failure{

	public UserHANCongestion(Steppable generator) {
		super(UserHANCongestion.class.getName(), generator, 1);	
		
	}

	@Override
	public void changeOtherFields()
			throws UnsupportedNetworkElementFieldException {
		
		
	}

	@Override
	public void addPossibleAffected() {
		this.addPossibleAffectedElementProperty(UserGateway.class, UserGateway.PROPERTY_USERHAN, 1);
		
	}

	@Override
	public void interactWithNE() {
		
		
	}

}
