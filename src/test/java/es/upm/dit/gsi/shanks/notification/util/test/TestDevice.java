package es.upm.dit.gsi.shanks.notification.util.test;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

public class TestDevice extends Device {

    public TestDevice(String id, String initialState, boolean isGateway)
            throws UnsupportedNetworkElementStatusException {
        super(id, initialState, isGateway);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void fillIntialProperties() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void checkProperties()
            throws UnsupportedNetworkElementStatusException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void checkStatus() throws UnsupportedNetworkElementStatusException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setPossibleStates() {
        // TODO Auto-generated method stub
        
    }

}
