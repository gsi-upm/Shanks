package es.upm.dit.gsi.shanks.model.failure.util.test;

import java.util.HashMap;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.device.Device;

/**
 * @author a.carrera
 *
 */
public class FailureTestDevice extends Device {

    private String userField;
    
    /**
     * @param id
     * @param initialState
     * @param isGateway
     * @throws ShanksException
     */
    public FailureTestDevice(String id, String initialState, boolean isGateway, Logger logger) throws ShanksException {
        super(id, initialState, isGateway, logger);
        this.userField = null;
    }

    public static final String OK_STATUS = "OK";
    public static final String NOK_STATUS = "NOK";
    public static final String OFF_STATUS = "OFF";
    public static final String UNKOWN_STATUS = "UNKOWN";
    
    public static final String POWER_PROPERTY = "Power";
    public static final String FUNDATIONS_PROPERTY = "Fundations";
    // It is OK if >1 and No-OK if <-1 and oknown of 1>x>-1
    public static final String OTHER_PROPERTY = "Other";
    
    //User Definitions. 
    public static final String USER_FIELD = "userField";
    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
     */
    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(FailureTestDevice.OK_STATUS);
        this.addPossibleStatus(FailureTestDevice.NOK_STATUS);
        this.addPossibleStatus(FailureTestDevice.UNKOWN_STATUS);
        this.addPossibleStatus(FailureTestDevice.OFF_STATUS);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
     */
    @Override
    public void fillIntialProperties() {
        this.addProperty(FailureTestDevice.FUNDATIONS_PROPERTY, "OK");
        this.addProperty(FailureTestDevice.OTHER_PROPERTY, 1.0);
        this.addProperty(FailureTestDevice.POWER_PROPERTY, "ON");
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
     */
    @Override
    public void checkProperties() throws ShanksException {
        HashMap<String, Boolean> status = this.getStatus();
        if (status.get(FailureTestDevice.OFF_STATUS)) {
            this.updatePropertyTo(FailureTestDevice.POWER_PROPERTY, "OFF");
            this.updatePropertyTo(FailureTestDevice.FUNDATIONS_PROPERTY, "UNKNOWN");
            this.updatePropertyTo(FailureTestDevice.OTHER_PROPERTY, 1.0);
        } else {
            this.updatePropertyTo(FailureTestDevice.POWER_PROPERTY, "ON");
            if (status.get(FailureTestDevice.NOK_STATUS)) {
                this.updatePropertyTo(FailureTestDevice.FUNDATIONS_PROPERTY, "NOK");
                this.updatePropertyTo(FailureTestDevice.OTHER_PROPERTY, -1.0);
            } else if (status.get(FailureTestDevice.OK_STATUS)) {
                this.updatePropertyTo(FailureTestDevice.FUNDATIONS_PROPERTY, "OK");    
                this.updatePropertyTo(FailureTestDevice.OTHER_PROPERTY, 1.0);
            } else if(status.get(FailureTestDevice.UNKOWN_STATUS)){
                this.updatePropertyTo(FailureTestDevice.OTHER_PROPERTY, 0.0);
                this.updatePropertyTo(FailureTestDevice.FUNDATIONS_PROPERTY, "UNKNOWN");
            }
        }
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
     */
    @Override
    public void checkStatus() throws ShanksException {
        String power = (String) this.getProperty(FailureTestDevice.POWER_PROPERTY);
        String fundations = (String) this.getProperty(FUNDATIONS_PROPERTY);
        Double other = (Double) this.getProperty(FailureTestDevice.OTHER_PROPERTY);
        
        if (power.equals("OFF")) {
            this.updateStatusTo(FailureTestDevice.OK_STATUS, false);
            this.updateStatusTo(FailureTestDevice.NOK_STATUS, false);
            this.updateStatusTo(FailureTestDevice.UNKOWN_STATUS, false);
            this.updateStatusTo(FailureTestDevice.OFF_STATUS, true);
        } else {
            this.updateStatusTo(FailureTestDevice.OFF_STATUS, false);
            if (fundations.equals("OK")&&(other>0)){
                this.updateStatusTo(FailureTestDevice.OK_STATUS, true);
                this.updateStatusTo(FailureTestDevice.NOK_STATUS, false);
                this.updateStatusTo(FailureTestDevice.UNKOWN_STATUS, false);
            } else if (fundations.equals("NOK")&&(other<0)) {
                this.updateStatusTo(FailureTestDevice.OK_STATUS, false);
                this.updateStatusTo(FailureTestDevice.NOK_STATUS, true);
                this.updateStatusTo(FailureTestDevice.UNKOWN_STATUS, false);
            } else {
                this.updateStatusTo(FailureTestDevice.OK_STATUS, false);
                this.updateStatusTo(FailureTestDevice.NOK_STATUS, false);
                this.updateStatusTo(FailureTestDevice.UNKOWN_STATUS, true);
            }
        }
    }

    /**
     * @return the userField
     */
    public String getUserField() {
        return userField;
    }

    /**
     * @param userField the userField to set
     */
    public void setUserField(String userField) {
        this.userField = userField;
    }

}