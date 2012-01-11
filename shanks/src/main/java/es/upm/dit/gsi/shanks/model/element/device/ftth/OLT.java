package es.upm.dit.gsi.shanks.model.element.device.ftth;

import es.upm.dit.gsi.shanks.model.element.device.Device;

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

    /**
	 * 
	 */
    private static final long serialVersionUID = -6004213563737699458L;

    public OLT(String id, String status) {
        super(id, status);
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
