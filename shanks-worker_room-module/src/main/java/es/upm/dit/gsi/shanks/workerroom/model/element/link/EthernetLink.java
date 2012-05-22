package es.upm.dit.gsi.shanks.workerroom.model.element.link;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
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
			throws UnsupportedNetworkElementFieldException {
		super(id, initialState, capacity);
	}


	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementFieldException {
		HashMap<String, Boolean> status = this.getStatus();
		if(status.get(STATUS_OK)){
			this.updatePropertyTo(EthernetLink.PROPERTY_LENGTH, 1.0);
			this.updatePropertyTo(EthernetLink.PROPERTY_PACKETLOSSRATIO, 0.001);
		}
		if(status.get(STATUS_DAMAGED)){
			this.updatePropertyTo(EthernetLink.PROPERTY_PACKETLOSSRATIO, 0.3);
		}
		if(status.get(STATUS_CUT)){
			this.updatePropertyTo(EthernetLink.PROPERTY_PACKETLOSSRATIO, 1.0);
		}
	}

	@Override
	public void checkStatus() throws UnsupportedNetworkElementFieldException {
		Double packetLoss = (Double) this.getProperty(EthernetLink.PROPERTY_PACKETLOSSRATIO);
		if(packetLoss > 0.1 && packetLoss < 0.5){
			this.updateStatusTo(STATUS_DAMAGED, true);
			this.updateStatusTo(STATUS_OK, false);
			this.updateStatusTo(STATUS_CUT, false);
		}else if(packetLoss > 0.5){
			this.updateStatusTo(STATUS_DAMAGED, false);
			this.updateStatusTo(STATUS_OK, false);
			this.updateStatusTo(STATUS_CUT, true);
		}else if(packetLoss < 0.1){
			this.updateStatusTo(STATUS_DAMAGED, false);
			this.updateStatusTo(STATUS_OK, true);
			this.updateStatusTo(STATUS_CUT, false);
		}
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
