package es.upm.dit.gsi.shanks.model.element.link;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * Link class
 * 
 * This class represents the links than connect the different devices
 * 
 * @author Daniel Lara
 * @author a.carrera
 * @version 0.2
 * 
 */

public abstract class Link extends NetworkElement {
    
    private Logger logger = Logger.getLogger(Link.class.getName());
    
    private List<Device> linkedDevices;
    private int deviceCapacity;

    /**
     * @param id
     * @param capacity
     * @throws UnsupportedNetworkElementStatusException 
     */
    public Link(String id, String initialState, int capacity) throws UnsupportedNetworkElementStatusException {
        super(id, initialState);
        this.deviceCapacity = capacity;
        this.linkedDevices = new ArrayList<Device>();
    }

    /**
     * @return
     */
    public List<Device> getLinkedDevices() {
        return linkedDevices;
    }

    /**
     * @param device
     * @return
     * @throws TooManyConnectionException 
     */
    public void connectDevice(Device device) throws TooManyConnectionException {
        if (this.linkedDevices.size()<deviceCapacity) {
            if (!this.linkedDevices.contains(device)) {
                this.linkedDevices.add(device);
                device.connectToLink(this);
                logger.fine("Link " + this.getID() + " has Device " + device.getID() + " in its linked device list.");
            } else {
                logger.fine("Link " + this.getID() + " already has Device " + device.getID() + " in its linked device list.");
            }
        } else {
            if (!this.linkedDevices.contains(device)) {
                logger.fine("Link " + this.getID() + " is full of its capacity. Device " + device.getID() + " was not included in its linked device list.");
                throw new TooManyConnectionException(this);   
            } else {
                logger.fine("Link " + this.getID() + " already has Device " + device.getID() + " in its linked device list.");
            }
        }
    }
    
    /**
     * @param device
     */
    public void disconnectDevice(Device device) {
        this.linkedDevices.remove(device);
        device.disconnectFromLink(this);
    }
    
    /**
     * Connect both devices to the link
     * 
     * @param device1
     * @param device2
     * @throws TooManyConnectionException 
     */
    public void connectDevices(Device device1, Device device2) throws TooManyConnectionException {
        this.connectDevice(device1);
        try {
            this.connectDevice(device2);
        } catch (TooManyConnectionException e) {
            this.disconnectDevice(device1);
            throw e;
        }
    }
    
}
