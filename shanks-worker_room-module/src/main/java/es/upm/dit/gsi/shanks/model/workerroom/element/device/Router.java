package es.upm.dit.gsi.shanks.model.workerroom.element.device;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;


/**
 * Class that represent a router
 * 
 * @author dlara
 *
 */
public class Router extends Device{
	
	public static final String STATUS_OFF = "OFF";
    public static final String STATUS_OK = "OK";
    public static final String STATUS_CONGESTED = "CONGESTED";
    public static final String STATUS_DISCONNECTED = "DISCONNECTED";
    public static final String STATUS_NOISP_SERVICE = "NoISPService";

    public static final String PROPERTY_POWER = "Power";
    public static final String PROPERTY_EXTERNAL_CONNECTION = "Connection";
    public static final String PROPERTY_CONGESTION = "Congestion";


	public Router(String id, String initialState, boolean isGateway)
			throws UnsupportedNetworkElementStatusException {
		super(id, initialState, isGateway);
	}


	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
		HashMap<String, Boolean> status = this.getStatus();
		if(status.get(Router.STATUS_OK)){
			this.updatePropertyTo(Router.PROPERTY_CONGESTION, 0);
			this.updatePropertyTo(Router.PROPERTY_EXTERNAL_CONNECTION, "OK");
			this.updatePropertyTo(Router.PROPERTY_POWER, true);
		}
		if(status.get(Router.STATUS_CONGESTED)){
			this.updatePropertyTo(Router.PROPERTY_CONGESTION, 30+Math.random()*70);
		}
		if(status.get(Router.STATUS_DISCONNECTED)){
			this.updatePropertyTo(Router.PROPERTY_EXTERNAL_CONNECTION, "DISCONNECTED");
		}
		if(status.get(Router.STATUS_NOISP_SERVICE)){
			this.updatePropertyTo(Router.PROPERTY_EXTERNAL_CONNECTION, "NO_IP");
		}
		if(status.get(Router.STATUS_OFF)){
			this.updatePropertyTo(Router.PROPERTY_POWER, false);
		}
		
	}

	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		Boolean power = (Boolean) this.getProperty(Router.PROPERTY_POWER);
		String connection = (String) this.getProperty(Router.PROPERTY_EXTERNAL_CONNECTION);
		Double congestion = (Double) this.getProperty(Router.PROPERTY_CONGESTION);
		
		if(power && connection.equals("OK") && congestion < 30){
			this.updateStatusTo(Router.STATUS_OK, true);
			this.updateStatusTo(Router.STATUS_OFF, false);
			this.updateStatusTo(Router.STATUS_CONGESTED, false);
			this.updateStatusTo(Router.STATUS_NOISP_SERVICE, false);
			this.updateStatusTo(Router.STATUS_DISCONNECTED, false);
		}
		if(!power){
			this.updateStatusTo(Router.STATUS_OK, false);
			this.updateStatusTo(Router.STATUS_OFF, true);
		}
		if(connection.equals("DISCONNECTED")){
			this.updateStatusTo(Router.STATUS_OK, false);
			this.updateStatusTo(Router.STATUS_DISCONNECTED, true);
		}
		if(connection.equals("NO-IP")){
			this.updateStatusTo(Router.STATUS_OK, false);
			this.updateStatusTo(Router.STATUS_NOISP_SERVICE, true);
		}
		if(congestion > 30){
			this.updateStatusTo(Router.STATUS_OK, false);
			this.updateStatusTo(Router.STATUS_CONGESTED, true);
		}
	}

	
	@Override
	public void fillIntialProperties() {
		this.addProperty(Router.PROPERTY_EXTERNAL_CONNECTION, "OK");
		this.addProperty(Router.PROPERTY_POWER, true);
		this.addProperty(Router.PROPERTY_CONGESTION, 0);
	}
	
	
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(Router.STATUS_CONGESTED);
		this.addPossibleStatus(Router.STATUS_DISCONNECTED);
		this.addPossibleStatus(Router.STATUS_NOISP_SERVICE);
		this.addPossibleStatus(Router.STATUS_OFF);
		this.addPossibleStatus(Router.STATUS_OK);		
	}

}
