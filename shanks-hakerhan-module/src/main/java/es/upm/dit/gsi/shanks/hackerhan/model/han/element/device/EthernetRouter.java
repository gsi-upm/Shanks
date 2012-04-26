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
public class EthernetRouter extends Device {

	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_CONGESTED = "CONGESTED";
	public static final String STATUS_DISCONNECTED = "DISCONNECTED";
	public static final String STATUS_NOISP_SERVICE = "NoISPService";

	public static final String PROPERTY_POWER = "Power";
	public static final String PROPERTY_EXTERNAL_CONNECTION = "Connection";
	public static final String PROPERTY_CONGESTION = "Congestion";


	/**
	 * @param id
	 * @throws UnsupportedNetworkElementStatusException
	 */
	public EthernetRouter(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id, EthernetRouter.STATUS_OK, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
	 */
	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
		// TODO Adapt the hole thing to HashMap String/boolean. 
        HashMap<String, Boolean> status = this.getStatus();
		if (status.get(EthernetRouter.STATUS_OFF)) {
			this.shutdown();
		} else if (status.equals(EthernetRouter.STATUS_OK)) {
			this.addProperty(EthernetRouter.PROPERTY_POWER, "ON");
			this.addProperty(EthernetRouter.PROPERTY_EXTERNAL_CONNECTION, "OK");
			this.addProperty(PROPERTY_CONGESTION, Math.random()*10);
		} else if (status.equals(EthernetRouter.STATUS_CONGESTED)) {
			this.addProperty(EthernetRouter.PROPERTY_POWER, "ON");
			this.addProperty(EthernetRouter.PROPERTY_CONGESTION, 30+Math.random()*60);
		} else if (status.equals(EthernetRouter.STATUS_NOISP_SERVICE)){
			this.addProperty(EthernetRouter.PROPERTY_POWER, "ON");
			this.addProperty(EthernetRouter.PROPERTY_EXTERNAL_CONNECTION, "NO-IP");
		} else if (status.equals(EthernetRouter.STATUS_DISCONNECTED)){
			this.addProperty(EthernetRouter.PROPERTY_POWER, "ON");
			this.addProperty(EthernetRouter.PROPERTY_EXTERNAL_CONNECTION, "DISCONNECTED");
		}
	}

	private void shutdown() {
		this.addProperty(EthernetRouter.PROPERTY_POWER, "OFF");
		this.addProperty(EthernetRouter.PROPERTY_EXTERNAL_CONNECTION, "DISCONNECTED");
		this.addProperty(EthernetRouter.PROPERTY_CONGESTION, 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		String power = (String) this.getProperty(EthernetRouter.PROPERTY_POWER);
		String connection = (String) this.getProperty(EthernetRouter.PROPERTY_EXTERNAL_CONNECTION);
		Double congestion = (Double) this.getProperty(EthernetRouter.PROPERTY_CONGESTION);
		
		if (power.equals("OFF")) {
			this.updateStatusTo(EthernetRouter.STATUS_OFF, true);
			this.shutdown();
		} else if (connection.equals("DISCONNECTED")) {
				this.updateStatusTo(EthernetRouter.STATUS_DISCONNECTED, true);
		} else if (connection.equals("NO-IP") ){
			this.updateStatusTo(EthernetRouter.STATUS_NOISP_SERVICE, true);
		} else if (congestion>30){
			this.updateStatusTo(EthernetRouter.STATUS_CONGESTED, true);
		} else {
			this.updateStatusTo(EthernetRouter.STATUS_OK, true);
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
		this.addProperty(EthernetRouter.PROPERTY_POWER, "ON");
		this.addProperty(EthernetRouter.PROPERTY_EXTERNAL_CONNECTION, "OK");
		this.addProperty(EthernetRouter.PROPERTY_CONGESTION, Math.random()*10);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(EthernetRouter.STATUS_OK);
		this.addPossibleStatus(EthernetRouter.STATUS_OFF);
		this.addPossibleStatus(EthernetRouter.STATUS_DISCONNECTED);
		this.addPossibleStatus(EthernetRouter.STATUS_CONGESTED);
		this.addPossibleStatus(EthernetRouter.STATUS_NOISP_SERVICE);
	}

}
