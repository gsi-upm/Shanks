package es.upm.dit.gsi.shanks.model.workerroom.failure;

import java.util.List;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.workerroom.element.link.EthernetLink;

public class WireBroken extends Failure{

	public WireBroken() {
		super(WireBroken.class.getName(), 1);
		System.out.println("FALLO WIRE BROKEN CONSTRUIDO");
	}

	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedProperties(EthernetLink.class, EthernetLink.PROPERTY_PACKETLOSSRATIO, 1.0);
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
