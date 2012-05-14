/**
 * 
 */
package es.upm.dit.gsi.shanks.networkattacks.util.networkelements;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;

/**
 * @author a.carrera
 * 
 */
public class EthernetLink extends Link {

	public static final String STATUS_OK = "OK";
	public static final String STATUS_NOK = "NOK";
	public static final String STATUS_CUT = "CUT";
	public static final String STATUS_DAMAGED = "DAMAGED";

	public static final String PROPERTY_LENGTH = "Length"; // In meters
	public static final String PROPERTY_PACKETLOSSRATIO = "Packet loss ratio"; //in %

	/**
	 * Constructor ethernet cable
	 * 
	 * @param id
	 * @param length
	 * @throws UnsupportedNetworkElementStatusException
	 */
	public EthernetLink(String id, double length)
			throws UnsupportedNetworkElementStatusException {
		super(id, EthernetLink.STATUS_OK, 2);
		this.updatePropertyTo(EthernetLink.PROPERTY_LENGTH, length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
	 */
	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
    	HashMap<String, Boolean> status = this.getStatus();
		if (status.get(EthernetLink.STATUS_OK)) {
			this.updatePropertyTo(EthernetLink.PROPERTY_PACKETLOSSRATIO, 0.001);
		} else if (status.get(EthernetLink.STATUS_NOK)) {
			this.updatePropertyTo(EthernetLink.PROPERTY_PACKETLOSSRATIO, 100.0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		double ratio = (Double) this.getProperty(EthernetLink.PROPERTY_PACKETLOSSRATIO);
		if (ratio < 0.1) {
			this.updateStatusTo(EthernetLink.STATUS_OK, true);
			this.updateStatusTo(EthernetLink.STATUS_NOK, false);
		} else if (ratio >= 10) {
			this.updateStatusTo(EthernetLink.STATUS_OK, false);
			this.updateStatusTo(EthernetLink.STATUS_NOK, true);
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
		this.addProperty(EthernetLink.PROPERTY_LENGTH, 1.0);
		this.addProperty(EthernetLink.PROPERTY_PACKETLOSSRATIO, 0.001);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(EthernetLink.STATUS_OK);
		this.addPossibleStatus(EthernetLink.STATUS_NOK);
	}

}
