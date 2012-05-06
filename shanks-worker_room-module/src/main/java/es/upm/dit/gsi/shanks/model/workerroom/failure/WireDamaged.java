package es.upm.dit.gsi.shanks.model.workerroom.failure;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.event.networkelement.ProbabilisticNetworkElementEvent;
import es.upm.dit.gsi.shanks.model.workerroom.element.link.EthernetLink;

public class WireDamaged extends ProbabilisticNetworkElementEvent{

	public WireDamaged(Steppable generator) {
		super(WireDamaged.class.getName(), generator, 0.05);
	}

	@Override
	public void addPossibleAffected() {
		this.addPossibleAffectedProperties(EthernetLink.class, EthernetLink.PROPERTY_PACKETLOSSRATIO, 0.35);
		
	}
	
	
	@Override
	public void interactWithNE() {
		
	}

}
