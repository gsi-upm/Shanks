package es.upm.dit.gsi.shanks.model.ftth.element.device;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.ftth.element.device.DeviceDefinitions;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * OLT class
 * 
 * Create the OLTs of the scenario
 * 
 * @author Daniel Lara
 * @version 0.1
 * 
 */

public class OLT extends Device {

    private static final long serialVersionUID = -6004213563737699458L;
    
    /**
     * @param id
     * @param initialState
     * @param isGateway
     * @throws UnsupportedNetworkElementStatusException
     */

    public OLT(String id, String status, boolean isGateway) throws UnsupportedNetworkElementStatusException {
        super(id, status, isGateway);
    }

    private String coreConnectionState; //OK, NOK, BROKEN
    private String ftthConnectionState; //OK, NOK, BROKEN
    private boolean emitedLaserPower;
    private boolean receivedLaserPower;
    private int inputBitrate;
    private int outputBitrate;

    
    public String getCoreConnectionState() {
        return coreConnectionState;
    }

    public void setCoreConnectionState(String coreConnectionState) {
        this.coreConnectionState = coreConnectionState;
    }

    public String getFtthConnectionState() {
        return ftthConnectionState;
    }

    public void setFtthConnectionState(String ftthConnectionState) {
        this.ftthConnectionState = ftthConnectionState;
    }

    public boolean getEmitedLaserPower() {
        return emitedLaserPower;
    }

    public void setEmitedLaserPower(boolean emitedLaserPower) {
        this.emitedLaserPower = emitedLaserPower;
    }

    public boolean getReceivedLaserPower() {
        return receivedLaserPower;
    }

    public void setReceivedLaserPower(boolean receivedLaserPower) {
        this.receivedLaserPower = receivedLaserPower;
    }

    public int getInputBitrate() {
        return inputBitrate;
    }

    public void setInputBitrate(int inputBitrate) {
        this.inputBitrate = inputBitrate;
    }

    public int getOutputBitrate() {
        return outputBitrate;
    }

    public void setOutputBitrate(int outputBitrate) {
        this.outputBitrate = outputBitrate;
    }

    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
     */
    //TODO "Complicarlo" más de momento es muy sencillo, en el futuro usar las caracteristicas reales de un OLT
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
	 //TODO "Complicarlo" más de momento es muy sencillo, en el futuro usar las caracteristicas reales de un OLT
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
