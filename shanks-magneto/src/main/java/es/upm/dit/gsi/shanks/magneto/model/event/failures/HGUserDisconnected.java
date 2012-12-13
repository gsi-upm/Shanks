package es.upm.dit.gsi.shanks.magneto.model.event.failures;

import java.util.List;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.magneto.model.element.device.ISPGateway;
import es.upm.dit.gsi.shanks.magneto.model.element.device.UserGateway;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.event.failiure.Failure;

public class HGUserDisconnected extends Failure{

	public HGUserDisconnected(Steppable generator) {
		super(HGUserDisconnected.class.getName(), generator, 0.01);
		
	}

	@Override
	public void changeOtherFields()
			throws UnsupportedNetworkElementFieldException {
		
	}

	@Override
	public void addPossibleAffected() {
		this.addPossibleAffectedElementProperty(UserGateway.class, UserGateway.PROPERTY_CONFIGURATION, false);
		this.addPossibleAffectedElementProperty(UserGateway.class, UserGateway.PROPERTY_CONNECTION_TO_ISP, 1);
		this.addPossibleAffectedElementProperty(UserGateway.class, UserGateway.PROPERTY_CONNECTION_TO_PROVIDER, 1);
		this.addPossibleAffectedElementProperty(ISPGateway.class, ISPGateway.PROPERTY_CONNECTION_TO_USER, 1);
	}

	@Override
	public void interactWithNE() {
		
	}

	@Override
	public boolean isResolved() {
		List<NetworkElement> affected = this.affectedElements;
        for(NetworkElement ne : affected){
      	  if(ne.getStatus().get(UserGateway.STATUS_OK)){
      		  return true;
      	  }
        }
		return false;
	}

	
}
