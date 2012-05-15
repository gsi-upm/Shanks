package es.upm.dit.gsi.shanks.networkattacks.util.networkelements;

import java.util.ArrayList;

import es.upm.dit.gsi.shanks.model.element.device.Device;

public class Firewall {
	
	private ArrayList<Device> block;
	private ArrayList<Device> allowed;
	private ArrayList<String> ports;
	
	public void blockAccess(Device d){
		this.block.add(d);
	}

	public boolean isBlocked(Device d){
		return this.block.contains(d);
	}
	
	public void allowAccess(Device d){
		this.allowed.add(d);
	}
	
	public boolean isAllowed(Device d){
		return this.allowed.contains(d);
	}
	
	public boolean openPort(String port){
		if(this.ports.contains(port)){
			this.ports.add(port);
			return true;
		}
		return false;
	}
	
	public boolean closePort(String port){
		return this.ports.remove(port);
	}
}