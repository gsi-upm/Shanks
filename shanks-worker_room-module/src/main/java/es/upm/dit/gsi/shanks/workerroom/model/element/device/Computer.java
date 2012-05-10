package es.upm.dit.gsi.shanks.workerroom.model.element.device;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * Class that represent a personal computer
 * 
 * @author dlara
 *
 */
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
		

	public Computer(String id, String status, boolean isGateway)
			throws UnsupportedNetworkElementStatusException {
		super(id, status, isGateway);
	}

	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
		HashMap<String, Boolean> status = this.getStatus();
		if(status.get(Computer.STATUS_OK)){
			this.updatePropertyTo(Computer.PROPERTY_ANTIVIRUS, true);
			this.updatePropertyTo(Computer.PROPERTY_CPUFREQ, 3000);
			this.updatePropertyTo(Computer.PROPERTY_ETHERNET_CONNECTION, true);
			this.updatePropertyTo(Computer.PROPERTY_FANSPEED, 2000);
			this.updatePropertyTo(Computer.PROPERTY_FIREWALL, true);
			this.updatePropertyTo(Computer.PROPERTY_POWER, true);
			this.updatePropertyTo(Computer.PROPERTY_PROCESSES, 55);
			this.updatePropertyTo(Computer.PROPERTY_RAM, 0.25);
			this.updatePropertyTo(Computer.PROPERTY_TEMPERATURE, 35);
		}
		if(status.get(Computer.STATUS_OFF)){
			this.updatePropertyTo(Computer.PROPERTY_POWER, false);
		}
		if(status.get(Computer.STATUS_BUSSYRAM)){
			this.updatePropertyTo(Computer.PROPERTY_RAM, 0.95);
		}
		if(status.get(Computer.STATUS_DISCONNECTED)){
			this.updatePropertyTo(Computer.PROPERTY_ETHERNET_CONNECTION, false);
		}
		if(status.get(Computer.STATUS_HIGHTEMP)){
			this.updatePropertyTo(PROPERTY_TEMPERATURE, 90);
		}
		if(status.get(Computer.STATUS_NOANTIVIRUS)){
			this.updatePropertyTo(PROPERTY_ANTIVIRUS, false);
		}
		if(status.get(Computer.STATUS_NOFIREWALL)){
			this.updatePropertyTo(PROPERTY_FIREWALL, false);
		}
		if(status.get(Computer.STATUS_OVERCLOCKING)){
			this.updatePropertyTo(PROPERTY_CPUFREQ, 5000);
		}
		if(status.get(Computer.STATUS_UNDERCLOCKING)){
			this.updatePropertyTo(Computer.STATUS_UNDERCLOCKING, 1000);
		}
	}

	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		Boolean antivirus = (Boolean) this.getProperty(Computer.PROPERTY_ANTIVIRUS);
		Integer cpuFrequ = (Integer) this.getProperty(Computer.PROPERTY_CPUFREQ);
		Boolean ethernet = (Boolean) this.getProperty(Computer.PROPERTY_ETHERNET_CONNECTION);
		Integer fanSpeed = (Integer) this.getProperty(Computer.PROPERTY_FANSPEED);
		Boolean firewall = (Boolean) this.getProperty(Computer.PROPERTY_FIREWALL);
		Boolean power = (Boolean) this.getProperty(Computer.PROPERTY_POWER);
		Integer processes = (Integer) this.getProperty(Computer.PROPERTY_PROCESSES);
		Double ram = (Double) this.getProperty(Computer.PROPERTY_RAM);
		Integer temperature = (Integer) this.getProperty(Computer.PROPERTY_TEMPERATURE);
		
		if(antivirus && 3500 > cpuFrequ && cpuFrequ > 2500 && ethernet && fanSpeed > 1500
				&& firewall && power && processes < 65 && ram < 0.85 && temperature < 80){
			this.updateStatusTo(Computer.STATUS_OK, true);
			this.updateStatusTo(Computer.STATUS_BUSSYRAM, false);
			this.updateStatusTo(Computer.STATUS_DISCONNECTED, false);
			this.updateStatusTo(Computer.STATUS_HIGHTEMP, false);
			this.updateStatusTo(Computer.STATUS_NOANTIVIRUS, false);
			this.updateStatusTo(Computer.STATUS_NOFIREWALL, false);
			this.updateStatusTo(Computer.STATUS_OFF, false);
			this.updateStatusTo(Computer.STATUS_OVERCLOCKING, false);
			this.updateStatusTo(Computer.STATUS_UNDERCLOCKING, false);
		}
		if(!antivirus){
			this.updateStatusTo(Computer.STATUS_OK, false);
			this.updateStatusTo(Computer.STATUS_NOANTIVIRUS, true);
		}
		if(cpuFrequ <= 2500){
			this.updateStatusTo(Computer.STATUS_OK, false);
			this.updateStatusTo(Computer.STATUS_UNDERCLOCKING, true);
		}
		if(cpuFrequ >= 3500){
			this.updateStatusTo(Computer.STATUS_OK, false);
			this.updateStatusTo(Computer.STATUS_OVERCLOCKING, true);
		}
		if(!ethernet){
			this.updateStatusTo(Computer.STATUS_OK, false);
			this.updateStatusTo(Computer.STATUS_DISCONNECTED, true);
		}
		if(!firewall){
			this.updateStatusTo(Computer.STATUS_OK, false);
			this.updateStatusTo(Computer.STATUS_NOFIREWALL, true);
		}
		if(!power){
			this.updateStatusTo(Computer.STATUS_OK, false);
			this.updateStatusTo(Computer.STATUS_OFF, true);
		}
		if(ram <= 0.85){
			this.updateStatusTo(Computer.STATUS_OK, false);
			this.updateStatusTo(Computer.STATUS_BUSSYRAM, true);
		}
		if(temperature >= 80){
			this.updateStatusTo(Computer.STATUS_OK, false);
			this.updateStatusTo(Computer.STATUS_HIGHTEMP, false);
		}
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
		this.addProperty(Computer.PROPERTY_RAM, 0.25);
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
