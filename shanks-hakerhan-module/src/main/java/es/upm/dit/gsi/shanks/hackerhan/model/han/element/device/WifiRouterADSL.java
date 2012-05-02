/**
 * es.upm.dit.gsi
 * 30/04/2012
 */
package es.upm.dit.gsi.shanks.hackerhan.model.han.element.device;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.hackerhan.model.Values;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * The access point for HANs to the ISP network. this device can be blocked to prevent 
 * Hackers making attacks.  
 * 
 * @author darofar
 *
 */
public class WifiRouterADSL extends Device {

	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_CONGESTED = "Congested";
	public static final String STATUS_DISCONNECTED = "Disconnected";
	public static final String STATUS_NOISP_SERVICE = "NoISPService";
	public static final String STATUS_STEALING_CONNECTION = "StealingConnection";

	public static final String PROPERTY_POWER = "Power";
	public static final String PROPERTY_CONNECTION = "ConnectionStatus";
	public static final String PROPERTY_CONNECTION_TYPE = "ConnectionType";
	public static final String PROPERTY_CONGESTION = "Congestion"; // in %
	
	public static final String CONNECTION_TYPE_ISP = "ISP";
	public static final String CONNECTION_TYPE_STEAL = "Steal";
	
	public WifiRouterADSL(String id, String initialState, boolean isGateway)
			throws UnsupportedNetworkElementStatusException {
		super(id, initialState, isGateway);
	}
	
	public WifiRouterADSL(String id, boolean isGateway)
			throws UnsupportedNetworkElementStatusException {
		super(id, WifiRouterADSL.STATUS_OK, isGateway);
	}
	
