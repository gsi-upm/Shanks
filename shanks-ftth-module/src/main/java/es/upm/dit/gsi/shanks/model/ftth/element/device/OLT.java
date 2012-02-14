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

    private static final String coreConnectionState = "coreConnectionState";
    private static final String ftthConnectionState = "ftthConnectionState";
    private static final String emitedLaserPower = "emitedLaserPower";
    private static final String receivedLaserPower = "receivedLaserPower";
    private static final String inputBitrate = "inputBitrate";
    private static final String outputBitrate = "outputBitrate";


    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
     */
    //TODO "Complicarlo" más de momento es muy sencillo, en el futuro usar las caracteristicas reales de un OLT
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
		String status = this.getCurrentStatus();
        if (status.equals(DeviceDefinitions.OK_STATUS)) {
            this.changeProperty(DeviceDefinitions.TEMPERATURE_PROPERTY, 30);
            this.changeProperty(coreConnectionState, DeviceDefinitions.OK_STATUS);
            this.changeProperty(ftthConnectionState, DeviceDefinitions.OK_STATUS);
            this.changeProperty(emitedLaserPower, 50);
            this.changeProperty(receivedLaserPower, 50);
            this.changeProperty(inputBitrate, 1000000000); 
            this.changeProperty(outputBitrate, 1000000000); 
        } else if (status.equals(DeviceDefinitions.NOK_STATUS)) {
            this.changeProperty(DeviceDefinitions.TEMPERATURE_PROPERTY, 90);
            this.changeProperty(coreConnectionState, DeviceDefinitions.NOK_STATUS);
            this.changeProperty(ftthConnectionState, DeviceDefinitions.NOK_STATUS);
            this.changeProperty(emitedLaserPower, 10);
            this.changeProperty(receivedLaserPower, 10);
            this.changeProperty(inputBitrate, 10000);  //1Gbps
            this.changeProperty(outputBitrate, 10000); //1Gbps
        } else if (status.equals(DeviceDefinitions.UNKOWN_STATUS)) {
            this.changeProperty(DeviceDefinitions.TEMPERATURE_PROPERTY, null);
            this.changeProperty(coreConnectionState, null);
            this.changeProperty(ftthConnectionState, null);
            this.changeProperty(emitedLaserPower, null);
            this.changeProperty(receivedLaserPower, null);
            this.changeProperty(inputBitrate, null); 
            this.changeProperty(outputBitrate, null); 
        }        
      
		
	}

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
     */
	 //TODO "Complicarlo" más de momento es muy sencillo, en el futuro usar las caracteristicas reales de un OLT
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		Integer temp = (Integer) this.getProperty(DeviceDefinitions.TEMPERATURE_PROPERTY);
        Integer inBitrate = (Integer) this.getProperty(inputBitrate);
        Integer outBitrate = (Integer) this.getProperty(outputBitrate);
        Integer emitedLaser = (Integer) this.getProperty(emitedLaserPower);
        Integer receivedLaser = (Integer) this.getProperty(receivedLaserPower);
		if (temp<70 || inBitrate >= 1000000000 || outBitrate >= 1000000000 
				|| emitedLaser > 45 || receivedLaser > 45) {
            this.updateStatusTo(DeviceDefinitions.OK_STATUS);
        }else if(temp>70 || inBitrate < 1000000000 || outBitrate < 1000000000 
				|| emitedLaser < 45 || receivedLaser < 45 ){
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
        this.addProperty(coreConnectionState, DeviceDefinitions.OK_STATUS);
        this.addProperty(ftthConnectionState, DeviceDefinitions.OK_STATUS);
        this.addProperty(emitedLaserPower, 50);
        this.addProperty(receivedLaserPower, 50);
        this.addProperty(inputBitrate, 1000000000);  //1Gbps
        this.addProperty(outputBitrate, 1000000000); //1Gbps
		
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
