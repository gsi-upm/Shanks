package es.upm.dit.gsi.shanks.hackerhan.model.adsl.element.device;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * @author a.carrera
 *
 */
public class DSLAM extends Device {



	public DSLAM(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id, STATUS_OK, true);
	}

	public static final String STATUS_OK = "OK";
    public static final String STATUS_NOK = "NOK";
    public static final String STATUS_CONGESTED = "CONGESTED";
    public static final String STATUS_HIGHTEMP = "HIGH_TEMPERATURE";
    public static final String STATUS_UNKOWN = "UNKOWN";
    
    public static final String PROPERTY_MODEL = "Model";
    public static final String PROPERTY_TEMPERATURE = "Temperature"; // 0�C-100�C
    public static final String PROPERTY_CONGESTION = "Congestion"; // In %
    
    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
     */
    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(DSLAM.STATUS_OK);
        this.addPossibleStatus(DSLAM.STATUS_NOK);
        this.addPossibleStatus(DSLAM.STATUS_UNKOWN);
        this.addPossibleStatus(DSLAM.STATUS_CONGESTED);
        this.addPossibleStatus(DSLAM.STATUS_HIGHTEMP);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
     */
    @Override
    public void fillIntialProperties() {
        this.addProperty(DSLAM.PROPERTY_MODEL, "CISCO");
        this.addProperty(DSLAM.PROPERTY_TEMPERATURE, 20.0);
        this.addProperty(DSLAM.PROPERTY_CONGESTION, 0.02);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
     */
    @Override
    public void checkProperties() throws UnsupportedNetworkElementStatusException {
        // TODO Adapt the hole thing to hasMapa String/Boolean.
    	HashMap<String, Boolean> status = this.getStatus();
        if (status.equals(DSLAM.STATUS_OK)) {
			this.addProperty(DSLAM.PROPERTY_TEMPERATURE, 20.0);
			this.addProperty(DSLAM.PROPERTY_CONGESTION, 0.02);
        } else if (status.equals(DSLAM.STATUS_NOK)) {
            this.addProperty(DSLAM.PROPERTY_TEMPERATURE, 50.0+Math.random()*49);
            this.addProperty(DSLAM.PROPERTY_CONGESTION, 0.1+Math.random());
        } else if(status.equals(DSLAM.STATUS_CONGESTED)) {
        	this.addProperty(DSLAM.PROPERTY_CONGESTION, 0.5+Math.random()*0.5);
        } else if(status.equals(DSLAM.STATUS_HIGHTEMP)) {
        	this.addProperty(DSLAM.PROPERTY_TEMPERATURE, 70.0+Math.random()*29);
        } else if (status.equals(DSLAM.STATUS_UNKOWN)) {
            this.addProperty(DSLAM.PROPERTY_TEMPERATURE, null);
            this.addProperty(DSLAM.PROPERTY_CONGESTION, null);
        }
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
     */
    @Override
    public void checkStatus() throws UnsupportedNetworkElementStatusException {
        Double temp = (Double) this.getProperty(DSLAM.PROPERTY_TEMPERATURE);
        Double cong = (Double) this.getProperty(DSLAM.PROPERTY_CONGESTION);
        if ((temp == null)||(cong==null)) {
        	this.updateStatusTo(DSLAM.STATUS_UNKOWN, true);
        } else {
        	if ((temp>50)&&(cong>0.3))
                this.updateStatusTo(DSLAM.STATUS_NOK, true);
        	else if (temp>70) 
            	this.updateStatusTo(DSLAM.STATUS_HIGHTEMP, true);
        	else if (cong>0.5) 
            	this.updateStatusTo(DSLAM.STATUS_CONGESTED, true);
        	else 
        		this.updateStatusTo(DSLAM.STATUS_OK, true);
        }
    }
}