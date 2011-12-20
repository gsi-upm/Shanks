package es.upm.dit.gsi.shanks.model.scenario;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import sim.portrayal3d.SimplePortrayal3D;
import es.upm.dit.gsi.shanks.model.common.Definitions;
import es.upm.dit.gsi.shanks.model.scenario.ftth.device.Link;

/**
 * Device class
 * 
 * This is used for create a common Device
 * 
 * @author Daniel Lara
 * @version 0.1
 * 
 */

public abstract class Device extends SimplePortrayal3D {

    Logger logger = Logger.getLogger(Device.class.getName());

    private static final long serialVersionUID = 5533704302816153428L;

    private String id;
    private String type; // The type of the device
    private Enumeration<String> possibleStatus;
    private String status;
    private List<Link> linkList; // The different devices that are connected
    private HashMap<String, String> properties;

    public Device(String id, String type) {
        this.id = id;
        this.type = type;
        this.properties = new HashMap<String, String>();
    }

    /**
     * Return the status of the device
     * 
     * @return status The state of the device
     */
    public String getStatus() {
        return status;
    }

    /**
     * Return the id of the device
     * 
     * @return id The id of the device
     */
    public String getID() {
        return id;
    }

    /**
     * Return the connections between the device
     * 
     * @return linkList A list with the different connections
     */
    public List<Link> getLinks() {
        return linkList;
    }

    /**
     * Change the status of the device
     * 
     * @param status
     *            The new state of the device
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Return the type of the device
     * 
     * @see Definitions
     * @return type The type of the device
     */
    public String getType() {
        return type;
    }

    /**
     * @return the possibleStatus
     */
    public Enumeration<String> getPossibleStatus() {
        return possibleStatus;
    }

    /**
     * @param possibleStatus
     *            the possibleStatus to set
     */
    public void setPossibleStatus(Enumeration<String> possibleStatus) {
        this.possibleStatus = possibleStatus;
    }

    /**
     * @return the properties
     */
    public HashMap<String, String> getProperties() {
        return properties;
    }

    /**
     * @param property
     *            the property to add
     */
    public void addProperty(String propertyType, String propertyValue) {
        this.properties.put(propertyType, propertyValue);
    }

    /**
     * @param property
     *            the property to remove
     */
    public void addProperty(String propertyType) {
        this.properties.remove(propertyType);
    }

}
