package es.upm.dit.gsi.shanks.model.workerroom.element;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

public class Computer extends Device{

	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_HIGHTEMP = "High Temperature";
	public static final String STATUS_DISCONNECTED = "Disconnected";
	public static final String STATUS_OVERCLOCKING = "Overclocking";
	public static final String STATUS_UNDERCLOCKING = "Underclocking";
	public static final String STATUS_NOANTIVIRUS = "Antivirus deactivated";
	public static final String STATUS_NOFIREWALL = "Firewall deactivated";
	public static final String STATUS_BUSSYRAM = "RAM saturation";
	

	public static final String PROPERTY_CPUFREQ = "CPU Frequency"; // In MHz
	public static final String PROPERTY_TEMPERATURE = "Temperature"; // In ºC
	public static final String PROPERTY_POWER = "Power"; //Power connection
	public static final String PROPERTY_ETHERNET_CONNECTION = "Connection"; //Ethernet connection
	public static final String PROPERTY_FANSPEED = "Fan Speed"; //Fan speed in rpm
	public static final String PROPERTY_PROCESSES = "Open processes"; //Number of open processes
	public static final String PROPERTY_RAM = "RAM occupied"; //Percentage of RAM occupied
	public static final String PROPERTY_ANTIVIRUS = "Antivirus Situation"; //true activated, false deactivated
	public static final String PROPERTY_FIREWALL = "Firewall Situation"; //true activated, false deactivated
		

	public Computer(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id, Computer.STATUS_OK, false);
	}

	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
		
		
	}

	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fillIntialProperties() {
		this.addProperty(Computer.PROPERTY_ANTIVIRUS, true);
		this.addProperty(Computer.PROPERTY_CPUFREQ, 3000);
		this.addProperty(Computer.PROPERTY_ETHERNET_CONNECTION, true);
		this.addProperty(Computer.PROPERTY_FANSPEED, 2000);
		this.addProperty(Computer.PROPERTY_FIREWALL, true);
		this.addProperty(Computer.PROPERTY_POWER, true);
		this.addProperty(Computer.PROPERTY_PROCESSES, 55);
		this.addProperty(Computer.PROPERTY_RAM, 0.50);
		this.addProperty(Computer.PROPERTY_TEMPERATURE, 35);		
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(Computer.STATUS_OK);
        this.addPossibleStatus(Computer.STATUS_OFF);
        this.addPossibleStatus(Computer.STATUS_HIGHTEMP);
        this.addPossibleStatus(Computer.STATUS_DISCONNECTED);
        this.addPossibleStatus(Computer.STATUS_BUSSYRAM);
        this.addPossibleStatus(Computer.STATUS_NOANTIVIRUS);
        this.addPossibleStatus(Computer.STATUS_OVERCLOCKING);
        this.addPossibleStatus(Computer.STATUS_UNDERCLOCKING);
        this.addPossibleStatus(Computer.STATUS_NOANTIVIRUS);
        this.addPossibleStatus(Computer.STATUS_NOFIREWALL);	
	}

}
