package es.upm.dit.gsi.shanks.networkattacks.util.networkelements;

import java.util.ArrayList;
import java.util.HashMap;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.networkattacks.util.Values;
import es.upm.dit.gsi.shanks.networkattacks.util.notifications.DNSConsult;
import es.upm.dit.gsi.shanks.notification.NotificationManager;

public class RouterDNS extends Device{

	private Scenario parentScenario;
	
	
	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_CONGESTED = "Congested";
	public static final String STATUS_DISCONNECTED = "Disconnected";
	public static final String STATUS_NOISP_SERVICE = "NoISPService";

	public static final String PROPERTY_POWER = "Power";
	public static final String PROPERTY_CONNECTION = "ConnectionStatus";
	public static final String PROPERTY_CONGESTION = "Congestion"; // in %
	
//	public RouterDNSDNS(String id, String initialState, boolean isGateway)
//			throws UnsupportedNetworkElementStatusException {
//		super(id, initialState, isGateway);
//	}
//	
//	public RouterDNSDNS(String id, boolean isGateway)
//			throws UnsupportedNetworkElementStatusException {
//		super(id, RouterDNS.STATUS_OK, isGateway);
//	}
	
	public RouterDNS(String id, Scenario parent)
			throws UnsupportedNetworkElementStatusException {
		super(id, RouterDNS.STATUS_OK, true);
		this.parentScenario = parent;
	}	

	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
	 */
	@Override
	public void fillIntialProperties() {
		this.addProperty(RouterDNS.PROPERTY_POWER, Values.ON);
		this.addProperty(RouterDNS.PROPERTY_CONNECTION, Values.CONNECTED);
		this.addProperty(RouterDNS.PROPERTY_CONGESTION, 0.0);
	}
	
	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
	 */
	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
        HashMap<String, Boolean> status = this.getStatus();
		if (status.get(RouterDNS.STATUS_OFF)) {
			this.shutdown();
		} else {
			this.updatePropertyTo(RouterDNS.PROPERTY_POWER, Values.ON);
			if (status.get(RouterDNS.STATUS_OK)) {
				this.updatePropertyTo(RouterDNS.PROPERTY_CONNECTION, Values.CONNECTED);
				this.updatePropertyTo(PROPERTY_CONGESTION, Math.random()*10);
			} else if (status.get(RouterDNS.STATUS_CONGESTED)) {
				this.updatePropertyTo(RouterDNS.PROPERTY_CONNECTION, Values.CONNECTED);
				this.updatePropertyTo(RouterDNS.PROPERTY_CONGESTION, 30+Math.random()*60);
			} else if (status.equals(RouterDNS.STATUS_DISCONNECTED)){
				this.updatePropertyTo(RouterDNS.PROPERTY_CONNECTION, Values.DISCONNECTED);
			} else if (status.equals(RouterDNS.STATUS_NOISP_SERVICE)){
				this.updatePropertyTo(RouterDNS.PROPERTY_CONNECTION, Values.NO_IP);
			} 
		}
	}

	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		String power = (String) this.getProperty(RouterDNS.PROPERTY_POWER);
		String cStatus = (String) this.getProperty(RouterDNS.PROPERTY_CONNECTION);
		double congestion = (Double) this.getProperty(RouterDNS.PROPERTY_CONGESTION);
		
		if (power.equals(Values.OFF)){
			this.updateStatusTo(RouterDNS.STATUS_OFF, true);
			this.shutdown();
		} else {
			this.updateStatusTo(RouterDNS.STATUS_OFF, false);
			if (cStatus.equals(Values.DISCONNECTED)){
				this.updateStatusTo(RouterDNS.STATUS_DISCONNECTED, true);
				this.updateStatusTo(RouterDNS.STATUS_CONGESTED, false);
				this.updateStatusTo(RouterDNS.STATUS_NOISP_SERVICE, false);
				this.updateStatusTo(RouterDNS.STATUS_OK, false);
			} else  {
				this.updateStatusTo(RouterDNS.STATUS_DISCONNECTED, false);
				if (cStatus.equals(Values.NO_IP)){
					this.updateStatusTo(RouterDNS.STATUS_NOISP_SERVICE, true);
					this.updateStatusTo(RouterDNS.STATUS_CONGESTED, false);
					this.updateStatusTo(RouterDNS.STATUS_OK, false);
				} else {
					this.updateStatusTo(RouterDNS.STATUS_NOISP_SERVICE, false);
					if (congestion>30){
						this.updateStatusTo(RouterDNS.STATUS_CONGESTED, true);
						this.updateStatusTo(RouterDNS.STATUS_OK, false);
					} else {
						this.updateStatusTo(RouterDNS.STATUS_CONGESTED, false);
						this.updateStatusTo(RouterDNS.STATUS_OK, true);
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
		this.updatePropertyTo(RouterDNS.PROPERTY_POWER, Values.OFF);
		this.updatePropertyTo(RouterDNS.PROPERTY_CONNECTION, Values.DISCONNECTED);
		this.updatePropertyTo(RouterDNS.PROPERTY_CONGESTION, 0);
		this.updateStatusTo(RouterDNS.STATUS_CONGESTED, false);
		this.updateStatusTo(RouterDNS.STATUS_DISCONNECTED, false);
		this.updateStatusTo(RouterDNS.STATUS_NOISP_SERVICE, false);
		this.updateStatusTo(RouterDNS.STATUS_OK, false);
	}
	/**
	 * Return the object of the gateway-device that the client is asking.
	 * 
	 * @param gatewayID
	 *            The ID of the device that the client is looking for.
	 * @param whoIsAsking
	 *            A reference to the client that is asking for service.
	 * @return A reference to the gateway-device asked, or null if there is no
	 *         one defined under the given ID.
	 */
	public Device getGateway(String gatewayID, Object whoIsAsking) {
		HashMap<String, NetworkElement> elements = this.parentScenario
				.getCurrentElements();
		if (elements.containsKey(gatewayID)) {
			NetworkElement e = elements.get(gatewayID);
			if (e instanceof Device) {
				ArrayList<Object> target = new ArrayList<Object>();
				target.add(e);
				NotificationManager.addNotification(new DNSConsult(whoIsAsking,
						target));
				return (Device) e;
			}
		}
		return null;
	}
	
	/**
	 * @return the parentScenario
	 */
	public Scenario getParentScenario() {
		return parentScenario;
	}

	/**
	 * @param parentScenario the parentScenario to set
	 */
	public void setParentScenario(Scenario parentScenario) {
		this.parentScenario = parentScenario;
	}

}
