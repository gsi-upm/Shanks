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
public class WifiConnection extends Link {
	
	public static final String STATUS_OK = "OK";
	public static final String STATUS_INTERFEARENCES = "Interfearences";
	public static final String STATUS_HIGH_BER = "High BER";

	public static final String PROPERTY_INTERFEARENCE = "Interfearecences"; 
	public static final String PROPERTY_PACKETLOSSRATIO = "Packet loss ratio"; //in %

	public WifiConnection(String id, String initialState, int capacity)
			throws UnsupportedNetworkElementStatusException {
		super(id, initialState, capacity);
	}
	

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
	 */
	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
        // TODO Adapt the hole thing to hasMapa String/Boolean.
    	HashMap<String, Boolean> status = this.getStatus();
		if (status.equals(WifiConnection.STATUS_OK)) {
			this.changeProperty(WifiConnection.PROPERTY_PACKETLOSSRATIO, 0.001);
			this.changeProperty(WifiConnection.PROPERTY_INTERFEARENCE, 0.01);
		} else if (status.equals(WifiConnection.STATUS_INTERFEARENCES)) {
			this.changeProperty(WifiConnection.PROPERTY_INTERFEARENCE, 0.90);
		} else if (status.equals(WifiConnection.STATUS_HIGH_BER)) {
			this.changeProperty(WifiConnection.PROPERTY_PACKETLOSSRATIO, 0.5);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		double pklratio = (Double) this.getProperty(WifiConnection.PROPERTY_PACKETLOSSRATIO);
		double interference = (Double) this.getProperty(WifiConnection.PROPERTY_INTERFEARENCE);
		if (interference > 0.2) {
			this.updateStatusTo(WifiConnection.STATUS_INTERFEARENCES, true);
		} else if (pklratio >= 0.2) {
			this.updateStatusTo(WifiConnection.STATUS_HIGH_BER, true);
		} else {
			this.updateStatusTo(WifiConnection.STATUS_OK, true);
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
		this.addProperty(WifiConnection.PROPERTY_INTERFEARENCE, 0.001);
		this.addProperty(WifiConnection.PROPERTY_PACKETLOSSRATIO, 0.01);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(WifiConnection.STATUS_OK);
		this.addPossibleStatus(WifiConnection.STATUS_HIGH_BER);
		this.addPossibleStatus(WifiConnection.STATUS_INTERFEARENCES);
	}

}
