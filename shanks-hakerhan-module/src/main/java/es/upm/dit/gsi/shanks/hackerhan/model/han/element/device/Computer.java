/**
 * es.upm.dit.gsi
 * 30/04/2012
 */
package es.upm.dit.gsi.shanks.hackerhan.model.han.element.device;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.hackerhan.model.han.element.Values;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * This is a simple implementation of Computer as a device of the simulation. On this simulation
 * the computer device has only three possible states: 
 * 		- OFF
		- OK 
		- NOK 
 * OK and NOK which implies that the device is turned on.
 * 
 * Computer are used by Hackers and Users to access different content on the Internet. So each 
 * of this agents actions let a trace to the computer in which the operation was performed. Unless 
 * the hacker hide it or mask.  
 * 
 * @author darofar
 *
 */
public class Computer extends Device {

	
	// It is simplified at there is a failure or not. Not in detail what type of failure. 
	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_NOK = "NOK";
	public static final String STATUS_HIGHTEMP = "High Temperature";
	public static final String STATUS_DISCONNECTED = "Disconnected";

	public static final String PROPERTY_CPUFREQ = "CPU Frequency"; // In %
	public static final String PROPERTY_TEMPERATURE = "Temperature"; // In ªC
	public static final String PROPERTY_POWER = "Power";
	public static final String PROPERTY_ETHERNET_CONNECTION = "Connection";

	public Computer(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id, Computer.STATUS_OK, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
	 */
	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
        HashMap<String, Boolean> status = this.getStatus();		
		if (status.get(Computer.STATUS_OFF)) {
			this.shutdown();
		} else if (status.get(Computer.STATUS_NOK)) {
			this.updatePropertyTo(Computer.PROPERTY_POWER, "ON");
			this.updatePropertyTo(Computer.PROPERTY_CPUFREQ, 99);
			this.updatePropertyTo(Computer.PROPERTY_TEMPERATURE, 70.0);
			this.updatePropertyTo(Computer.PROPERTY_ETHERNET_CONNECTION, Values.DISCONNECTED);
		} else {
			this.updatePropertyTo(Computer.PROPERTY_POWER, "ON");
			this.updatePropertyTo(Computer.PROPERTY_CPUFREQ, 10);
			this.updatePropertyTo(Computer.PROPERTY_TEMPERATURE, 40.0);
			this.updatePropertyTo(Computer.PROPERTY_ETHERNET_CONNECTION, Values.CONNECTED);
			this.updateStatusTo(Computer.STATUS_OK, true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		String power = (String) this.getProperty(Computer.PROPERTY_POWER);
		double temp = (Double) this.getProperty(Computer.PROPERTY_TEMPERATURE);
		double freq = (Double) this.getProperty(Computer.PROPERTY_CPUFREQ);
		String connection = (String) this.getProperty(Computer.PROPERTY_ETHERNET_CONNECTION);

		if (power.equals(Values.OFF)) {
			this.updateStatusTo(Computer.STATUS_OFF, true);
			this.shutdown();
		} else if ((temp >= 80)||(freq > 90)||(connection.equals(Values.DISCONNECTED))) {
				this.updateStatusTo(Computer.STATUS_NOK, true);
				this.updateStatusTo(Computer.STATUS_OK, false);
		} else {
			this.updateStatusTo(Computer.STATUS_OK, true);
			this.updateStatusTo(Computer.STATUS_NOK, false);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
	 */
	@Override
	public void fillIntialProperties() {
		this.addProperty(Computer.PROPERTY_CPUFREQ, 50);
		this.addProperty(Computer.PROPERTY_TEMPERATURE, 30.0);
		this.addProperty(Computer.PROPERTY_POWER, Values.ON);
		this.addProperty(Computer.PROPERTY_ETHERNET_CONNECTION, Values.CONNECTED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(Computer.STATUS_NOK);
		this.addPossibleStatus(Computer.STATUS_OK);
		this.addPossibleStatus(Computer.STATUS_OFF);
	}

	/**
	 * Accessory method that configures the device on shut down state. 
	 * 
	 * @throws UnsupportedNetworkElementStatusException
	 */
	private void shutdown() throws UnsupportedNetworkElementStatusException {
		this.updatePropertyTo(Computer.PROPERTY_CPUFREQ, 0);
		this.updatePropertyTo(Computer.PROPERTY_TEMPERATURE, 0);
		this.updatePropertyTo(Computer.PROPERTY_POWER, Values.OFF);
		this.updatePropertyTo(Computer.PROPERTY_ETHERNET_CONNECTION, Values.DISCONNECTED);
		this.updateStatusTo(Computer.STATUS_OK, false);
		this.updateStatusTo(Computer.STATUS_NOK, false);
	}
}
