package es.upm.dit.gsi.shanks.workerroom.model.failure;

import java.util.List;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.event.networkelement.ProbabilisticNetworkElementEvent;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.workerroom.model.element.link.EthernetLink;

public class WireDamaged extends Failure{

	


	public WireDamaged() {
		super(WireDamaged.class.getName(), 0.05);
		
	}

	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedProperties(EthernetLink.class, EthernetLink.PROPERTY_PACKETLOSSRATIO, 0.35);
		
	}

	@Override
	public boolean isResolved() {
		boolean resolved = false;
		List<NetworkElement> list = this.getAffectedElements();
		for(NetworkElement ne : list){
			if((Double)ne.getProperties().get(EthernetLink.PROPERTY_PACKETLOSSRATIO) < 0.1){
				resolved = true;
			}
		}
		return resolved;
	}

}
