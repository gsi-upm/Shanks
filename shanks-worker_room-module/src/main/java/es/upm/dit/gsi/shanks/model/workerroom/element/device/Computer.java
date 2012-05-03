package es.upm.dit.gsi.shanks.model.workerroom.element.device;

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
			this.changeProperty(Computer.PROPERTY_ANTIVIRUS, true);
			this.changeProperty(Computer.PROPERTY_CPUFREQ, 3000);
			this.changeProperty(Computer.PROPERTY_ETHERNET_CONNECTION, true);
			this.changeProperty(Computer.PROPERTY_FANSPEED, 2000);
			this.changeProperty(Computer.PROPERTY_FIREWALL, true);
			this.changeProperty(Computer.PROPERTY_POWER, true);
			this.changeProperty(Computer.PROPERTY_PROCESSES, 55);
			this.changeProperty(Computer.PROPERTY_RAM, 0.25);
			this.changeProperty(Computer.PROPERTY_TEMPERATURE, 35);
		}
		if(status.get(Computer.STATUS_OFF)){
			this.changeProperty(Computer.PROPERTY_POWER, false);
		}
		if(status.get(Computer.STATUS_BUSSYRAM)){
			this.changeProperty(Computer.PROPERTY_RAM, 0.95);
		}
		if(status.get(Computer.STATUS_DISCONNECTED)){
			this.changeProperty(Computer.PROPERTY_ETHERNET_CONNECTION, false);
		}
		if(status.get(Computer.STATUS_HIGHTEMP)){
			this.changeProperty(PROPERTY_TEMPERATURE, 90);
		}
		if(status.get(Computer.STATUS_NOANTIVIRUS)){
			this.changeProperty(PROPERTY_ANTIVIRUS, false);
		}
		if(status.get(Computer.STATUS_NOFIREWALL)){
			this.changeProperty(PROPERTY_FIREWALL, false);
		}
		if(status.get(Computer.STATUS_OVERCLOCKING)){
			this.changeProperty(PROPERTY_CPUFREQ, 5000);
		}
		if(status.get(Computer.STATUS_UNDERCLOCKING)){
			this.changeProperty(Computer.STATUS_UNDERCLOCKING, 1000);
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
			this.setCurrentStatus(Computer.STATUS_OK, true);
			this.setCurrentStatus(Computer.STATUS_BUSSYRAM, false);
			this.setCurrentStatus(Computer.STATUS_DISCONNECTED, false);
			this.setCurrentStatus(Computer.STATUS_HIGHTEMP, false);
			this.setCurrentStatus(Computer.STATUS_NOANTIVIRUS, false);
			this.setCurrentStatus(Computer.STATUS_NOFIREWALL, false);
			this.setCurrentStatus(Computer.STATUS_OFF, false);
			this.setCurrentStatus(Computer.STATUS_OVERCLOCKING, false);
			this.setCurrentStatus(Computer.STATUS_UNDERCLOCKING, false);
		}
		if(!antivirus){
			this.setCurrentStatus(Computer.STATUS_OK, false);
			this.setCurrentStatus(Computer.STATUS_NOANTIVIRUS, true);
		}
		if(cpuFrequ <= 2500){
			this.setCurrentStatus(Computer.STATUS_OK, false);
			this.setCurrentStatus(Computer.STATUS_UNDERCLOCKING, true);
		}
		if(cpuFrequ >= 3500){
			this.setCurrentStatus(Computer.STATUS_OK, false);
			this.setCurrentStatus(Computer.STATUS_OVERCLOCKING, true);
		}
		if(!ethernet){
			this.setCurrentStatus(Computer.STATUS_OK, false);
			this.setCurrentStatus(Computer.STATUS_DISCONNECTED, true);
		}
		if(!firewall){
			this.setCurrentStatus(Computer.STATUS_OK, false);
			this.setCurrentStatus(Computer.STATUS_NOFIREWALL, true);
		}
		if(!power){
			this.setCurrentStatus(Computer.STATUS_OK, false);
			this.setCurrentStatus(Computer.STATUS_OFF, true);
		}
		if(ram <= 0.85){
			this.setCurrentStatus(Computer.STATUS_OK, false);
			this.setCurrentStatus(Computer.STATUS_BUSSYRAM, true);
		}
		if(temperature >= 80){
			this.setCurrentStatus(Computer.STATUS_OK, false);
			this.setCurrentStatus(Computer.STATUS_HIGHTEMP, false);
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
