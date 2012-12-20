package es.upm.dit.gsi.shanks.magneto.model.element.device;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.device.Device;

public class ServerGateway extends Device{
	
	public static final String STATUS_OK = "Ok";
	
	public static final String PROPERTY_PROVIDERHAN = "Provider HAN";
	public static final String PROPERTY_LOCALACCESS = "Local Acces";
	
	public int counter;

	public ServerGateway(String id, String initialState, boolean isGateway) {
		super(id, initialState, isGateway);
		this.counter = 0;
	}


	@Override
	public void fillIntialProperties() {
		this.addProperty(PROPERTY_PROVIDERHAN, 0);
		this.addProperty(PROPERTY_LOCALACCESS, 2);
		
	}

	@Override
	public void checkProperties() throws ShanksException {
		HashMap <String, Boolean> status = this.getStatus();
		if(status.get(STATUS_OK)){
			this.updatePropertyTo(PROPERTY_LOCALACCESS, 2);
			this.updatePropertyTo(PROPERTY_PROVIDERHAN, 0);
		}
	}

	@Override
	public void checkStatus() throws ShanksException {
		Integer local = (Integer) this.getProperty(PROPERTY_LOCALACCESS);
		Integer han = (Integer) this.getProperty(PROPERTY_PROVIDERHAN);
		
		if(han == 0 && local == 2){
			this.updateStatusTo(STATUS_OK, true);
		}else{
			counter++;
			this.updateStatusTo(STATUS_OK, false);
		}
		
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(STATUS_OK);
		
	}
	
	public int getCounter(){
		return counter;
	}
	
	public void resetCounter(){
		counter = 0;
	}

}
