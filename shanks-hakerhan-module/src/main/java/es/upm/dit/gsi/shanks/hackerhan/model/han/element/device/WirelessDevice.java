/**
 * 
 */
package es.upm.dit.gsi.shanks.hackerhan.model.han.element.device;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * @author darofar
 *
 */
public class WirelessDevice extends Device{

	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_DISCONNECTED = "Disconnected";
	public static final String STATUS_OUT_OF_RANGE = "Out of range";
	public static final String STATUS_DISCHARGED = "Discharged";

	public static final String PROPERTY_SIGNAL = "Signal"; // In %
	public static final String PROPERTY_BATTERY_CAPACITY = "Battery Power"; //In %
	public static final String PROPERTY_CONNECTION = "Connection";
	public static final String PROPERTY_POWER = "Power";

	
	public WirelessDevice(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id, WirelessDevice.STATUS_OK, false);
	}

	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {

		// TODO Adapt the hole thing to HashMap String/boolean. 
        HashMap<String, Boolean> status = this.getStatus();
		if(status.equals(WirelessDevice.STATUS_OFF)) {
			this.shutdown();
		} else if(status.equals(WirelessDevice.STATUS_OK)) {
			this.addProperty(WirelessDevice.PROPERTY_BATTERY_CAPACITY,
					50 + Math.random() * 50);
			this.addProperty(WirelessDevice.PROPERTY_SIGNAL,
					70 + Math.random() * 30);
			this.addProperty(WirelessDevice.PROPERTY_CONNECTION, "Connected");
			this.addProperty(WirelessDevice.PROPERTY_POWER, "ON");
		} else if(status.equals(WirelessDevice.STATUS_DISCONNECTED)) {
			this.addProperty(WirelessDevice.PROPERTY_CONNECTION, "Disconnected");
			this.addProperty(WirelessDevice.PROPERTY_POWER, "ON");
		} else if(status.equals(WirelessDevice.STATUS_OUT_OF_RANGE)) {
			this.addProperty(WirelessDevice.PROPERTY_SIGNAL, 0.1);
			this.addProperty(WirelessDevice.PROPERTY_CONNECTION, "Disconnected");
			this.addProperty(WirelessDevice.PROPERTY_POWER, "ON");
		} else if(status.equals(WirelessDevice.STATUS_DISCHARGED)) {
			this.addProperty(WirelessDevice.PROPERTY_BATTERY_CAPACITY, 1.0);
			this.addProperty(WirelessDevice.PROPERTY_CONNECTION, "Disconnected");
			this.addProperty(WirelessDevice.PROPERTY_POWER, "ON");
		}
	}

	private void shutdown() {
		this.addProperty(WirelessDevice.PROPERTY_CONNECTION, "Disconnected");
		this.addProperty(WirelessDevice.PROPERTY_SIGNAL, 0.0);
		this.addProperty(WirelessDevice.PROPERTY_POWER, "OFF");
	}

	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {

		double battery = (Double) this.getProperty(WirelessDevice.PROPERTY_BATTERY_CAPACITY);
		double signal = (Double) this.getProperty(WirelessDevice.PROPERTY_SIGNAL);
		String connection = (String) this.getProperty(WirelessDevice.PROPERTY_CONNECTION);
		String power = (String) this.getProperty(WirelessDevice.PROPERTY_POWER);

		if (power.equals("OFF")) {
			this.updateStatusTo(WirelessDevice.STATUS_OFF, true);
			this.shutdown();
		} else {
			if (battery <= 5) {
				this.updateStatusTo(WirelessDevice.STATUS_DISCHARGED, true);
			} else if (signal > 50) {
				if (connection.equals("Connected")) {
					this.updateStatusTo(WirelessDevice.STATUS_OK, true);
				} else {
					this.updateStatusTo(WirelessDevice.STATUS_DISCONNECTED, true);
				}
			} else {
				this.updateStatusTo(WirelessDevice.STATUS_OUT_OF_RANGE, true);
			}
		}
	}

	@Override
	public void fillIntialProperties() {
		this.addProperty(PROPERTY_CONNECTION, "Connected");
		this.addProperty(PROPERTY_POWER, "ON");
		this.addProperty(PROPERTY_SIGNAL, 70+Math.random()*10);
		this.addProperty(PROPERTY_BATTERY_CAPACITY, 50+Math.random()*10);
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(WirelessDevice.STATUS_OFF);
		this.addPossibleStatus(WirelessDevice.STATUS_OK);
		this.addPossibleStatus(WirelessDevice.STATUS_DISCONNECTED);
		this.addPossibleStatus(WirelessDevice.STATUS_DISCHARGED);
		this.addPossibleStatus(WirelessDevice.STATUS_OUT_OF_RANGE);
	}

}
