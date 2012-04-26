package es.upm.dit.gsi.shanks.hackerhan.model.han.element.device;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * @author a.carrera
 *
 */
public class ModemADSL extends Device {

	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_NO_ADSL_CONNECTION = "DISCONNECTED";

	public static final String PROPERTY_POWER = "Power";
	public static final String PROPERTY_EXTERNAL_CONNECTION = "Connection";
	
	/**
	 * @param id
	 * @param initialState
	 * @param isGateway
	 * @throws UnsupportedNetworkElementStatusException
	 */
	public ModemADSL(String id, boolean isGateway)
			throws UnsupportedNetworkElementStatusException {
		super(id, ModemADSL.STATUS_OK, isGateway);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
	 */
	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
		// TODO Adapt the hole thing to HashMap String/boolean. 
        HashMap<String, Boolean> status = this.getStatus();
		if (status.equals(ModemADSL.STATUS_OFF)) {
			this.shutdown();
		} else if (status.equals(ModemADSL.STATUS_OK)) {
			this.addProperty(ModemADSL.PROPERTY_POWER, "ON");
			this.addProperty(ModemADSL.PROPERTY_EXTERNAL_CONNECTION, "OK");
		} else if (status.equals(ModemADSL.STATUS_NO_ADSL_CONNECTION)){
			this.addProperty(ModemADSL.PROPERTY_EXTERNAL_CONNECTION, "NOK");
		}
	}
	
	private void shutdown() {
		this.addProperty(ModemADSL.PROPERTY_POWER, "OFF");
		this.addProperty(ModemADSL.PROPERTY_EXTERNAL_CONNECTION, "NOK");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		String power = (String) this.getProperty(ModemADSL.PROPERTY_POWER);
		String connection = (String) this.getProperty(ModemADSL.PROPERTY_EXTERNAL_CONNECTION);
		
		if (power.equals("OFF")) {
			this.updateStatusTo(ModemADSL.STATUS_OFF, true);
			this.shutdown();
		} else if (!connection.equals("OK")) {
				this.updateStatusTo(ModemADSL.STATUS_NO_ADSL_CONNECTION, true);
		} else {
			this.updateStatusTo(ModemADSL.STATUS_OK, true);
		}
	}

	@Override
	public void fillIntialProperties() {
		this.addProperty(ModemADSL.PROPERTY_POWER, "ON");
		this.addProperty(ModemADSL.PROPERTY_EXTERNAL_CONNECTION, "OK");
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(ModemADSL.STATUS_OFF);
		this.addPossibleStatus(ModemADSL.STATUS_OK);
		this.addPossibleStatus(ModemADSL.STATUS_NO_ADSL_CONNECTION);
	}

}
