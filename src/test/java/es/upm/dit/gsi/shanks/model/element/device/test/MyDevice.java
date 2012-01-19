package es.upm.dit.gsi.shanks.model.element.device.test;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

public class MyDevice extends Device {

    public MyDevice(String id, String initialState, boolean isGateway) throws UnsupportedNetworkElementStatusException {
        super(id, initialState, isGateway);
    }

    public static final String OK_STATUS = "OK";
    public static final String NOK_STATUS = "NOK";
    public static final String UNKOWN_STATUS = "UNKOWN";
    
    public static final String OS_PROPERTY = "OS";
    public static final String TEMPERATURE_PROPERTY = "Temperature";
    
    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(MyDevice.OK_STATUS);
        this.addPossibleStatus(MyDevice.NOK_STATUS);
        this.addPossibleStatus(MyDevice.UNKOWN_STATUS);

    }

    @Override
    public void fillIntialProperties() {
        this.addProperty(MyDevice.OS_PROPERTY, "Windows");
        this.addProperty(MyDevice.TEMPERATURE_PROPERTY, 15.5);
    }

    @Override
    public void checkProperties() {
        String status = this.getCurrentStatus();
        if (status.equals(MyDevice.OK_STATUS)) {
            this.changeProperty(MyDevice.TEMPERATURE_PROPERTY, 30);
        } else if (status.equals(MyDevice.NOK_STATUS)) {
            this.changeProperty(MyDevice.TEMPERATURE_PROPERTY, 90);
        } else if (status.equals(MyDevice.UNKOWN_STATUS)) {
            this.changeProperty(MyDevice.TEMPERATURE_PROPERTY, null);
        }
    }

}
