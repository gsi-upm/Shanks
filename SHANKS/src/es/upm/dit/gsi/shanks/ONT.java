package es.upm.dit.gsi.shanks;


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

	public ONT(String id, int status, int temperature, int type) {
		super(id, status, temperature, type);
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
	
    
    
}
