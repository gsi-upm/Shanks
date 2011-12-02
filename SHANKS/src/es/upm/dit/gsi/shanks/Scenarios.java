package es.upm.dit.gsi.shanks;

import java.util.List;

import es.upm.dit.gsi.shanks.devices.Device;

/**
 * Scenarios class
 * 
 * This class create the different scenarios
 * 
 * @author Daniel Lara
 * @version 0.1
 *
 */


//This class make the diferent scenarios
public class Scenarios {
	
	public String name;
	public List<Device> devices;
	public List<Device> ftth;
	public Scenarios scenario;
	
	
	public Scenarios(String name, List<Device> devices){
		this.name = name;
		this.devices = devices;
		
	}

	public String getName(){
		return name;
	}
	
	public List<Device> getDevices(){
		return devices;
	}
	
}
