/**
 * es.upm.dit.gsi
 * 01/05/2012
 */
package es.upm.dit.gsi.shanks.hackerhan.model.element.link;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;

/**
 * @author darofar
 *
 */
public class WifiConnection extends Link {
	
	public static final String STATUS_OK = "OK";
	public static final String STATUS_NOK = "NOK";
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
    	HashMap<String, Boolean> status = this.getStatus();
		if (status.get(WifiConnection.STATUS_OK)) {
			this.updatePropertyTo(WifiConnection.PROPERTY_PACKETLOSSRATIO, 0.001);
			this.updatePropertyTo(WifiConnection.PROPERTY_INTERFEARENCE, 0.01);
		} else if (status.get(WifiConnection.STATUS_NOK)) {
			this.updatePropertyTo(WifiConnection.PROPERTY_INTERFEARENCE, 90);
			this.updatePropertyTo(WifiConnection.PROPERTY_PACKETLOSSRATIO, 90);
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
		if ((interference > 1)||(pklratio > 1)) {
			this.updateStatusTo(WifiConnection.STATUS_OK, true);
			this.updateStatusTo(WifiConnection.STATUS_NOK, false);
		} else {
			this.updateStatusTo(WifiConnection.STATUS_OK, false);
			this.updateStatusTo(WifiConnection.STATUS_NOK, true);
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
		this.addPossibleStatus(WifiConnection.STATUS_NOK);
	}
}