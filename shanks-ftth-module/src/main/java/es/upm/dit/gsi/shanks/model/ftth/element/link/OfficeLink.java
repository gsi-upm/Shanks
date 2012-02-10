package es.upm.dit.gsi.shanks.model.ftth.element.link;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;

public class OfficeLink extends Link{

	
	/**
     * @param id
     * @param initialState
     * @param capacity
     * @throws UnsupportedNetworkElementStatusException
     */
	public OfficeLink(String id, String initialState, int capacity)
			throws UnsupportedNetworkElementStatusException {
		super(id, initialState, capacity);
		// TODO Auto-generated constructor stub
	}

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
     */
	@Override
	public void fillIntialProperties() {
		this.addProperty(LinkDefinitions.LINK_TYPE_PROPERTY, "Ethernet");
        this.addProperty(LinkDefinitions.DISTANCE_PROPERTY, 3.5);
		
	}

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
     */
	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
		String status = this.getCurrentStatus();
        if (status.equals(LinkDefinitions.BROKEN_STATUS)) {
            this.changeProperty(LinkDefinitions.DISTANCE_PROPERTY, 0.0);
        } else if (status.equals(LinkDefinitions.OK_STATUS)) {
            this.changeProperty(LinkDefinitions.DISTANCE_PROPERTY, 3.5);
        }
		
	}

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
     */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		Double distance = (Double) this.getProperty(LinkDefinitions.DISTANCE_PROPERTY);
        if (distance>0){
            this.updateStatusTo(LinkDefinitions.OK_STATUS);
        }
		
	}

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
     */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(LinkDefinitions.OK_STATUS);
        this.addPossibleStatus(LinkDefinitions.BROKEN_STATUS);
		
	}

}
