package es.upm.dit.gsi.shanks.magneto.model.element.device;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.device.Device;

public class Server extends Device{

	public Server(String id, String initialState, boolean isGateway) {
		super(id, initialState, isGateway);

	}

	public static final String STATUS_OK = "Ok";
	
	public static final String PROPERTY_SERVER_CPU_LOAD = "CPU Load";
	public static final String PROPERTY_SERVICE_LOOK_UP = "Service Look Up";
	
	@Override
	public void fillIntialProperties() {
		this.addProperty(PROPERTY_SERVICE_LOOK_UP, 0);
		this.addProperty(PROPERTY_SERVER_CPU_LOAD, 1);
		
	}

	@Override
	public void checkProperties() throws ShanksException {
		HashMap <String, Boolean> status = this.getStatus();
		
		if(status.get(STATUS_OK)){
			this.updatePropertyTo(PROPERTY_SERVER_CPU_LOAD, 1);
			this.updatePropertyTo(PROPERTY_SERVICE_LOOK_UP, 0);
		}
		
	}

	@Override
	public void checkStatus() throws ShanksException {
		Integer look = (Integer) this.getProperty(PROPERTY_SERVICE_LOOK_UP);
		Integer load = (Integer) this.getProperty(PROPERTY_SERVER_CPU_LOAD);
		
		if(look == 0 && load == 1){
			this.updateStatusTo(STATUS_OK, true);
		}else{
			this.updateStatusTo(STATUS_OK, false);
		}
		
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(STATUS_OK);
		
	}

}
