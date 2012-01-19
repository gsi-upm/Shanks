package es.upm.dit.gsi.shanks.model.element.link.test;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;

public class MyLink extends Link {

    public static final String OK_STATUS = "OK";
    public static final String BROKEN_STATUS = "BROKEN";
    
    public static final String DISTANCE_PROPERTY = "Distance";
    public static final String LINK_TYPE_PROPERTY = "LinkType";
    
    public MyLink(String id, String initialState, int capacity) throws UnsupportedNetworkElementStatusException {
        super(id, initialState, capacity);
    }

    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(MyLink.OK_STATUS);
        this.addPossibleStatus(MyLink.BROKEN_STATUS);
    }

    @Override
    public void checkProperties() {
        String status = this.getCurrentStatus();
        if (status.equals(MyLink.BROKEN_STATUS)) {
            this.changeProperty(MyLink.DISTANCE_PROPERTY, 0);
        } else if (status.equals(MyLink.OK_STATUS)) {
            this.changeProperty(MyLink.DISTANCE_PROPERTY, 3.5);
        }
    }

    @Override
    public void fillIntialProperties() {
        this.addProperty(MyLink.LINK_TYPE_PROPERTY, "Ethernet");
        this.addProperty(MyLink.DISTANCE_PROPERTY, 3.5);
    }

}
