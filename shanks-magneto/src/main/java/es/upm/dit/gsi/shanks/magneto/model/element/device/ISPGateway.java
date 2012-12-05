package es.upm.dit.gsi.shanks.magneto.model.element.device;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.device.Device;

public class ISPGateway extends Device{

	public ISPGateway(String id, String initialState, boolean isGateway) {
		super(id, initialState, isGateway);
		
	}

	public static final String STATUS_OK = "Ok";

	
	public static final String PROPERTY_CONNECTION_TO_USER = "ISP to User";
	public static final String PROPERTY_CONNECTION_TO_PROVIDER = "ISP to Provider";
	
	@Override
	public void fillIntialProperties() {
		this.addProperty(PROPERTY_CONNECTION_TO_USER, 0);
		this.addProperty(PROPERTY_CONNECTION_TO_PROVIDER, 0);
	}

	@Override
	public void checkProperties() throws ShanksException {
		HashMap <String, Boolean> status = this.getStatus();
		if(status.get(STATUS_OK)){
			this.updatePropertyTo(PROPERTY_CONNECTION_TO_PROVIDER, 0);
			this.updatePropertyTo(PROPERTY_CONNECTION_TO_USER, 0);
		}
		
	}

	@Override
	public void checkStatus() throws ShanksException {
		Integer provider = (Integer) this.getProperty(PROPERTY_CONNECTION_TO_PROVIDER);
		Integer user = (Integer) this.getProperty(PROPERTY_CONNECTION_TO_USER);
		
		if(provider == 0 && user == 0){
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
