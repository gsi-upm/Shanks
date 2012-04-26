/**
 * 
 */
package es.upm.dit.gsi.shanks.hackerhan.model.han.element.device;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * @author a.carrera
 *
 */
public class WifiAccessPoint extends Device {

	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_CONGESTED = "CONGESTED";
	public static final String STATUS_DISCONNECTED = "DISCONNECTED";

	public static final String PROPERTY_POWER = "Power";
	public static final String PROPERTY_EXTERNAL_CONNECTION = "Connection";
	public static final String PROPERTY_CONGESTION = "Congestion";
	
	public WifiAccessPoint(String id) throws UnsupportedNetworkElementStatusException {
		super(id, WifiAccessPoint.STATUS_OK, false);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
	 */
	@Override
	public void checkProperties() throws UnsupportedNetworkElementStatusException {
		// TODO Adapt the hole thing to HashMap String/boolean. 
        HashMap<String, Boolean> status = this.getStatus();
        if (status.equals(WifiAccessPoint.STATUS_OFF)) {
			this.shutdown();
		} else if (status.equals(WifiAccessPoint.STATUS_OK)) {
			this.addProperty(WifiAccessPoint.PROPERTY_POWER, "ON");
			this.addProperty(WifiAccessPoint.PROPERTY_EXTERNAL_CONNECTION, "OK");
			this.addProperty(PROPERTY_CONGESTION, Math.random()*10);
		} else if (status.equals(WifiAccessPoint.STATUS_CONGESTED)) {
			this.addProperty(WifiAccessPoint.PROPERTY_POWER, "ON");
			this.addProperty(WifiAccessPoint.PROPERTY_CONGESTION, 30+Math.random()*60);
		} else if (status.equals(WifiAccessPoint.STATUS_DISCONNECTED)){
			this.addProperty(WifiAccessPoint.PROPERTY_POWER, "ON");
			this.addProperty(WifiAccessPoint.PROPERTY_EXTERNAL_CONNECTION, "DISCONNECTED");
		}
	}

	private void shutdown() {
		this.addProperty(WifiAccessPoint.PROPERTY_POWER, "OFF");
		this.addProperty(WifiAccessPoint.PROPERTY_EXTERNAL_CONNECTION, "DISCONNECTED");
		this.addProperty(WifiAccessPoint.PROPERTY_CONGESTION, 0);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		String power = (String) this.getProperty(WifiAccessPoint.PROPERTY_POWER);
		String connection = (String) this.getProperty(WifiAccessPoint.PROPERTY_EXTERNAL_CONNECTION);
		Double congestion = (Double) this.getProperty(WifiAccessPoint.PROPERTY_CONGESTION);
		
		if (power.equals("OFF")) {
			this.updateStatusTo(WifiAccessPoint.STATUS_OFF, true);
			this.shutdown();
		} else if (connection.equals("DISCONNECTED")) {
				this.updateStatusTo(WifiAccessPoint.STATUS_DISCONNECTED, true);
		} else if (congestion>30){
			this.updateStatusTo(WifiAccessPoint.STATUS_CONGESTED, true);
		} else {
			this.updateStatusTo(WifiAccessPoint.STATUS_OK, true);
		}
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
	 */
	@Override
	public void fillIntialProperties() {
		this.addProperty(WifiAccessPoint.PROPERTY_POWER, "ON");
		this.addProperty(WifiAccessPoint.PROPERTY_EXTERNAL_CONNECTION, "OK");
		this.addProperty(WifiAccessPoint.PROPERTY_CONGESTION, Math.random()*10);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(WifiAccessPoint.STATUS_OK);
		this.addPossibleStatus(WifiAccessPoint.STATUS_OFF);
		this.addPossibleStatus(WifiAccessPoint.STATUS_DISCONNECTED);
		this.addPossibleStatus(WifiAccessPoint.STATUS_CONGESTED);
	}

}
