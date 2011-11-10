package es.upm.dit.gsi.shanks;

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
    
	public OLT(String id, int status, int temperature, int type) {
		super(id, status, temperature, type);
	}
	
	private int coreConnectionState;
    private int ftthConnectionState;
    private int emitedLaserPower;
    private int receivedLaserPower;
    private int inputBitrate;
    private int outputBitrate;
    
    
    
	public int getCoreConnectionState() {
		return coreConnectionState;
	}
	
	public void setCoreConnectionState(int coreConnectionState) {
		this.coreConnectionState = coreConnectionState;
	}
	
	public int getFtthConnectionState() {
		return ftthConnectionState;
	}
	
	public void setFtthConnectionState(int ftthConnectionState) {
		this.ftthConnectionState = ftthConnectionState;
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
