package es.upm.dit.gsi.shanks.notification.util.test;

import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;

public class TestDevice extends Device {

    public TestDevice(String id, String initialState, boolean isGateway, Logger logger)
            throws ShanksException {
        super(id, initialState, isGateway, logger);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void fillIntialProperties() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void checkProperties()
            throws UnsupportedNetworkElementFieldException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void checkStatus() throws UnsupportedNetworkElementFieldException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setPossibleStates() {
        // TODO Auto-generated method stub
        
    }

}
