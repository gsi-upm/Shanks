package es.upm.dit.gsi.shanks.tutorial.model.han.element.device;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * @author a.carrera
 * 
 */
public class Computer extends Device {

	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_HIGHTEMP = "High Temperature";
	public static final String STATUS_DISCONNECTED = "Disconnected";

	public static final String PROPERTY_CPUFREQ = "CPU Frequency"; // In MHz
	public static final String PROPERTY_TEMPERATURE = "Temperature"; // In ºC
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
		
		String status = this.getCurrentStatus();
		
		if (status.equals(Computer.STATUS_OFF)) {
			this.shutdown();
		} else if (status.equals(Computer.STATUS_HIGHTEMP)) {
			this.addProperty(Computer.PROPERTY_TEMPERATURE, 60.0);
			this.addProperty(Computer.PROPERTY_POWER, "ON");
		} else if (status.equals(Computer.STATUS_OK)) {
			this.addProperty(Computer.PROPERTY_TEMPERATURE,
					30 + Math.random() * 20);
			this.addProperty(Computer.PROPERTY_CPUFREQ,
					300 + Math.random() * 200);
			this.addProperty(Computer.PROPERTY_POWER, "ON");
			this.addProperty(Computer.PROPERTY_ETHERNET_CONNECTION, "CONNECTED");
		} else if (status.equals(Computer.STATUS_DISCONNECTED)) {
			this.addProperty(Computer.PROPERTY_ETHERNET_CONNECTION, "IP NOK");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		double temp = (Double) this.getProperty(Computer.PROPERTY_TEMPERATURE);
		double freq = (Double) this.getProperty(Computer.PROPERTY_CPUFREQ);
		String power = (String) this.getProperty(Computer.PROPERTY_POWER);
		String connection = (String) this.getProperty(Computer.PROPERTY_ETHERNET_CONNECTION);

		if (power.equals("OFF")) {
			this.updateStatusTo(Computer.STATUS_OFF);
			this.shutdown();
		} else {
			if (temp >= 80) {
				this.updateStatusTo(Computer.STATUS_HIGHTEMP);
			} else if (freq > 999) {
				this.addProperty(Computer.PROPERTY_TEMPERATURE, temp+5);
			} else {
				if (connection.equals("CONNECTED")) {
					this.updateStatusTo(Computer.STATUS_OK);
				} else {
					this.updateStatusTo(Computer.STATUS_DISCONNECTED);
				}
			}
		}
	}

	private void shutdown() {
		this.addProperty(Computer.PROPERTY_CPUFREQ, 0);
		this.addProperty(Computer.PROPERTY_TEMPERATURE, 0);
		this.addProperty(Computer.PROPERTY_POWER, "OFF");
		this.addProperty(Computer.PROPERTY_ETHERNET_CONNECTION, "DISCONNECTED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
	 */
	@Override
	public void fillIntialProperties() {
		this.addProperty(Computer.PROPERTY_CPUFREQ, 800.0);
		this.addProperty(Computer.PROPERTY_TEMPERATURE, 30.0);
		this.addProperty(Computer.PROPERTY_POWER, "ON");
		this.addProperty(Computer.PROPERTY_ETHERNET_CONNECTION, "CONNECTED");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(Computer.STATUS_HIGHTEMP);
		this.addPossibleStatus(Computer.STATUS_DISCONNECTED);
		this.addPossibleStatus(Computer.STATUS_OK);
		this.addPossibleStatus(Computer.STATUS_OFF);
	}

}
