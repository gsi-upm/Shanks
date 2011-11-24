package es.upm.dit.gsi.shanks;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import sim.engine.SimState;
import sim.engine.Steppable;

public class Agent implements Steppable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4744430503147830611L;
	
	public Logger log = Logger.getLogger("Agent");
	
	public List<Device> faildevices = new ArrayList<Device>();
	public List<Device> totaldevice = new ArrayList<Device>();

	public Agent (){

	}
	
	public List<Device> getFailureDevice(){
		
		log.info("-> getFailureDevice()");
		totaldevice = ScenarioManager.getDeviceFromScenario();
		
		log.fine("Scenario size: " + totaldevice.size());
		
		//Make a list with the broken devices
		for(int i=0; i < totaldevice.size(); i++){
			if(totaldevice.get(i).getStatus() == 1){
				//log.info("Error in device: " + totaldevice.get(i).getID());
				faildevices.add(totaldevice.get(i));
			}
		}
		
		if(faildevices.isEmpty()){
			log.info("No problems");
		}else{
			for(int j = 0; j < faildevices.size(); j++){
				log.info("Error in device: " + faildevices.get(j).getID());
			}
			
		}
		return faildevices;
	}
	
//	public String getError(){
//		String error = "No problem";
//		
//		return error;
//	}
	public void repairDevices(){
		log.info("-> repairDevices()");
		for(Device d : faildevices){
			log.fine("Device broken: " + d.getID());
				d.setStatus(Definitions.HEALTHY_STATUS);
				log.fine("STATUS " + d.getStatus() + " of Device " + d.getID());
		}
		faildevices.clear();
		log.info("Devices have been repaired");
	}

	public void step(SimState state) {
		getFailureDevice();
		if(!faildevices.isEmpty()){
			repairDevices();
		}
	}
	
}