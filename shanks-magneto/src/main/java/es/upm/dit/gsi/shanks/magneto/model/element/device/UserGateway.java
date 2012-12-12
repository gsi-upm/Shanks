package es.upm.dit.gsi.shanks.magneto.model.element.device;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.device.Device;

public class UserGateway extends Device{

	public UserGateway(String id, String initialState, boolean isGateway) {
		super(id, initialState, isGateway);
		
	}

	public static final String STATUS_OK = "Ok";
	
	public static final String PROPERTY_CONNECTION_TO_ISP = "User to ISP";
	public static final String PROPERTY_CONNECTION_TO_PROVIDER = "User to Provider";
	public static final String PROPERTY_CONFIGURATION = "Configuration";
	public static final String PROPERTY_USERHAN = "User HAN";
	
	@Override
	public void fillIntialProperties() {
		this.addProperty(PROPERTY_CONNECTION_TO_ISP, 0);
		this.addProperty(PROPERTY_CONNECTION_TO_PROVIDER, 0);
		this.addProperty(PROPERTY_CONFIGURATION, true);
		this.addProperty(PROPERTY_USERHAN, 0);
		
	}

	@Override
	public void checkProperties() throws ShanksException {
		HashMap <String, Boolean> status = this.getStatus();	
		if(status.get(STATUS_OK)){
			this.updatePropertyTo(PROPERTY_CONFIGURATION, true);
			this.updatePropertyTo(PROPERTY_CONNECTION_TO_ISP, 0);
			this.updatePropertyTo(PROPERTY_CONNECTION_TO_PROVIDER, 0);
			this.updatePropertyTo(PROPERTY_USERHAN, 0);
		}
		
	}

	@Override
	public void checkStatus() throws ShanksException {
		Integer provider = (Integer) this.getProperty(PROPERTY_CONNECTION_TO_PROVIDER);
		Integer isp = (Integer) this.getProperty(PROPERTY_CONNECTION_TO_ISP);
		Boolean conf = (Boolean) this.getProperty(PROPERTY_CONFIGURATION);
		Integer userHan = (Integer) this.getProperty(PROPERTY_USERHAN);
		if(provider == 0 && isp == 0 && userHan == 0 && conf){
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
