package es.upm.dit.gsi.shanks.model.element.device.test;

import es.upm.dit.gsi.shanks.model.element.device.Device;

public class MyDevice extends Device {

    public MyDevice(String id, String initialState, boolean isGateway) {
        super(id, initialState, isGateway);
    }

    public static final String OK = "OK";
    public static final String NOK = "NOK";
    public static final String UNKOWN = "UNKOWN";
    
    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(MyDevice.OK);
        this.addPossibleStatus(MyDevice.NOK);
        this.addPossibleStatus(MyDevice.UNKOWN);

    }

}
