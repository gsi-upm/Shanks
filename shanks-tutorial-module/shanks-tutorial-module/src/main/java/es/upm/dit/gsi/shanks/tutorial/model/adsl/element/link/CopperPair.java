/**
 * 
 */
package es.upm.dit.gsi.shanks.tutorial.model.adsl.element.link;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;

/**
 * @author a.carrera
 *
 */
public class CopperPair extends Link {

    public static final String STATUS_OK = "OK";
    public static final String STATUS_BROKEN = "BROKEN";
    
    public static final String DISTANCE_PROPERTY = "Distance";
    public static final String LINK_TYPE_PROPERTY = "LinkType";
    public static final String PROPERTY_RELIABILITY = "Reability";
    
    
    /**
     * @param id
     * @param initialState
     * @param capacity
     * @throws UnsupportedNetworkElementStatusException
     */
    public CopperPair(String id) throws UnsupportedNetworkElementStatusException {
        super(id, STATUS_OK, 2);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
     */
    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(CopperPair.STATUS_OK);
        this.addPossibleStatus(CopperPair.STATUS_BROKEN);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
     */
    @Override
    public void checkProperties() throws UnsupportedNetworkElementStatusException {
        String status = this.getCurrentStatus();
        if (status.equals(CopperPair.STATUS_BROKEN)) {
            this.addProperty(CopperPair.PROPERTY_RELIABILITY, "BROKEN");
        }
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
     */
    @Override
    public void fillIntialProperties() {
        this.addProperty(CopperPair.LINK_TYPE_PROPERTY, "Ethernet");
        this.addProperty(CopperPair.DISTANCE_PROPERTY, 3.5);
        this.addProperty(CopperPair.PROPERTY_RELIABILITY, "OK");
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
     */
    @Override
    public void checkStatus() throws UnsupportedNetworkElementStatusException {
        String reliability = (String) this.getProperty(CopperPair.PROPERTY_RELIABILITY);
        if (reliability.equals("OK"))
            this.updateStatusTo(CopperPair.STATUS_OK);
        else
        	this.updateStatusTo(CopperPair.STATUS_BROKEN);
    }
}