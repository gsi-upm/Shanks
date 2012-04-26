package es.upm.dit.gsi.shanks.hackerhan.model.han.element.device;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.hackerhan.model.han.element.Values;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

public class WifiRouterADSL extends Device {

	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_CONGESTED = "Congested";
	public static final String STATUS_DISCONNECTED = "Disconnected";
	public static final String STATUS_NOISP_SERVICE = "NoISPService";
	public static final String STATUS_STEALING_CONNECTION = "StealingConnection";

	public static final String PROPERTY_POWER = "Power";
	public static final String PROPERTY_EXTERNAL_CONNECTION = "ConnectionStatus";
	public static final String PROPERTY_CONNECTION_TYPE = "ConnectionType";
	public static final String PROPERTY_CONGESTION = "Congestion";
	
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

	@Override
	public void fillIntialProperties() {
		this.addProperty(WifiRouterADSL.PROPERTY_POWER, Values.ON);
		this.addProperty(WifiRouterADSL.PROPERTY_EXTERNAL_CONNECTION, Values.CONNECTED);
		this.addProperty(WifiRouterADSL.PROPERTY_CONNECTION_TYPE, WifiRouterADSL.CONNECTION_TYPE_ISP);
		this.addProperty(WifiRouterADSL.PROPERTY_CONGESTION, 0);
	}

	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
		// TODO Adapt the hole thing to HashMap String/boolean. 
        HashMap<String, Boolean> status = this.getStatus();
		if (status.get(EthernetRouter.STATUS_OFF)) {
			this.shutdown();
		} else {
			this.updatePropertyTo(EthernetRouter.PROPERTY_POWER, Values.ON);
			if(status.get(WifiRouterADSL.STATUS_STEALING_CONNECTION)) {
				this.updatePropertyTo(WifiRouterADSL.PROPERTY_CONNECTION_TYPE, WifiRouterADSL.CONNECTION_TYPE_STEAL);
			} else {
				this.updatePropertyTo(WifiRouterADSL.PROPERTY_CONNECTION_TYPE, WifiRouterADSL.CONNECTION_TYPE_ISP);
			}
			if (status.get(EthernetRouter.STATUS_OK)) {
				this.updatePropertyTo(EthernetRouter.PROPERTY_EXTERNAL_CONNECTION, Values.CONNECTED);
				this.updatePropertyTo(PROPERTY_CONGESTION, Math.random()*10);
			} else if (status.get(EthernetRouter.STATUS_CONGESTED)) {
				this.updatePropertyTo(EthernetRouter.PROPERTY_EXTERNAL_CONNECTION, Values.CONNECTED);
				this.updatePropertyTo(EthernetRouter.PROPERTY_CONGESTION, 30+Math.random()*60);
			} else if (status.equals(EthernetRouter.STATUS_DISCONNECTED)){
				this.updatePropertyTo(EthernetRouter.PROPERTY_EXTERNAL_CONNECTION, "DISCONNECTED");
				this.updatePropertyTo(PROPERTY_CONNECTION_TYPE, Values.NA);
			} else if (status.equals(EthernetRouter.STATUS_NOISP_SERVICE)){
				this.updatePropertyTo(EthernetRouter.PROPERTY_EXTERNAL_CONNECTION, "NO-IP");
			} 
		}
	}

	private void shutdown() throws UnsupportedNetworkElementStatusException {
		this.updatePropertyTo(WifiRouterADSL.PROPERTY_POWER, Values.OFF);
		this.updatePropertyTo(WifiRouterADSL.PROPERTY_CONNECTION_TYPE, Values.NA);
		this.updatePropertyTo(WifiRouterADSL.PROPERTY_EXTERNAL_CONNECTION, Values.DISCONNECTED);
		this.updatePropertyTo(WifiRouterADSL.PROPERTY_CONGESTION, 0);
	}

	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPossibleStates() {
		// TODO Auto-generated method stub
		
	}

}
