package es.upm.dit.gsi.shanks.tutorial.model.element.devices;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.device.Device;

/**
 * 
 * @author Daniel Lara
 * 
 * A device that represent a computer with basic properties and status 
 * in order to simplify the simulation
 *
 */

public class Computer extends Device{
	
	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_HIGHTEMP = "High Temperature";
	public static final String STATUS_DISCONNECTED = "Disconnected"; // Represents if the computer is connected to the LAN
	
	public static final String PROPERTY_TEMPERATURE = "Temperature"; // In ºC
	public static final String PROPERTY_POWER = "Power"; //Power connection
	public static final String PROPERTY_ETHERNET_CONNECTION = "Connection"; //Ethernet connection

	public Computer(String id, String initialState, boolean isGateway)
			throws ShanksException {
		super(id, initialState, isGateway);
	}

	@Override
	public void fillIntialProperties() {
		//Original values of the device
		this.addProperty(PROPERTY_POWER, true);
		this.addProperty(PROPERTY_TEMPERATURE, 50);
		this.addProperty(PROPERTY_ETHERNET_CONNECTION, true);
		
	}

	@Override
	public void checkProperties() throws ShanksException {
		HashMap<String, Boolean> status = this.getStatus(); //Take all states of the device
		//Now we see all the status and change the properties, take care with the new values,
		//if they are wrong the simulation maybe launch errors
		if(status.get(STATUS_OK)){
			this.updatePropertyTo(PROPERTY_POWER, true);
			this.updatePropertyTo(PROPERTY_ETHERNET_CONNECTION, true);
			this.updatePropertyTo(PROPERTY_TEMPERATURE, 50);
		}if(status.get(STATUS_OFF)){
			this.updatePropertyTo(PROPERTY_POWER, false);
		}if(status.get(STATUS_DISCONNECTED)){
			this.updatePropertyTo(PROPERTY_ETHERNET_CONNECTION, false);
		}if(status.get(STATUS_HIGHTEMP)){
			this.updatePropertyTo(PROPERTY_TEMPERATURE, 80);
		}
		
	}

	@Override
	public void checkStatus() throws ShanksException {
		//We take the values of the properties, be carefully with double and integers types
		Boolean ethernet = (Boolean) this.getProperty(Computer.PROPERTY_ETHERNET_CONNECTION);
		Boolean power = (Boolean) this.getProperty(Computer.PROPERTY_POWER);
		Integer temperature = (Integer) this.getProperty(Computer.PROPERTY_TEMPERATURE);
		if(ethernet && power && temperature < 80){
			this.updateStatusTo(STATUS_OK, true);
			this.updateStatusTo(STATUS_DISCONNECTED, false);
			this.updateStatusTo(STATUS_HIGHTEMP, false);
			this.updateStatusTo(STATUS_OFF, false);
		}
		if(!ethernet){
			this.updateStatusTo(STATUS_DISCONNECTED, true);
		}
		if(!power){
			this.updateStatusTo(STATUS_OFF, true);
		}
		if(temperature >= 80){
			this.updateStatusTo(STATUS_HIGHTEMP, true);
		}

		
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(STATUS_OFF);
		this.addPossibleStatus(STATUS_OK);
		this.addPossibleStatus(STATUS_DISCONNECTED);
		this.addPossibleStatus(STATUS_HIGHTEMP);
		
	}

}
