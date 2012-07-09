package es.upm.dit.gsi.shanks.tutorial.model.element.links;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.link.Link;

/**
 * 
 * @author Daniel Lara
 * 
 * A link that represents the connections between the computers and router
 *
 */

public class EthernetLink extends Link{
	
	public static final String STATUS_OK = "OK";
	public static final String STATUS_DAMAGED = "Damaged";
	public static final String STATUS_BROKEN = "Broken";
	
	public static final String PROPERTY_STATUS = "Status"; // 0-100 An abstract property to check the status of the link
														   // if less than 30 the link is OK, between 30-70 the link is damaged
														   // and more than 70 the link is broken
	public EthernetLink(String id, String initialState, int capacity)
			throws ShanksException {
		super(id, initialState, capacity);
	}

	@Override
	public void fillIntialProperties() {
		this.addProperty(PROPERTY_STATUS, 0);	
	}

	@Override
	public void checkProperties() throws ShanksException {
		HashMap<String, Boolean> status = this.getStatus(); //Take all states of the device
		
		if(status.get(STATUS_OK)){
			this.updatePropertyTo(PROPERTY_STATUS, 0);
		}
		if(status.get(STATUS_DAMAGED)){
			this.updatePropertyTo(PROPERTY_STATUS, 50);
		}
		if(status.get(STATUS_BROKEN)){
			this.updatePropertyTo(PROPERTY_STATUS, 90);
		}
		
	}

	@Override
	public void checkStatus() throws ShanksException {
		Integer status = (Integer) this.getProperty(PROPERTY_STATUS);
		if(status >= 30){
			this.updateStatusTo(STATUS_OK, true);
			this.updateStatusTo(STATUS_BROKEN, false);
			this.updateStatusTo(STATUS_DAMAGED, false);
		}
		if(status > 30 && status <= 70){
			this.updateStatusTo(STATUS_OK, false);
			this.updateStatusTo(STATUS_BROKEN, false);
			this.updateStatusTo(STATUS_DAMAGED, true);
		}
		if(status > 70){
			this.updateStatusTo(STATUS_OK, false);
			this.updateStatusTo(STATUS_BROKEN, true);
			this.updateStatusTo(STATUS_DAMAGED, false);
		}
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(STATUS_OK);
		this.addPossibleStatus(STATUS_BROKEN);
		this.addPossibleStatus(STATUS_DAMAGED);
		
	}

}
