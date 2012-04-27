package es.upm.dit.gsi.shanks.model.workerroom.events;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.event.networkelement.ProbabilisticNetworkElementEvent;
import es.upm.dit.gsi.shanks.model.workerroom.element.link.EthernetLink;

public class WireBroken extends ProbabilisticNetworkElementEvent{

	public WireBroken(String name, Steppable generator, Double prob) {
		super(WireBroken.class.getName(), generator, 0.01);
	}

	
	@Override
	public void addPossibleAffected() {
		this.addPossibleAffectedProperties(EthernetLink.class, EthernetLink.PROPERTY_PACKETLOSSRATIO, 1.0);
		
	}
	
	@Override
	public void interactWithNE() {
		// TODO Auto-generated method stub
		
	}

}
