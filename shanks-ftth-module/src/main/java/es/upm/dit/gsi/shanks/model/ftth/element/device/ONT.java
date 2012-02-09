package es.upm.dit.gsi.shanks.model.ftth.element.device;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * ONT class
 * 
 * Create the ONTs of the scenario
 * 
 * @author Daniel Lara
 * @version 0.1
 * 
 */

public class ONT extends Device {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7825776336743519245L;

    public ONT(String id, String status, boolean isGateway) throws UnsupportedNetworkElementStatusException {
        super(id, status, isGateway);
    }

    private int gatewayConnectionState;
    private int fiberConnectionState;
    private int emitedLaserPower;
    private int receivedLaserPower;
    private int inputBitrate;
    private int outputBitrate;

    public int getGatewayConnectionState() {
        return gatewayConnectionState;
    }

    public void setGatewayConnectionState(int gatewayConnectionState) {
        this.gatewayConnectionState = gatewayConnectionState;
    }

    public int getFiberConnectionState() {
        return fiberConnectionState;
    }

    public void setFiberConnectionState(int fiberConnectionState) {
        this.fiberConnectionState = fiberConnectionState;
    }

    public int getEmitedLaserPower() {
        return emitedLaserPower;
    }

    public void setEmitedLaserPower(int emitedLaserPower) {
        this.emitedLaserPower = emitedLaserPower;
    }

    public int getReceivedLaserPower() {
        return receivedLaserPower;
    }

    public void setReceivedLaserPower(int receivedLaserPower) {
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
    //TODO "Complicarlo" más de momento es muy sencillo, en el futuro usar las caracteristicas reales de un ONT
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
	 //TODO "Complicarlo" más de momento es muy sencillo, en el futuro usar las caracteristicas reales de un ONT
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
