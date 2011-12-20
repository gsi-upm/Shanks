package es.upm.dit.gsi.shanks.model.device.ftth;

import es.upm.dit.gsi.shanks.model.device.Device;

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

    public Splitter(String id, int status, int temperature, int type) {
        super(id, status, temperature, type);
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

}
