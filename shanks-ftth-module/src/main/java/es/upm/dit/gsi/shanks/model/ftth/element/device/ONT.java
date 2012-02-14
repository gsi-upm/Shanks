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

    private String gatewayConnectionState = "gatewayConnectionState";
    private String fiberConnectionState = "fiberConnectionState";
    private String emitedLaserPower = "emitedLaserPower";
    private String receivedLaserPower = "receivedLaserPower";
    private String inputBitrate = "inputBitrate";
    private String outputBitrate = "outputBitrate";



    
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
		Integer inBitrate = (Integer) this.getProperty(inputBitrate);
        Integer outBitrate = (Integer) this.getProperty(outputBitrate);
        Integer emitedLaser = (Integer) this.getProperty(emitedLaserPower);
        Integer receivedLaser = (Integer) this.getProperty(receivedLaserPower);
		if (temp<70|| inBitrate >= 1000000000 || outBitrate >= 1000000000 
				|| emitedLaser.equals(DeviceDefinitions.OK_STATUS) || receivedLaser.equals(DeviceDefinitions.OK_STATUS)){
            this.updateStatusTo(DeviceDefinitions.OK_STATUS);
        }else if(temp>70 || inBitrate < 1000000000 || outBitrate < 1000000000 
				|| emitedLaser.equals(DeviceDefinitions.NOK_STATUS) || receivedLaser.equals(DeviceDefinitions.NOK_STATUS)){
        	this.updateStatusTo(DeviceDefinitions.NOK_STATUS);
        }else{
        	this.updateStatusTo(DeviceDefinitions.UNKOWN_STATUS);
        }
		
	}

	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillInitialProperties()
     */
	public void fillIntialProperties() {
		this.addProperty(DeviceDefinitions.OS_PROPERTY, "Linux");
        this.addProperty(DeviceDefinitions.TEMPERATURE_PROPERTY, 20);
        this.addProperty(gatewayConnectionState, DeviceDefinitions.OK_STATUS);
        this.addProperty(fiberConnectionState, DeviceDefinitions.OK_STATUS);
        this.addProperty(inputBitrate, 1000000);
        this.addProperty(outputBitrate, 1000000);
        this.addProperty(receivedLaserPower, 50);
        this.addProperty(emitedLaserPower, 50);
		
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
