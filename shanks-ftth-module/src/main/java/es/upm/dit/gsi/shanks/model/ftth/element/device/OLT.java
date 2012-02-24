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
    
    public static final String OK_STATUS = "OK";
    public static final String NOK_STATUS = "NOK";
    public static final String UNKNOWN_STATUS = "UNKNOWN";
    public static final String HIGHTEMP_STATUS = "HIGHTEMP";
    public static final String LOW_INPUT_BITRATE_STATUS = "LOW INPUT BITRATE";
    public static final String LOW_OUTPUT_BITRATE_STATUS = "LOW OUTPUT BITRATE";
    public static final String LOW_REC_LASER_POWER = "LOW RECEIVED LASER POWER";
    public static final String LOW_EMI_LASER_POWER = "LOW EMITED LASER POWER";
    public static final String LOST_CORE_CONNECTION_STATUS = "LOST CORE CONNECTION";
    public static final String LOST_FTTH_CONNECTION_STATUS = "LOST FTTH CONNECTION";

    public static final String coreConnectionState = "coreConnectionState";
    public static final String ftthConnectionState = "ftthConnectionState";
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
        	this.changeProperty(coreConnectionState, true);
        	this.changeProperty(ftthConnectionState, true);
        	this.changeProperty(emitedLaserPower, 50);
        	this.changeProperty(receivedLaserPower, 50);
        	this.changeProperty(inputBitrate, 1000000000); //1Gbps 
        	this.changeProperty(outputBitrate, 1000000000); //1Gbps 
        	
        } else if (status.equals(NOK_STATUS)) {
        	this.changeProperty(TEMPERATURE_PROPERTY, 90);
            this.changeProperty(coreConnectionState, false);
            this.changeProperty(ftthConnectionState, false);
            this.changeProperty(emitedLaserPower, 10);
            this.changeProperty(receivedLaserPower, 10);
            this.changeProperty(inputBitrate, 10000000); //10Mbps
            this.changeProperty(outputBitrate, 1000000); //1Mbps
       
        } else if (status.equals(UNKNOWN_STATUS)) {
        	this.changeProperty(TEMPERATURE_PROPERTY, null);
            this.changeProperty(coreConnectionState, null);
            this.changeProperty(ftthConnectionState, null);
            this.changeProperty(emitedLaserPower, null);
            this.changeProperty(receivedLaserPower, null);
            this.changeProperty(inputBitrate, null); 
            this.changeProperty(outputBitrate, null); 
           
        } else if (status.equals(HIGHTEMP_STATUS)){
        	this.changeProperty(TEMPERATURE_PROPERTY, 90);
//            this.changeProperty(coreConnectionState, true);
//            this.changeProperty(ftthConnectionState, true);
//            this.changeProperty(emitedLaserPower, 50);
//            this.changeProperty(receivedLaserPower, 50);
//            this.changeProperty(inputBitrate, 1000000000); //1Gbps
//            this.changeProperty(outputBitrate, 1000000000); //1Gbps
        	
        } else if (status.equals(LOST_FTTH_CONNECTION_STATUS)){
//        	this.changeProperty(TEMPERATURE_PROPERTY, 30);
//            this.changeProperty(coreConnectionState, true);
            this.changeProperty(ftthConnectionState, false);
//            this.changeProperty(emitedLaserPower, 50);
//            this.changeProperty(receivedLaserPower, 50);
//            this.changeProperty(inputBitrate, 1000000000); //1Gbps
//            this.changeProperty(outputBitrate, 1000000000); //1Gbps
        	
        } else if (status.equals(LOST_CORE_CONNECTION_STATUS)){
//        	this.changeProperty(TEMPERATURE_PROPERTY, 30);
            this.changeProperty(coreConnectionState, false);
//            this.changeProperty(ftthConnectionState, true);
//            this.changeProperty(emitedLaserPower, 50);
//            this.changeProperty(receivedLaserPower, 50);
//            this.changeProperty(inputBitrate, 1000000000); //1Gbps
//            this.changeProperty(outputBitrate, 1000000000); //1Gbps
        	
        } else if (status.equals(LOW_EMI_LASER_POWER)){
//        	this.changeProperty(TEMPERATURE_PROPERTY, 30);
//            this.changeProperty(coreConnectionState, true);
//            this.changeProperty(ftthConnectionState, true);
            this.changeProperty(emitedLaserPower, 10);
//            this.changeProperty(receivedLaserPower, 50);
//            this.changeProperty(inputBitrate, 1000000000); //1Gbps
//            this.changeProperty(outputBitrate, 1000000000); //1Gbps
        	
        } else if (status.equals(LOW_REC_LASER_POWER)){
//        	this.changeProperty(TEMPERATURE_PROPERTY, 30);
//            this.changeProperty(coreConnectionState, true);
//            this.changeProperty(ftthConnectionState, true);
//            this.changeProperty(emitedLaserPower, 50);
            this.changeProperty(receivedLaserPower, 10);
//            this.changeProperty(inputBitrate, 1000000000); //1Gbps
//            this.changeProperty(outputBitrate, 1000000000); //1Gbps
        	
        } else if (status.equals(LOW_INPUT_BITRATE_STATUS)){
//        	this.changeProperty(TEMPERATURE_PROPERTY, 30);
//            this.changeProperty(coreConnectionState, true);
//            this.changeProperty(ftthConnectionState, true);
//            this.changeProperty(emitedLaserPower, 50);
//            this.changeProperty(receivedLaserPower, 50);
            this.changeProperty(inputBitrate, 1000000); //1Mbps
//            this.changeProperty(outputBitrate, 1000000000); //1Gbps
        	
        } else if (status.equals(LOW_OUTPUT_BITRATE_STATUS)){
//        	this.changeProperty(TEMPERATURE_PROPERTY, 30);
//            this.changeProperty(coreConnectionState, true);
//            this.changeProperty(ftthConnectionState, true);
//            this.changeProperty(emitedLaserPower, 50);
//            this.changeProperty(receivedLaserPower, 50);
//            this.changeProperty(inputBitrate, 1000000000); //1Gbps 
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
        boolean core = (Boolean) this.getProperty(coreConnectionState);
		boolean ftth = (Boolean) this.getProperty(ftthConnectionState);
		
		if (temp < 70 && inBitrate >= 750000000 && outBitrate >= 750000000
				&& emited >= 40 && received >= 40 && core && ftth){
        	this.updateStatusTo(OK_STATUS);	
        	
		}else if(temp > 70 && inBitrate < 750000000 && outBitrate < 750000000
				&& emited < 40 && received < 40 && !core && !ftth){
        	this.updateStatusTo(NOK_STATUS);	
        	
		}else if(emited < 40){
        	this.updateStatusTo(LOW_EMI_LASER_POWER);	
        	
		}else if(received < 40){
        	this.updateStatusTo(LOW_REC_LASER_POWER);	
        	
		}else if(inBitrate < 750000000){
        	this.updateStatusTo(LOW_INPUT_BITRATE_STATUS);	
        	
		}else if(outBitrate < 750000000){
        	this.updateStatusTo(LOW_OUTPUT_BITRATE_STATUS);	
        	
		}else if(!core){
        	this.updateStatusTo(LOST_CORE_CONNECTION_STATUS);
        	
		}else if(ftth){
        	this.updateStatusTo(LOST_FTTH_CONNECTION_STATUS);
        	
		}else if(temp > 70){
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
        this.addProperty(coreConnectionState, true);
        this.addProperty(ftthConnectionState, true);
        this.addProperty(emitedLaserPower, 50);
        this.addProperty(receivedLaserPower, 50);
        this.addProperty(inputBitrate, 1000000000);  //1Gbps
        this.addProperty(outputBitrate, 1000000000); //1Gbps
		
	}
	
	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
     */
	public void setPossibleStates() {
		this.addPossibleStatus(OK_STATUS);
        this.addPossibleStatus(NOK_STATUS);
        this.addPossibleStatus(UNKNOWN_STATUS);
        this.addPossibleStatus(LOST_FTTH_CONNECTION_STATUS);
        this.addPossibleStatus(LOST_CORE_CONNECTION_STATUS);
        this.addPossibleStatus(LOW_EMI_LASER_POWER);
        this.addPossibleStatus(LOW_REC_LASER_POWER);
        this.addPossibleStatus(LOW_INPUT_BITRATE_STATUS);
        this.addPossibleStatus(LOW_OUTPUT_BITRATE_STATUS);
        this.addPossibleStatus(HIGHTEMP_STATUS);
		
	}

}
