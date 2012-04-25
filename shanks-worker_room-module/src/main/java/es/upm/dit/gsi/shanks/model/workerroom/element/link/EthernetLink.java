package es.upm.dit.gsi.shanks.model.workerroom.element.link;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;


/**
 * 
 * @author dlara
 *
 */

public class EthernetLink extends Link{
	
	public static final String STATUS_OK = "OK";
    public static final String STATUS_CUT = "CUT";
    public static final String STATUS_DAMAGED = "DAMAGED";

    public static final String PROPERTY_LENGTH = "Length"; // In meters
    public static final String PROPERTY_PACKETLOSSRATIO = "Packet loss ratio";
	

	public EthernetLink(String id, String initialState, int capacity)
			throws UnsupportedNetworkElementStatusException {
		super(id, initialState, capacity);
	}


	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
		HashMap<String, Boolean> status = this.getStatus();
		if(status.get(STATUS_OK)){
			this.changeProperty(EthernetLink.PROPERTY_LENGTH, 1.0);
			this.changeProperty(EthernetLink.PROPERTY_PACKETLOSSRATIO, 0.001);
		}
		if(status.get(STATUS_DAMAGED)){
			this.changeProperty(EthernetLink.PROPERTY_PACKETLOSSRATIO, 0.3);
		}
		if(status.get(STATUS_CUT)){
			this.changeProperty(EthernetLink.PROPERTY_PACKETLOSSRATIO, 1.0);
		}
	}

	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		Double packetLoss = (Double) this.getProperty(EthernetLink.PROPERTY_PACKETLOSSRATIO);
	}

	
	@Override
	public void fillIntialProperties() {
		this.addProperty(EthernetLink.PROPERTY_PACKETLOSSRATIO, 0.001);
		this.addProperty(EthernetLink.PROPERTY_LENGTH, 1.0);
	}
	
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(EthernetLink.STATUS_CUT);
		this.addPossibleStatus(EthernetLink.STATUS_OK);
		this.addPossibleStatus(EthernetLink.STATUS_DAMAGED);
	}

}
