/**
 * 
 */
package es.upm.dit.gsi.shanks.hackerhan.model.han.element.link;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;

/**
 * @author a.carrera
 * 
 */
public class EthernetCable extends Link {

	public static final String STATUS_OK = "OK";
	public static final String STATUS_CUT = "CUT";
	public static final String STATUS_DAMAGED = "DAMAGED";

	public static final String PROPERTY_LENGTH = "Length"; // In meters
	public static final String PROPERTY_PACKETLOSSRATIO = "Packet loss ratio";

	/**
	 * Constructor ethernet cable
	 * 
	 * @param id
	 * @param length
	 * @throws UnsupportedNetworkElementStatusException
	 */
	public EthernetCable(String id, double length)
			throws UnsupportedNetworkElementStatusException {
		super(id, EthernetCable.STATUS_OK, 2);
		this.addProperty(EthernetCable.PROPERTY_LENGTH, length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
	 */
	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
        // TODO Adapt the hole thing to hasMapa String/Boolean.
    	HashMap<String, Boolean> status = this.getStatus();
		if (status.equals(EthernetCable.STATUS_OK)) {
			this.changeProperty(EthernetCable.PROPERTY_PACKETLOSSRATIO, 0.001);
		} else if (status.equals(EthernetCable.STATUS_CUT)) {
			this.changeProperty(EthernetCable.PROPERTY_PACKETLOSSRATIO, 1.0);
		} else if (status.equals(EthernetCable.STATUS_DAMAGED)) {
			this.changeProperty(EthernetCable.PROPERTY_PACKETLOSSRATIO, 0.3);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		double ratio = (Double) this.getProperty(EthernetCable.PROPERTY_PACKETLOSSRATIO);
		if (ratio < 0.01) {
			this.updateStatusTo(EthernetCable.STATUS_OK, true);
		} else if (ratio >= 1) {
			this.updateStatusTo(EthernetCable.STATUS_CUT, true);
		} else {
			this.updateStatusTo(EthernetCable.STATUS_DAMAGED, true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
	 */
	@Override
	public void fillIntialProperties() {
		this.addProperty(EthernetCable.PROPERTY_LENGTH, 1.0);
		this.addProperty(EthernetCable.PROPERTY_PACKETLOSSRATIO, 0.001);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(EthernetCable.STATUS_OK);
		this.addPossibleStatus(EthernetCable.STATUS_CUT);
		this.addPossibleStatus(EthernetCable.STATUS_DAMAGED);
	}

}
