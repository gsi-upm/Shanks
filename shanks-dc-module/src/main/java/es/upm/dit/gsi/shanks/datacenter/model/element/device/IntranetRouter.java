package es.upm.dit.gsi.shanks.datacenter.model.element.device;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.datacenter.model.Values;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

public class IntranetRouter extends Device {

	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_CONGESTED = "Congested";
	public static final String STATUS_DISCONNECTED = "Disconnected";
	public static final String STATUS_NOISP_SERVICE = "NoISPService";

	public static final String PROPERTY_POWER = "Power";
	public static final String PROPERTY_CONNECTION = "ConnectionStatus";
	public static final String PROPERTY_CONGESTION = "Congestion"; // in %
	
	public IntranetRouter(String id, String initialState, boolean isGateway)
			throws UnsupportedNetworkElementStatusException {
		super(id, initialState, isGateway);
	}
	
	public IntranetRouter(String id, boolean isGateway)
			throws UnsupportedNetworkElementStatusException {
		super(id, IntranetRouter.STATUS_OK, isGateway);
	}
	
	public IntranetRouter(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id, IntranetRouter.STATUS_OK, true);
	}	

	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
	 */
	@Override
	public void fillIntialProperties() {
		this.addProperty(IntranetRouter.PROPERTY_POWER, Values.ON);
		this.addProperty(IntranetRouter.PROPERTY_CONNECTION, Values.CONNECTED);
		this.addProperty(IntranetRouter.PROPERTY_CONGESTION, 0.0);
	}
	
	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
	 */
	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
        HashMap<String, Boolean> status = this.getStatus();
		if (status.get(IntranetRouter.STATUS_OFF)) {
			this.shutdown();
		} else {
			this.updatePropertyTo(IntranetRouter.PROPERTY_POWER, Values.ON);
			if (status.get(IntranetRouter.STATUS_OK)) {
				this.updatePropertyTo(IntranetRouter.PROPERTY_CONNECTION, Values.CONNECTED);
				this.updatePropertyTo(PROPERTY_CONGESTION, Math.random()*10);
			} else if (status.get(IntranetRouter.STATUS_CONGESTED)) {
				this.updatePropertyTo(IntranetRouter.PROPERTY_CONNECTION, Values.CONNECTED);
				this.updatePropertyTo(IntranetRouter.PROPERTY_CONGESTION, 30+Math.random()*60);
			} else if (status.equals(IntranetRouter.STATUS_DISCONNECTED)){
				this.updatePropertyTo(IntranetRouter.PROPERTY_CONNECTION, Values.DISCONNECTED);
			} else if (status.equals(IntranetRouter.STATUS_NOISP_SERVICE)){
				this.updatePropertyTo(IntranetRouter.PROPERTY_CONNECTION, Values.NO_IP);
			} 
		}
	}

	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		String power = (String) this.getProperty(IntranetRouter.PROPERTY_POWER);
		String cStatus = (String) this.getProperty(IntranetRouter.PROPERTY_CONNECTION);
		double congestion = (Double) this.getProperty(IntranetRouter.PROPERTY_CONGESTION);
		
		if (power.equals(Values.OFF)){
			this.updateStatusTo(IntranetRouter.STATUS_OFF, true);
			this.shutdown();
		} else {
			this.updateStatusTo(IntranetRouter.STATUS_OFF, false);
			if (cStatus.equals(Values.DISCONNECTED)){
				this.updateStatusTo(IntranetRouter.STATUS_DISCONNECTED, true);
				this.updateStatusTo(IntranetRouter.STATUS_CONGESTED, false);
				this.updateStatusTo(IntranetRouter.STATUS_NOISP_SERVICE, false);
				this.updateStatusTo(IntranetRouter.STATUS_OK, false);
			} else  {
				this.updateStatusTo(IntranetRouter.STATUS_DISCONNECTED, false);
				if (cStatus.equals(Values.NO_IP)){
					this.updateStatusTo(IntranetRouter.STATUS_NOISP_SERVICE, true);
					this.updateStatusTo(IntranetRouter.STATUS_CONGESTED, false);
					this.updateStatusTo(IntranetRouter.STATUS_OK, false);
				} else {
					this.updateStatusTo(IntranetRouter.STATUS_NOISP_SERVICE, false);
					if (congestion>30){
						this.updateStatusTo(IntranetRouter.STATUS_CONGESTED, true);
						this.updateStatusTo(IntranetRouter.STATUS_OK, false);
					} else {
						this.updateStatusTo(IntranetRouter.STATUS_CONGESTED, false);
						this.updateStatusTo(IntranetRouter.STATUS_OK, true);
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
		this.updatePropertyTo(IntranetRouter.PROPERTY_POWER, Values.OFF);
		this.updatePropertyTo(IntranetRouter.PROPERTY_CONNECTION, Values.DISCONNECTED);
		this.updatePropertyTo(IntranetRouter.PROPERTY_CONGESTION, 0);
		this.updateStatusTo(IntranetRouter.STATUS_CONGESTED, false);
		this.updateStatusTo(IntranetRouter.STATUS_DISCONNECTED, false);
		this.updateStatusTo(IntranetRouter.STATUS_NOISP_SERVICE, false);
		this.updateStatusTo(IntranetRouter.STATUS_OK, false);
	}
}
