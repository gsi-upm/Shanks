package es.upm.dit.gsi.shanks.model.element.device.test;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.device.Device;

/**
 * @author a.carrera
 *
 */
public class MyDevice extends Device {

    /**
     * @param id
     * @param initialState
     * @param isGateway
     * @throws ShanksException 
     */
    public MyDevice(String id, String initialState, boolean isGateway) throws ShanksException {
        super(id, initialState, isGateway);
    }

    public static final String OK_STATUS = "OK";
    public static final String NOK_STATUS = "NOK";
    public static final String UNKOWN_STATUS = "UNKOWN";
    public static final String HIGH_TEMP_STATUS = "HIGH-TEMP";
    
    public static final String OS_PROPERTY = "OS";
    public static final String TEMPERATURE_PROPERTY = "Temperature";
    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
     */
    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(MyDevice.OK_STATUS);
        this.addPossibleStatus(MyDevice.NOK_STATUS);
        this.addPossibleStatus(MyDevice.UNKOWN_STATUS);
        this.addPossibleStatus(MyDevice.HIGH_TEMP_STATUS);

    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
     */
    @Override
    public void fillIntialProperties() {
        this.addProperty(MyDevice.OS_PROPERTY, "Windows");
        this.addProperty(MyDevice.TEMPERATURE_PROPERTY, 15.5);
        
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
     */
    @Override
    public void checkProperties() throws ShanksException {
        HashMap<String, Boolean> status = this.getStatus();
            if (status.get(MyDevice.OK_STATUS)) {
                this.changeProperty(MyDevice.TEMPERATURE_PROPERTY, 30);
            } else if (status.get(MyDevice.NOK_STATUS)) {
                this.changeProperty(MyDevice.TEMPERATURE_PROPERTY, 90);
            } else if (status.get(MyDevice.UNKOWN_STATUS)) {
                this.changeProperty(MyDevice.TEMPERATURE_PROPERTY, null);
            } else if(status.get(MyDevice.HIGH_TEMP_STATUS)){
                this.changeProperty(MyDevice.TEMPERATURE_PROPERTY, 60);
            }
        
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
     */
    @Override
    public void checkStatus() throws ShanksException {
        Integer temp = (Integer) this.getProperty(MyDevice.TEMPERATURE_PROPERTY);
        if (temp <= 70 && temp >=50) {
            this.updateStatusTo(MyDevice.OK_STATUS, true);
            this.updateStatusTo(MyDevice.NOK_STATUS, false);
            this.updateStatusTo(MyDevice.UNKOWN_STATUS, false);
            this.updateStatusTo(MyDevice.HIGH_TEMP_STATUS, true);
        } else if (temp > 70){
            this.updateStatusTo(MyDevice.OK_STATUS, false);
            this.updateStatusTo(MyDevice.NOK_STATUS, true);
            this.updateStatusTo(MyDevice.UNKOWN_STATUS, false);
            this.updateStatusTo(MyDevice.HIGH_TEMP_STATUS, true);
        } else if(temp < 50){
            this.updateStatusTo(MyDevice.OK_STATUS, true);
            this.updateStatusTo(MyDevice.NOK_STATUS, false);
            this.updateStatusTo(MyDevice.UNKOWN_STATUS, false);
            this.updateStatusTo(MyDevice.HIGH_TEMP_STATUS, false);
        }
    }

}
