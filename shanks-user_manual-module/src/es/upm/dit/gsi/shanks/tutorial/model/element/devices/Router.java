package es.upm.dit.gsi.shanks.tutorial.model.element.devices;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.device.Device;

/**
 * 
 * @author Daniel Lara
 * 
 * A device that represents a router which connect the differents computers in a LAN
 *
 */

public class Router extends Device{
	
	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_CONGESTED = "Congested";
	public static final String STATUS_DISCONNECTED = "Disconnected";
	
	public static final String PROPERTY_POWER = "Power";
	public static final String PROPERTY_CONNECTION = "ConnectionStatus";
	public static final String PROPERTY_CONGESTION = "Congestion"; // in %

	public Router(String id, String initialState, boolean isGateway)
			throws ShanksException {
		super(id, initialState, isGateway);
	
	}

	@Override
	public void fillIntialProperties() {
		this.addProperty(PROPERTY_CONNECTION, true);
		this.addProperty(PROPERTY_POWER, true);
		this.addProperty(PROPERTY_CONGESTION, 0.10);	
	}

	@Override
	public void checkProperties() throws ShanksException {
		HashMap<String, Boolean> status = this.getStatus(); //Take all states of the device
		//Now we see all the status and change the properties, take care with the new values,
		//if they are wrong the simulation maybe launch errors
		if(status.get(STATUS_OK)){
			this.updatePropertyTo(PROPERTY_POWER, true);
			this.updatePropertyTo(PROPERTY_CONNECTION, true);
			this.updatePropertyTo(PROPERTY_CONGESTION, 0.10);
		}
		if(status.get(STATUS_CONGESTED)){
			this.updatePropertyTo(PROPERTY_CONGESTION, 0.80);		
		}
		if(status.get(STATUS_DISCONNECTED)){
			this.updatePropertyTo(PROPERTY_CONNECTION, false);
		}
		if(status.get(STATUS_OFF)){
			this.updatePropertyTo(PROPERTY_POWER, false);
		}
	}

	@Override
	public void checkStatus() throws ShanksException {
		Boolean ethernet = (Boolean) this.getProperty(PROPERTY_CONNECTION);
		Boolean power = (Boolean) this.getProperty(PROPERTY_POWER);
		Double congestion = (Double) this.getProperty(PROPERTY_CONGESTION);
		
		if(ethernet && power && congestion < 0.20){
			this.updateStatusTo(STATUS_OK, true);
			this.updateStatusTo(STATUS_OFF, false);
			this.updateStatusTo(STATUS_CONGESTED, false);
			this.updateStatusTo(STATUS_DISCONNECTED, false);
		}
		if(!ethernet){
			this.updateStatusTo(STATUS_DISCONNECTED, true);
		}
		if(!power){
			this.updateStatusTo(STATUS_OFF, true);			
		}
		if(congestion > 0.20){
			this.updateStatusTo(STATUS_CONGESTED, true);
		}
		
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(STATUS_OFF);
		this.addPossibleStatus(STATUS_OK);
		this.addPossibleStatus(STATUS_CONGESTED);
		this.addPossibleStatus(STATUS_DISCONNECTED);
		
	}

}
