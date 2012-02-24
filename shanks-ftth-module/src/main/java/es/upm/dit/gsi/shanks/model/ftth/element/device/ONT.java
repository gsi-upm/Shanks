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

    public static final String OK_STATUS = "OK";
    public static final String NOK_STATUS = "NOK";
    public static final String UNKNOWN_STATUS = "UNKNOWN";
    public static final String HIGHTEMP_STATUS = "HIGHTEMP";
    public static final String LOW_INPUT_BITRATE_STATUS = "LOW INPUT BITRATE";
    public static final String LOW_OUTPUT_BITRATE_STATUS = "LOW OUTPUT BITRATE";
    public static final String LOW_REC_LASER_POWER = "LOW RECEIVED LASER POWER";
    public static final String LOW_EMI_LASER_POWER = "LOW EMITED LASER POWER";
    public static final String LOST_GATEWAY_CONNECTION_STATUS = "LOST GATEWAY CONNECTION";
    public static final String LOST_FIBER_CONNECTION_STATUS = "LOST FIBER CONNECTION";
    
    public static final String gatewayConnectionState = "gatewayConnectionState";
    public static final String fiberConnectionState = "fiberConnectionState";
    public static final String emitedLaserPower = "emitedLaserPower";
    public static final String receivedLaserPower = "receivedLaserPower";
    public static final String inputBitrate = "inputBitrate";
    public static final String outputBitrate = "outputBitrate";
    public static final String OS_PROPERTY = "OS";
    public static final String TEMPERATURE_PROPERTY = "Temperature";



    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
     */
    public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
		String status = this.getCurrentStatus();
        if (status.equals(DeviceDefinitions.OK_STATUS)) {
        	this.changeProperty(TEMPERATURE_PROPERTY, 30);
        	this.changeProperty(gatewayConnectionState, true);
        	this.changeProperty(fiberConnectionState, true);
        	this.changeProperty(emitedLaserPower, 50);
        	this.changeProperty(receivedLaserPower, 50);
        	this.changeProperty(inputBitrate, 100000000); //100Mbps 
        	this.changeProperty(outputBitrate, 100000000); //100Mbps 
        	
        } else if (status.equals(NOK_STATUS)) {
        	this.changeProperty(TEMPERATURE_PROPERTY, 90);
            this.changeProperty(gatewayConnectionState, false);
            this.changeProperty(fiberConnectionState, false);
            this.changeProperty(emitedLaserPower, 10);
            this.changeProperty(receivedLaserPower, 10);
            this.changeProperty(inputBitrate, 1000000); //1Mbps
            this.changeProperty(outputBitrate, 1000000); //1Mbps
       
        } else if (status.equals(UNKNOWN_STATUS)) {
        	this.changeProperty(TEMPERATURE_PROPERTY, null);
            this.changeProperty(gatewayConnectionState, null);
            this.changeProperty(fiberConnectionState, null);
            this.changeProperty(emitedLaserPower, null);
            this.changeProperty(receivedLaserPower, null);
            this.changeProperty(inputBitrate, null); 
            this.changeProperty(outputBitrate, null); 
           
        } else if (status.equals(HIGHTEMP_STATUS)){
        	this.changeProperty(TEMPERATURE_PROPERTY, 90);
//            this.changeProperty(gatewayConnectionState, true);
//            this.changeProperty(fiberConnectionState, true);
//            this.changeProperty(emitedLaserPower, 50);
//            this.changeProperty(receivedLaserPower, 50);
//            this.changeProperty(inputBitrate, 100000000); //100Mbps
//            this.changeProperty(outputBitrate, 100000000); //100Mbps
        	
        } else if (status.equals(LOST_FIBER_CONNECTION_STATUS)){
//        	this.changeProperty(TEMPERATURE_PROPERTY, 30);
//            this.changeProperty(gatewayConnectionState, true);
            this.changeProperty(fiberConnectionState, false);
//            this.changeProperty(emitedLaserPower, 50);
//            this.changeProperty(receivedLaserPower, 50);
//            this.changeProperty(inputBitrate, 100000000); //100Mbps
//            this.changeProperty(outputBitrate, 100000000); //100Mbps
        	
        } else if (status.equals(LOST_GATEWAY_CONNECTION_STATUS)){
//        	this.changeProperty(TEMPERATURE_PROPERTY, 30);
            this.changeProperty(gatewayConnectionState, false);
//            this.changeProperty(fiberConnectionState, true);
//            this.changeProperty(emitedLaserPower, 50);
//            this.changeProperty(receivedLaserPower, 50);
//            this.changeProperty(inputBitrate, 100000000); //100Mbps
//            this.changeProperty(outputBitrate, 100000000); //100Mbps
        	
        } else if (status.equals(LOW_EMI_LASER_POWER)){
//        	this.changeProperty(TEMPERATURE_PROPERTY, 30);
//            this.changeProperty(gatewayConnectionState, true);
//            this.changeProperty(fiberConnectionState, true);
            this.changeProperty(emitedLaserPower, 10);
//            this.changeProperty(receivedLaserPower, 50);
//            this.changeProperty(inputBitrate, 100000000); //100Mbps
//            this.changeProperty(outputBitrate, 100000000); //100Mbps
        	
        } else if (status.equals(LOW_REC_LASER_POWER)){
//        	this.changeProperty(TEMPERATURE_PROPERTY, 30);
//            this.changeProperty(gatewayConnectionState, true);
//            this.changeProperty(fiberConnectionState, true);
//            this.changeProperty(emitedLaserPower, 50);
            this.changeProperty(receivedLaserPower, 10);
//            this.changeProperty(inputBitrate, 100000000); //100Mbps
//            this.changeProperty(outputBitrate, 100000000); //100Mbps
        	
        } else if (status.equals(LOW_INPUT_BITRATE_STATUS)){
//        	this.changeProperty(TEMPERATURE_PROPERTY, 30);
//            this.changeProperty(gatewayConnectionState, true);
//            this.changeProperty(fiberConnectionState, true);
//            this.changeProperty(emitedLaserPower, 50);
//            this.changeProperty(receivedLaserPower, 50);
            this.changeProperty(inputBitrate, 1000000); //1Mbps
//            this.changeProperty(outputBitrate, 100000000); //100Mbps
        	
        } else if (status.equals(LOW_OUTPUT_BITRATE_STATUS)){
//        	this.changeProperty(TEMPERATURE_PROPERTY, 30);
//            this.changeProperty(gatewayConnectionState, true);
//            this.changeProperty(fiberConnectionState, true);
//            this.changeProperty(emitedLaserPower, 50);
//            this.changeProperty(receivedLaserPower, 50);
//            this.changeProperty(inputBitrate, 100000000); //100Mbps 
            this.changeProperty(outputBitrate, 1000000); //1Mbps
        	
        }
	}

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
     */
    public void checkStatus() throws UnsupportedNetworkElementStatusException {
    	
		Integer temp = (Integer) this.getProperty(DeviceDefinitions.TEMPERATURE_PROPERTY);
		Integer inBitrate = (Integer) this.getProperty(inputBitrate);
        Integer outBitrate = (Integer) this.getProperty(outputBitrate);
        Integer emited = (Integer) this.getProperty(emitedLaserPower);
        Integer received = (Integer) this.getProperty(receivedLaserPower);
        boolean gateway = (Boolean) this.getProperty(gatewayConnectionState);
		boolean fiber = (Boolean) this.getProperty(fiberConnectionState);
		
        if (temp < 70 && inBitrate >= 75000000 && outBitrate >= 75000000
				&& emited >= 40 && received >= 40 && gateway && fiber){
        	this.updateStatusTo(OK_STATUS);	
        	
		}else if(temp > 70 && inBitrate < 75000000 && outBitrate < 75000000
				&& emited < 40 && received < 40 && !gateway && !fiber){
        	this.updateStatusTo(NOK_STATUS);	
        	
		}else if(temp < 70 && inBitrate >= 75000000 && outBitrate >= 75000000
				&& emited < 40 && received >= 40 && gateway && fiber){
        	this.updateStatusTo(LOW_EMI_LASER_POWER);	
        	
		}else if(temp < 70 && inBitrate >= 75000000 && outBitrate >= 75000000
				&& emited >= 40 && received < 40 && gateway && fiber){
        	this.updateStatusTo(LOW_REC_LASER_POWER);	
        	
		}else if(temp < 70 && inBitrate < 75000000 && outBitrate >= 75000000
				&& emited >= 40 && received >= 40 && gateway && fiber){
        	this.updateStatusTo(LOW_INPUT_BITRATE_STATUS);	
        	
		}else if(temp < 70 && inBitrate >= 75000000 && outBitrate < 75000000
				&& emited >= 40 && received >= 40 && gateway && fiber){
        	this.updateStatusTo(LOW_OUTPUT_BITRATE_STATUS);	
        	
		}else if(temp < 70 && inBitrate >= 75000000 && outBitrate >= 75000000
				&& emited >= 40 && received >= 40 && !gateway && fiber){
        	this.updateStatusTo(LOST_GATEWAY_CONNECTION_STATUS);
        	
		}else if(temp < 70 && inBitrate >= 75000000 && outBitrate >= 75000000
				&& emited >= 40 && received >= 40 && gateway && !fiber){
        	this.updateStatusTo(LOST_GATEWAY_CONNECTION_STATUS);
        	
		}else if(temp > 70 && inBitrate >= 75000000 && outBitrate >= 75000000
    			&& emited >= 40 && received >= 40 && gateway && fiber){
            this.updateStatusTo(HIGHTEMP_STATUS);	
            
		}else{
			this.updateStatusTo(UNKNOWN_STATUS);
		}

	}

	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillInitialProperties()
     */
	public void fillIntialProperties() {
		this.addProperty(OS_PROPERTY, "Linux");
        this.addProperty(TEMPERATURE_PROPERTY, 20);
        this.addProperty(gatewayConnectionState, true);
        this.addProperty(fiberConnectionState, true);
        this.addProperty(inputBitrate, 100000000); //100Mbps
        this.addProperty(outputBitrate, 100000000); //100Mbps
        this.addProperty(receivedLaserPower, 50);
        this.addProperty(emitedLaserPower, 50);
		
	}

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
     */
	public void setPossibleStates() {
		this.addPossibleStatus(OK_STATUS);
        this.addPossibleStatus(NOK_STATUS);
        this.addPossibleStatus(UNKNOWN_STATUS);
        this.addPossibleStatus(LOST_FIBER_CONNECTION_STATUS);
        this.addPossibleStatus(LOST_GATEWAY_CONNECTION_STATUS);
        this.addPossibleStatus(LOW_EMI_LASER_POWER);
        this.addPossibleStatus(LOW_REC_LASER_POWER);
        this.addPossibleStatus(LOW_INPUT_BITRATE_STATUS);
        this.addPossibleStatus(LOW_OUTPUT_BITRATE_STATUS);
        this.addPossibleStatus(HIGHTEMP_STATUS);
		
	}

}
