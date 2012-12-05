package es.upm.dit.gsi.shanks.magneto.model.element.device;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.device.Device;



public class Computer extends Device{
	
	public Computer(String id, String initialState, boolean isGateway){
		super(id, initialState, isGateway);
	}

	public static final String STATUS_ON = "On";
	public static final String STATUS_DISCONNECTED = "Disconnected";
	
	public static final String PROPERTY_POWER = "Power";
	public static final String PROPERTY_LINKCONNECTION = "Link Connection";
	
	@Override
	public void fillIntialProperties() {
		this.addProperty(PROPERTY_POWER, true);
		this.addProperty(PROPERTY_LINKCONNECTION, true);	
	}

	@Override
	public void checkProperties() throws ShanksException {
		HashMap <String, Boolean> status = this.getStatus();
		if(status.get(STATUS_ON)){
			this.updatePropertyTo(PROPERTY_POWER, true);
		}else{
			this.updatePropertyTo(PROPERTY_POWER, false);	
		}
		if(status.get(STATUS_DISCONNECTED)){
			this.updatePropertyTo(PROPERTY_LINKCONNECTION, false);
		}else{
			this.updatePropertyTo(PROPERTY_LINKCONNECTION, true);
		}
		
	}

	@Override
	public void checkStatus() throws ShanksException {
		Boolean power = (Boolean) this.getProperty(PROPERTY_POWER);
		Boolean connection = (Boolean) this.getProperty(PROPERTY_LINKCONNECTION);
		
		if(power){
			this.updateStatusTo(STATUS_ON, true);
		}else{
			this.updateStatusTo(STATUS_ON, false);
		}
		
		if(connection){
			this.updateStatusTo(STATUS_DISCONNECTED, false);
		}else{
			this.updateStatusTo(STATUS_DISCONNECTED, true);
		}
		
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(STATUS_ON);
		this.addPossibleStatus(STATUS_DISCONNECTED);
		
	}
}
