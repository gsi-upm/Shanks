package es.upm.dit.gsi.shanks.model.element.link.test;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;

/**
 * @author a.carrera
 *
 */
public class MyLink extends Link {

    public static final String OK_STATUS = "OK";
    public static final String BROKEN_STATUS = "BROKEN";
    
    public static final String DISTANCE_PROPERTY = "Distance";
    public static final String LINK_TYPE_PROPERTY = "LinkType";
    
    /**
     * @param id
     * @param initialState
     * @param capacity
     * @throws UnsupportedNetworkElementStatusException
     */
    public MyLink(String id, String initialState, int capacity) throws UnsupportedNetworkElementStatusException {
        super(id, initialState, capacity);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
     */
    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(MyLink.OK_STATUS);
        this.addPossibleStatus(MyLink.BROKEN_STATUS);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
     */
    @Override
    public void checkProperties() throws UnsupportedNetworkElementStatusException {
        String status = this.getCurrentStatus();
        if (status.equals(MyLink.BROKEN_STATUS)) {
            this.changeProperty(MyLink.DISTANCE_PROPERTY, 0.0);
        } else if (status.equals(MyLink.OK_STATUS)) {
            this.changeProperty(MyLink.DISTANCE_PROPERTY, 3.5);
        }
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
     */
    @Override
    public void fillIntialProperties() {
        this.addProperty(MyLink.LINK_TYPE_PROPERTY, "Ethernet");
        this.addProperty(MyLink.DISTANCE_PROPERTY, 3.5);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
     */
    @Override
    public void checkStatus() throws UnsupportedNetworkElementStatusException {
        Double distance = (Double) this.getProperty(MyLink.DISTANCE_PROPERTY);
        if (distance>0){
            this.updateStatusTo(MyLink.OK_STATUS);
        }
    }

}
