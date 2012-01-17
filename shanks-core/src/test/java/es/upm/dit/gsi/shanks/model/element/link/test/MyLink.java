package es.upm.dit.gsi.shanks.model.element.link.test;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;

public class MyLink extends Link {

    public static final String OK = "OK";
    public static final String BROKEN = "BROKEN";
    
    public MyLink(String id, String initialState, int capacity) throws UnsupportedNetworkElementStatusException {
        super(id, initialState, capacity);
    }

    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(MyLink.OK);
        this.addPossibleStatus(MyLink.BROKEN);
    }

}
