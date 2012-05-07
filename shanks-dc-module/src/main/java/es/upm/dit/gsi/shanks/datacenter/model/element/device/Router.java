package es.upm.dit.gsi.shanks.datacenter.model.element.device;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.datacenter.model.Values;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

public class Router extends Device {

	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_CONGESTED = "Congested";
	public static final String STATUS_DISCONNECTED = "Disconnected";
	public static final String STATUS_NOISP_SERVICE = "NoISPService";

	public static final String PROPERTY_POWER = "Power";
	public static final String PROPERTY_CONNECTION = "ConnectionStatus";
	public static final String PROPERTY_CONGESTION = "Congestion"; // in %
	
	public Router(String id, String initialState, boolean isGateway)
			throws UnsupportedNetworkElementStatusException {
		super(id, initialState, isGateway);
	}
	
	public Router(String id, boolean isGateway)
			throws UnsupportedNetworkElementStatusException {
		super(id, Router.STATUS_OK, isGateway);
	}
	
	public Router(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id, Router.STATUS_OK, true);
	}	

	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
	 */
	@Override
	public void fillIntialProperties() {
		this.addProperty(Router.PROPERTY_POWER, Values.ON);
		this.addProperty(Router.PROPERTY_CONNECTION, Values.CONNECTED);
		this.addProperty(Router.PROPERTY_CONGESTION, 0.0);
	}
	
	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
	 */
	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
        HashMap<String, Boolean> status = this.getStatus();
		if (status.get(Router.STATUS_OFF)) {
			this.shutdown();
		} else {
			this.updatePropertyTo(Router.PROPERTY_POWER, Values.ON);
			if (status.get(Router.STATUS_OK)) {
				this.updatePropertyTo(Router.PROPERTY_CONNECTION, Values.CONNECTED);
				this.updatePropertyTo(PROPERTY_CONGESTION, Math.random()*10);
			} else if (status.get(Router.STATUS_CONGESTED)) {
				this.updatePropertyTo(Router.PROPERTY_CONNECTION, Values.CONNECTED);
				this.updatePropertyTo(Router.PROPERTY_CONGESTION, 30+Math.random()*60);
			} else if (status.equals(Router.STATUS_DISCONNECTED)){
				this.updatePropertyTo(Router.PROPERTY_CONNECTION, Values.DISCONNECTED);
			} else if (status.equals(Router.STATUS_NOISP_SERVICE)){
				this.updatePropertyTo(Router.PROPERTY_CONNECTION, Values.NO_IP);
			} 
		}
	}

	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		String power = (String) this.getProperty(Router.PROPERTY_POWER);
		String cStatus = (String) this.getProperty(Router.PROPERTY_CONNECTION);
		double congestion = (Double) this.getProperty(Router.PROPERTY_CONGESTION);
		
		if (power.equals(Values.OFF)){
			this.updateStatusTo(Router.STATUS_OFF, true);
			this.shutdown();
		} else {
			this.updateStatusTo(Router.STATUS_OFF, false);
			if (cStatus.equals(Values.DISCONNECTED)){
				this.updateStatusTo(Router.STATUS_DISCONNECTED, true);
				this.updateStatusTo(Router.STATUS_CONGESTED, false);
				this.updateStatusTo(Router.STATUS_NOISP_SERVICE, false);
				this.updateStatusTo(Router.STATUS_OK, false);
			} else  {
				this.updateStatusTo(Router.STATUS_DISCONNECTED, false);
				if (cStatus.equals(Values.NO_IP)){
					this.updateStatusTo(Router.STATUS_NOISP_SERVICE, true);
					this.updateStatusTo(Router.STATUS_CONGESTED, false);
					this.updateStatusTo(Router.STATUS_OK, false);
				} else {
					this.updateStatusTo(Router.STATUS_NOISP_SERVICE, false);
					if (congestion>30){
						this.updateStatusTo(Router.STATUS_CONGESTED, true);
						this.updateStatusTo(Router.STATUS_OK, false);
					} else {
						this.updateStatusTo(Router.STATUS_CONGESTED, false);
						this.updateStatusTo(Router.STATUS_OK, true);
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
	}
	
	/**
	 * Accessory method that configures the device on shut down state. 
	 * 
	 * @throws UnsupportedNetworkElementStatusException
	 */
	private void shutdown() throws UnsupportedNetworkElementStatusException {
		this.updatePropertyTo(Router.PROPERTY_POWER, Values.OFF);
		this.updatePropertyTo(Router.PROPERTY_CONNECTION, Values.DISCONNECTED);
		this.updatePropertyTo(Router.PROPERTY_CONGESTION, 0);
		this.updateStatusTo(Router.STATUS_CONGESTED, false);
		this.updateStatusTo(Router.STATUS_DISCONNECTED, false);
		this.updateStatusTo(Router.STATUS_NOISP_SERVICE, false);
		this.updateStatusTo(Router.STATUS_OK, false);
	}
}
