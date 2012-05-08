package es.upm.dit.gsi.shanks.datacenter.model.element.device;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * Class to represent tha gateway of a company
 * Draft
 * 
 */
public class Gateway extends Router{
	
	public static final int NUMBER_OF_PORTS = 1000;
	public static final double OPEN_PROBABILITY = 0.2; 
	
	private boolean[] ports;
	
	public Gateway(String id) throws UnsupportedNetworkElementStatusException {
		super(id);
		this.ports = new boolean[NUMBER_OF_PORTS];
	}
	
	public void init(ShanksSimulation sim){
		// Initializes the ports, open (true) or not (false)
		for(int i = 0; i< NUMBER_OF_PORTS; i++){
			ports[i] = sim.random.nextBoolean(OPEN_PROBABILITY);
		}
	}
	
	public boolean isPortOpen(String askerID, int port){
		if (!ports[port]) {
			//TODO: log the atempt. Possible hacker. 
		}
		return ports[port];
	}
}
