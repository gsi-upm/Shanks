package es.upm.dit.gsi.shanks.model.ftth.element.device;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * Splitter class
 * 
 * Create the Splitters of the scenario
 * 
 * @author Daniel Lara
 * @version 0.1
 * 
 */

public class Splitter extends Device {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8253186044606614656L;

    public Splitter(String id, String status, boolean isGateway) throws UnsupportedNetworkElementStatusException {
        super(id, status, isGateway);
    }

    private int numberOfInputInterfaces;
    private int numberOfOutputInterfaces;

    public int getNumberOfInputInterfaces() {
        return numberOfInputInterfaces;
    }

    public void setNumberOfInputInterfaces(int numberOfInputInterfaces) {
        this.numberOfInputInterfaces = numberOfInputInterfaces;
    }

    public int getNumberOfOutputInterfaces() {
        return numberOfOutputInterfaces;
    }

    public void setNumberOfOutputInterfaces(int numberOfOutputInterfaces) {
        this.numberOfOutputInterfaces = numberOfOutputInterfaces;
    }

    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
     */
    //TODO "Complicarlo" más de momento es muy sencillo, en el futuro usar las caracteristicas reales de un Splitter
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
		String status = this.getCurrentStatus();
        if (status.equals(DeviceDefinitions.OK_STATUS)) {
            this.changeProperty(DeviceDefinitions.TEMPERATURE_PROPERTY, 30);
        } else if (status.equals(DeviceDefinitions.NOK_STATUS)) {
            this.changeProperty(DeviceDefinitions.TEMPERATURE_PROPERTY, 90);
        } else if (status.equals(DeviceDefinitions.UNKOWN_STATUS)) {
            this.changeProperty(DeviceDefinitions.TEMPERATURE_PROPERTY, null);
        }        
		
	}

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
     */
	 //TODO "Complicarlo" más de momento es muy sencillo, en el futuro usar las caracteristicas reales de un Splitter
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		Integer temp = (Integer) this.getProperty(DeviceDefinitions.TEMPERATURE_PROPERTY);
        if (temp<70) {
            this.updateStatusTo(DeviceDefinitions.OK_STATUS);
        }		
	}

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillInitialProperties()
     */
	public void fillIntialProperties() {
		this.addProperty(DeviceDefinitions.OS_PROPERTY, "Linux");
        this.addProperty(DeviceDefinitions.TEMPERATURE_PROPERTY, 20);
		
	}


	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
     */
	public void setPossibleStates() {
		this.addPossibleStatus(DeviceDefinitions.OK_STATUS);
        this.addPossibleStatus(DeviceDefinitions.NOK_STATUS);
        this.addPossibleStatus(DeviceDefinitions.UNKOWN_STATUS);
		
	}

}
