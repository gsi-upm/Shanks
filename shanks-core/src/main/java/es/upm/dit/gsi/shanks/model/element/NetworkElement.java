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
/** es.upm.dit.gsi.shanks
 11/01/2012

 */
package es.upm.dit.gsi.shanks.model.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;

/**
 * @author a.carrera
 * 
 */
public abstract class NetworkElement {

    private String id;
    private HashMap<String, Boolean> states;
    private HashMap<String, Object> properties;
    private HashMap<String, Object> universal; // TODO rename this variable to
                                              // global. Universal seems like
                                              // whole simulation state.
//    private List<String> possiblesStates;
    
    private Logger logger; 

    /**
     * @param id
     * @param initialStatus
     * @param logger
     */
    public NetworkElement(String id, String initialStatus, Logger logger){
        this.id = id;
        this.logger = logger;
        this.properties = new HashMap<String, Object>();
        this.states = new HashMap<String, Boolean>();

        this.setPossibleStates();
        this.states.put(initialStatus, true);
        this.fillIntialProperties();
    }

    /**
     * @return the id
     */
    public String getID() {
        return id;
    }

    /**
     * @param id
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * @return the currentStatus
     */
    public HashMap<String, Boolean> getStatus() {
        return states;
    }

    /**
     * @param desiredStatus
     * @return
     * @throws ShanksException
     */
    public boolean setCurrentStatus(String desiredStatus, boolean state)
            throws ShanksException {
        List<String> states = new ArrayList<String>();
        states.add(desiredStatus);
        return this.setCurrentStatus(states, state);
    }

    /**
     * @param desiredStatus
     *            the currentStatus to set
     * @return true if the status was set correctly and false if the status is
     *         not a possible status of the network element
     * @throws UnsupportedNetworkElementFieldException
     */
    public boolean setCurrentStatus(List<String> desiredStatus, boolean state)
            throws ShanksException {
        for (String s : desiredStatus) {
            if (states.containsKey(s)) {
                states.put(s, state);
                this.checkProperties();
            } else {
                throw new UnsupportedNetworkElementFieldException(this, s);
            }
        }
        return true;
    }

    /**
     * @param desiredStatus
     * @param state
     * @return
     * @throws UnsupportedNetworkElementFieldException
     */
    public boolean updateStatusTo(String desiredStatus, boolean state)
            throws ShanksException {
        List<String> states = new ArrayList<String>();
        states.add(desiredStatus);
        return this.updateStatusTo(states, state);
    }

    /**
     * @param desiredStatus
     *            the currentStatus to set
     * @return true if the status was set correctly and false if the status is
     *         not a possible status of the network element
     * @throws UnsupportedNetworkElementFieldException
     */
    public boolean updateStatusTo(List<String> desiredStatus, boolean value)
            throws ShanksException {
        boolean allStatusUpdated = false;
        for (String status : desiredStatus) {
            if (this.isPossibleStatus(status)) {
                states.put(status, value);
                logger.fine("Network Element Status updated -> ElementID: "
                        + this.getID() + " Current Status: " + status);
            } else {
                logger.warning("Impossible to update status: " + status
                        + ". This network element " + this.getID()
                        + "does not support this status.");
                throw new UnsupportedNetworkElementFieldException(this, status);
            }
        }
        allStatusUpdated = true;
        return allStatusUpdated;
    }

    /**
     * @param desiredProperty
     * @param value
     * @return
     * @throws UnsupportedNetworkElementFieldException
     */
    public boolean updatePropertyTo(String desiredProperty, Object value)
            throws ShanksException {
        List<String> properties = new ArrayList<String>();
        properties.add(desiredProperty);
        return this.updatePropertyTo(properties, value);
    }

    /**
     * @param desiredProperty
     * @param value
     * @return
     * @throws UnsupportedNetworkElementFieldException
     */
    public boolean updatePropertyTo(List<String> desiredProperty, Object value)
            throws ShanksException {
        boolean allPropertiesUpdated = false;
        for (String property : desiredProperty) {
            if (this.properties.containsKey(property)) {
                properties.put(property, value);
                logger.fine("Network Element Property updated -> ElementID: "
                        + this.getID() + " Property : " + property
                        + " changed to : " + value);
            } else {
                logger.warning("Impossible to change property: " + property
                        + ". This network element " + this.getID()
                        + "haven't got this property.");
                // TODO make Unssoported... PropertyException
                throw new UnsupportedNetworkElementFieldException(this,
                        property);
            }
        }
        allPropertiesUpdated = true;
        return allPropertiesUpdated;
    }

    /**
     * Set the initial properties of the network element
     */
    abstract public void fillIntialProperties();

    /**
     * This method check properties and change them depending on the current
     * status
     * 
     * @throws UnsupportedNetworkElementFieldException
     */
    abstract public void checkProperties() throws ShanksException;

    /**
     * @param possibleStatus
     * @return
     */
    private boolean isPossibleStatus(String possibleStatus) {
        if (this.states.containsKey(possibleStatus)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the properties
     */
    public HashMap<String, Object> getProperties() {
        return properties;
    }

    /**
     * @param properties
     *            the properties to set
     */
    public void setProperties(HashMap<String, Object> properties) {
        this.properties = properties;
    }

    /**
     * @param propertyName
     * @param propertyValue
     */
    public void addProperty(String propertyName, Object propertyValue) {
        this.properties.put(propertyName, propertyValue);
    }

    /**
     * If the property exists, this method changes it. If not, this method
     * creates it.
     * 
     * @param propertyName
     * @param propertyValue
     * @throws UnsupportedNetworkElementFieldException
     */
    public void changeProperty(String propertyName, Object propertyValue)
            throws ShanksException {
        this.properties.put(propertyName, propertyValue);
        this.checkStatus();
    }

    /**
     * This method check status and change them depending on the current
     * properties This method must use updateStatusTo(String desiredStatus)
     * method to change element status. WARNING: Do not use
     * setCurrentStatus(String desiredStatus) to avoid an infinitive loop
     * 
     * @throws UnsupportedNetworkElementFieldException
     */
    abstract public void checkStatus() throws ShanksException;

    /**
     * @param propertyName
     * @return property value
     */
    public Object getProperty(String propertyName) {
        return this.properties.get(propertyName);
    }

    /**
     * This method only returns a copy of the list, if you modify the copy, the
     * internal list of PossibleStates will be not modified. To interact with
     * the real list, use "addPossibleStatus" and "removePossibleStatus" methods
     * 
     * @return a copy of possibleStates list
     */
    public List<String> getPossibleStates() {
        List<String> copy = new ArrayList<String>();
        for (String possibleStatus : states.keySet()) {
            copy.add(possibleStatus);
        }
        return copy;
    }

    /**
     *
     */
    abstract public void setPossibleStates();

    /**
     * Add the possibles states of the device
     * 
     * @param possibleStatus
     */
    public void addPossibleStatus(String possibleStatus) {
        this.states.put(possibleStatus, false);
    }

    /**
     * @param possibleStatus
     */
    public void removePossibleStatus(String possibleStatus) {
        this.states.remove(possibleStatus);
    }

    public void setUniversalSituation() {
        universal.put("PROPERTIES", properties);
        universal.put("STATUS", states);
    }

}
