package es.upm.dit.gsi.shanks.shanks_enterprise_module.model.element;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

public class IntranetRouter extends Device{

	public static final String STATUS_OK = "Ok";
	public static final String STATUS_OFF ="Off";
	
	public static final String PROPERTY_POWER = "Power";
	
	public IntranetRouter(String id, String initialState, boolean isGateway)
			throws UnsupportedNetworkElementStatusException {
		super(id, initialState, isGateway);
	}

	@Override
	public void fillIntialProperties() {
		this.addProperty(PROPERTY_POWER, true);
		
	}

	@Override
	public void checkStatus()
			throws UnsupportedNetworkElementStatusException {
		Boolean power = (Boolean) this.getProperty(PROPERTY_POWER);
		if(power){
			this.updateStatusTo(STATUS_OFF, false);
			this.updateStatusTo(STATUS_OK, true);
		}else{
			this.updateStatusTo(STATUS_OFF, true);
			this.updateStatusTo(STATUS_OK, false);
		}
	}

	@Override
	public void checkProperties() throws UnsupportedNetworkElementStatusException {
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(STATUS_OFF);
		this.addPossibleStatus(STATUS_OK);
	}

}
