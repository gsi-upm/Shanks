package es.upm.dit.gsi.shanks;


/**
 * SecenarioManager class
 * 
 * This class generate the possibles errors
 * 
 * @author Daniel Lara
 * @version 0.1
 * 
 */


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import sim.engine.SimState;
import sim.engine.Steppable;



public class ScenarioManager implements Steppable{


		public Logger log = Logger.getLogger(this.getClass().toString());
	
		private static final long serialVersionUID = -7448202235281457216L;
		public static Scenarios scenario;
		public static DeviceErrors dev;
		public static int totalproblems = 0;
		
		public ScenarioManager(Scenarios scen){
			this.log.setLevel(Level.ALL);
			scenario = scen;
		}
		
		//Get the devices from scenario
		public static List<Device> getDeviceFromScenario(){
			return Model.devicesnetwork;
		}
		
		
		public void generateProblem(){
			if(Agent.repairFlag){
				repairProblems();
			}
			int randomproblem = (int) (Math.random()*DeviceErrors.deverrors.size());
			double randomerrorgenerator = Math.random();
			if(randomerrorgenerator <= Model.PROB_BROKEN){
				dev = DeviceErrors.deverrors.get(randomproblem);
				dev.setTrigger(true);
				Model.problems.setObjectLocation(dev, 25, 25);
				Agent.problemDetected = dev.getName();
				totalproblems++;				
			}else if(randomerrorgenerator > Model.PROB_BROKEN){
				DeviceErrors noproblem = new DeviceErrors ("No problem", true);
				dev = noproblem;
				Model.problems.setObjectLocation(dev, 25, 25);
				Agent.problemDetected = dev.getName();
			}
			System.out.println("ERROR " + dev.getName());
			System.out.println("TOTAL PROBLEMS " + totalproblems);
		}
		
		public void repairProblems(){
			for(DeviceErrors d : DeviceErrors.deverrors){
				d.setTrigger(false);
			}
		}
		
		
		//The actions done by the agent for each step
		public void step(SimState state){
			System.out.println("NUEVA PRUEBA");
			Model model = (Model)state;
			switch(model.selectError()){
			case 0:
				model.setBrokenStatus();
				break;
			case 1:
				generateProblem();
				DeviceErrors.setDeviceWithProblems();
				break;
			}
		}
	}