	public WifiRouterADSL(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id, WifiRouterADSL.STATUS_OK, true);
	}	

	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
	 */
	@Override
	public void fillIntialProperties() {
		this.addProperty(WifiRouterADSL.PROPERTY_POWER, Values.ON);
		this.addProperty(WifiRouterADSL.PROPERTY_CONNECTION, Values.CONNECTED);
		this.addProperty(WifiRouterADSL.PROPERTY_CONNECTION_TYPE, WifiRouterADSL.CONNECTION_TYPE_ISP);
		this.addProperty(WifiRouterADSL.PROPERTY_CONGESTION, 0);
	}
	
	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
	 */
	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
        HashMap<String, Boolean> status = this.getStatus();
		if (status.get(WifiRouterADSL.STATUS_OFF)) {
			this.shutdown();
		} else {
			this.updatePropertyTo(WifiRouterADSL.PROPERTY_POWER, Values.ON);
			if(status.get(WifiRouterADSL.STATUS_STEALING_CONNECTION)) {
				this.updatePropertyTo(WifiRouterADSL.PROPERTY_CONNECTION_TYPE, WifiRouterADSL.CONNECTION_TYPE_STEAL);
			} else {
				this.updatePropertyTo(WifiRouterADSL.PROPERTY_CONNECTION_TYPE, WifiRouterADSL.CONNECTION_TYPE_ISP);
			}
			if (status.get(WifiRouterADSL.STATUS_OK)) {
				this.updatePropertyTo(WifiRouterADSL.PROPERTY_CONNECTION, Values.CONNECTED);
				this.updatePropertyTo(PROPERTY_CONGESTION, Math.random()*10);
			} else if (status.get(WifiRouterADSL.STATUS_CONGESTED)) {
				this.updatePropertyTo(WifiRouterADSL.PROPERTY_CONNECTION, Values.CONNECTED);
				this.updatePropertyTo(WifiRouterADSL.PROPERTY_CONGESTION, 30+Math.random()*60);
			} else if (status.equals(WifiRouterADSL.STATUS_DISCONNECTED)){
				this.updatePropertyTo(WifiRouterADSL.PROPERTY_CONNECTION, Values.DISCONNECTED);
				this.updatePropertyTo(PROPERTY_CONNECTION_TYPE, Values.NA);
			} else if (status.equals(WifiRouterADSL.STATUS_NOISP_SERVICE)){
				this.updatePropertyTo(WifiRouterADSL.PROPERTY_CONNECTION, Values.NO_IP);
			} 
		}
	}

	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		String power = (String) this.getProperty(WifiRouterADSL.PROPERTY_POWER);
		String cStatus = (String) this.getProperty(WifiRouterADSL.PROPERTY_CONNECTION);
		String cType = (String) this.getProperty(WifiRouterADSL.PROPERTY_CONNECTION_TYPE);
		double congestion = (Double) this.getProperty(WifiRouterADSL.PROPERTY_CONGESTION);
		
		if(cType.equals(WifiRouterADSL.CONNECTION_TYPE_ISP)){
			this.updateStatusTo(WifiRouterADSL.STATUS_STEALING_CONNECTION, false);
		} else {
			this.updateStatusTo(WifiRouterADSL.STATUS_STEALING_CONNECTION, true);
		}
		
		if (power.equals(Values.OFF)){
			this.updateStatusTo(WifiRouterADSL.STATUS_OFF, true);
			this.shutdown();
		} else {
			this.updateStatusTo(WifiRouterADSL.STATUS_OFF, false);
			if (cStatus.equals(Values.DISCONNECTED)){
				this.updateStatusTo(WifiRouterADSL.STATUS_DISCONNECTED, true);
				this.updateStatusTo(WifiRouterADSL.STATUS_CONGESTED, false);
				this.updateStatusTo(WifiRouterADSL.STATUS_NOISP_SERVICE, false);
				this.updateStatusTo(WifiRouterADSL.STATUS_OK, false);
			} else  {
				this.updateStatusTo(WifiRouterADSL.STATUS_DISCONNECTED, true);
				if (cStatus.equals(Values.NO_IP)){
					this.updateStatusTo(WifiRouterADSL.STATUS_NOISP_SERVICE, true);
					this.updateStatusTo(WifiRouterADSL.STATUS_CONGESTED, false);
					this.updateStatusTo(WifiRouterADSL.STATUS_OK, false);
				} else {
					this.updateStatusTo(WifiRouterADSL.STATUS_NOISP_SERVICE, false);
					if (congestion>30){
						this.updateStatusTo(WifiRouterADSL.STATUS_CONGESTED, true);
						this.updateStatusTo(WifiRouterADSL.STATUS_OK, false);
					} else {
						this.updateStatusTo(WifiRouterADSL.STATUS_CONGESTED, false);
						this.updateStatusTo(WifiRouterADSL.STATUS_OK, true);
					}
				}
			}
						
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(STATUS_OFF);
		this.addPossibleStatus(STATUS_OK);
		this.addPossibleStatus(STATUS_CONGESTED);
		this.addPossibleStatus(STATUS_DISCONNECTED);
		this.addPossibleStatus(STATUS_NOISP_SERVICE);
		this.addPossibleStatus(STATUS_STEALING_CONNECTION);
	}
	
	/**
	 * Accessory method that configures the device on shut down state. 
	 * 
	 * @throws UnsupportedNetworkElementStatusException
	 */
	private void shutdown() throws UnsupportedNetworkElementStatusException {
		this.updatePropertyTo(WifiRouterADSL.PROPERTY_POWER, Values.OFF);
		this.updatePropertyTo(WifiRouterADSL.PROPERTY_CONNECTION_TYPE, Values.NA);
		this.updatePropertyTo(WifiRouterADSL.PROPERTY_CONNECTION, Values.DISCONNECTED);
		this.updatePropertyTo(WifiRouterADSL.PROPERTY_CONGESTION, 0);
		this.updateStatusTo(WifiRouterADSL.STATUS_CONGESTED, false);
		this.updateStatusTo(WifiRouterADSL.STATUS_DISCONNECTED, true);
		this.updateStatusTo(WifiRouterADSL.STATUS_NOISP_SERVICE, false);
		this.updateStatusTo(WifiRouterADSL.STATUS_OK, false);
		this.updateStatusTo(WifiRouterADSL.STATUS_STEALING_CONNECTION, false);
	}

}
