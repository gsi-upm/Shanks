package es.upm.dit.gsi.shanks.shanks_isp_module.model.element.link;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;

public class CupperLink extends Link{

	public static final String STATUS_OK = "Ok";
	public static final String STATUS_CUT = "Cut";
	public static final String STATUS_BLOCKED = "Blocked";
	
	public static final String PROPERTY_PACKETLOSTRATIO = "Packet Lost Ratio";
	
	public CupperLink(String id, String initialState, int capacity)
			throws UnsupportedNetworkElementStatusException {
		super(id, initialState, capacity);
		
	}

	@Override
	public void fillIntialProperties() {
		this.addProperty(PROPERTY_PACKETLOSTRATIO, 0.001);
		
	}

	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(STATUS_CUT);
		this.addPossibleStatus(STATUS_OK);
		this.addPossibleStatus(STATUS_BLOCKED);
		
	}

}
