/*******************************************************************************
 * Copyright  (C) 2014 Álvaro Carrera Barroso
 * Grupo de Sistemas Inteligentes - Universidad Politecnica de Madrid
 *  
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package es.upm.dit.gsi.shanks.model.element.link;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;

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

    private List<Device> linkedDevices;
    private int deviceCapacity;

    private Logger logger;

    /**
     * @param id
     * @param initialState
     * @param capacity
     * @param logger
     * @throws ShanksException
     */
    public Link(String id, String initialState, int capacity, Logger logger)
            throws ShanksException {
        super(id, initialState, logger);
        this.logger = logger;
        this.deviceCapacity = capacity;
        this.linkedDevices = new ArrayList<Device>();
    }

    /**
     * @return List of connected devices to the link
     */
    public List<Device> getLinkedDevices() {
        return linkedDevices;
    }

    /**
     * Get the capacity of the link (max number of connected device)
     * 
     * @return the capacity
     */
    public int getCapacity() {
        return deviceCapacity;
    }

    /**
     * Connect a device to the link
     * 
     * @param device
     * @throws TooManyConnectionException
     */
    public void connectDevice(Device device) throws ShanksException {
        if (this.linkedDevices.size() < deviceCapacity) {
            if (!this.linkedDevices.contains(device)) {
                this.linkedDevices.add(device);
                device.connectToLink(this);
                logger.finer("Link " + this.getID() + " has Device "
                        + device.getID() + " in its linked device list.");
            } else {
                logger.finer("Link " + this.getID() + " already has Device "
                        + device.getID() + " in its linked device list.");
            }
        } else {
            if (!this.linkedDevices.contains(device)) {
                logger.warning("Link " + this.getID()
                        + " is full of its capacity. Device " + device.getID()
                        + " was not included in its linked device list.");
                throw new TooManyConnectionException(this);
            } else {
                logger.finer("Link " + this.getID() + " already has Device "
                        + device.getID() + " in its linked device list.");
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
    public void connectDevices(Device device1, Device device2)
            throws ShanksException {
        this.connectDevice(device1);
        try {
            this.connectDevice(device2);
        } catch (ShanksException e) {
            this.disconnectDevice(device1);
            throw e;
        }
    }

}
