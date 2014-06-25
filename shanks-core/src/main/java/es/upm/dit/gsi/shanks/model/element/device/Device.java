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
/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.model.element.device;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.link.Link;

/**
 * Device class
 * 
 * This is used to represent a common Device
 * 
 * @author Daniel Lara
 * @author a.carrera
 * @version 0.2
 * 
 */

public abstract class Device extends NetworkElement {

    private List<Link> linksList;
    private boolean isGateway;

    private Logger logger;

    /**
     * 
     * @param id
     *            the identifier of the device as network element of the
     *            simulation.
     * 
     * @param isGateway
     *            defines if the current devices is a gateway or not. A gateway
     *            device (an only gateway devices) can be connected with
     *            multiple scenarios.
     * 
     * @param initialState
     *            the state in which the device has to be initialized.
     */
    public Device(String id, String initialState, boolean isGateway,
            Logger logger) {
        super(id, initialState, logger);
        this.logger = logger;
        this.isGateway = isGateway;
        this.linksList = new ArrayList<Link>();
    }

    /**
     * Return the connections between the device
     * 
     * @return linkList A list with the different connections
     */
    public List<Link> getLinks() {
        return linksList;
    }

    /**
     * Connect the device to a link
     * 
     * @param link
     * @throws TooManyConnectionException
     */
    public void connectToLink(Link link) throws ShanksException {
        if (!this.linksList.contains(link)) {
            this.linksList.add(link);
            logger.finer("Device " + this.getID()
                    + " is now connected to Link " + link.getID());
            link.connectDevice(this);
        }
    }

    /**
     * Disconnect the device From a link
     * 
     * @param link
     */
    public void disconnectFromLink(Link link) {
        boolean disconnected = this.linksList.remove(link);
        if (disconnected) {
            link.disconnectDevice(this);
            logger.fine("Device " + this.getID()
                    + " is now disconnected from Link " + link.getID());
        } else {
            logger.warning("Device " + this.getID()
                    + " could not be disconnected from Link " + link.getID()
                    + ", because it was not connected.");
        }
    }

    /**
     * Connect the device to other device with a link
     * 
     * @param device
     * @param link
     * @throws TooManyConnectionException
     */
    public void connectToDeviceWithLink(Device device, Link link)
            throws ShanksException {
        this.connectToLink(link);
        try {
            device.connectToLink(link);
        } catch (ShanksException e) {
            this.disconnectFromLink(link);
            throw e;
        }
    }

    /**
     * @return the isGateway
     */
    public boolean isGateway() {
        return isGateway;
    }
}
