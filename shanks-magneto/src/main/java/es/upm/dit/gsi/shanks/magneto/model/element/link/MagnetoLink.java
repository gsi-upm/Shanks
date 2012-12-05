package es.upm.dit.gsi.shanks.magneto.model.element.link;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.link.Link;

public class MagnetoLink extends Link{

	public MagnetoLink(String id, String initialState, int capacity)
			throws ShanksException {
		super(id, initialState, capacity);
		
	}

	public static final String STATUS_OK = "Ok";
	
	public static final String PROPERTY_LENGTH = "Length";
	
	
	@Override
	public void fillIntialProperties() {
		this.addProperty(PROPERTY_LENGTH, 20);
		
	}

	@Override
	public void checkProperties() throws ShanksException {
		HashMap <String, Boolean> status = this.getStatus();
		
		if(status.get(STATUS_OK)){
			this.updatePropertyTo(PROPERTY_LENGTH, 20);
		}		
	}

	@Override
	public void checkStatus() throws ShanksException {
		Integer length = (Integer) this.getProperty(PROPERTY_LENGTH);
		
		if(length == 20){
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
