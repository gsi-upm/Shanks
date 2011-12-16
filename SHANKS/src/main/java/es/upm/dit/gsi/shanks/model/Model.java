package es.upm.dit.gsi.shanks.model;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.Definitions;
import es.upm.dit.gsi.shanks.Error;
import es.upm.dit.gsi.shanks.ScenarioManager;
import es.upm.dit.gsi.shanks.Scenario;
import es.upm.dit.gsi.shanks.agents.Agent;
import es.upm.dit.gsi.shanks.devices.Device;
import es.upm.dit.gsi.shanks.devices.Gateway;
import es.upm.dit.gsi.shanks.devices.OLT;
import es.upm.dit.gsi.shanks.devices.ONT;
import es.upm.dit.gsi.shanks.devices.Splitter;
import es.upm.dit.gsi.shanks.portrayal.Links;

import sim.engine.Schedule;
import sim.engine.SimState;
import sim.field.continuous.Continuous3D;
import sim.field.grid.SparseGrid2D;
import sim.field.network.Edge;
import sim.field.network.Network;
import sim.util.Double3D;
import sim.util.Int2D;


/**
 * Model class
 * 
 * This class represents the model which manage all the simulation
 * 
 * @author Daniel Lara
 * @version 0.1
 *
 */

public class Model extends SimState{
	
	/**Parameters of the class*/
	private static final long serialVersionUID = -2238530527253654867L;
	
	public Logger log = Logger.getLogger("Model");
	
	public String[] allScenarios = {"FTTH", "PRUEBA"};
	public static List<Device> devicesnetwork = new ArrayList<Device>();
	public static SparseGrid2D problems;
	public int gridWidth = 500;
	public int gridHeight = 500;
	public int continuous1 = 500;
	public int continuous2 = 500;
	public int continuous3 = 500;
	public static Scenario scenario;
	public static double PROB_BROKEN = 0.15;
	public static String SELECT_SCENARIO = "REAL FTTH";
	public static String SELECT_ERROR_GENERATION = "ErrorList";
	public Network links1;
	public ScenarioManager m;
	public Continuous3D elements3d;
	public Continuous3D legend;
	
	/**Default Constructor*/
	public Model(long seed) {
		super(seed);
		log.fine("Model constructor ->");
	}
	
	
	/**
	 * Get the gridWidth
	 * @return gridWidth
	 */
	public int getGridWidth() {
		return gridWidth;
	}

	/**
	 * Set the grid Width
	 * @param width
	 */
	public void setGridWidth(int width) {
		gridWidth = width;
	}

	/**
	 * Get the Grid Height
	 * @return gridHeight
	 */
	public int getGridHeight() {
		return gridHeight;
	}

	/**
	 * Set the grid Height
	 * @param height
	 */
	public void setGridHeight(int height) {
		gridHeight = height;
	}
	
	/**
	 * Set the probability to broke a device or a probability that an error happens
	 * @param prob
	 */
	public void setProbBroken(double prob){
		Model.PROB_BROKEN = prob;
	}
	
	/**
	 * Get the probability to broke a device or a probability that an error happens
	 * @return PROB_BROKEN
	 */
	public double getProbBroken(){
		return PROB_BROKEN;
	}
	
	
	/**
	 * Assign an int to ech scenario
	 * @return int that represent the selected scenario
	 */
	public int selectScenario(){
		if(SELECT_SCENARIO.equals("REAL FTTH")){
			return 1;
		}
		if(SELECT_SCENARIO.equals("SIMPLE FTTH")){
			return 0;
		}
		return 1;
	}
	
	/**
	 * Assign an int to each way of generate an error
	 * @return int that represents the way to generate an error
	 */
	public int selectError(){
		if(SELECT_ERROR_GENERATION.equals("ProbBroken")){
			return 0;
		}
		if(SELECT_ERROR_GENERATION.equals("ErrorList")){
			return 1;
		}
		return 0;
	}
	
	public void makeLinks (Device from, Device to){
		Edge e = new Edge(from, to, new Links());
		links1.addEdge(e);
	}
	
	public void situateDevice(Device d, double x, double y, double z){
		elements3d.setObjectLocation(d, new Double3D(x, y, z));
		devicesnetwork.add(d);
	}
	
	
	/**
	 * Create a simple FTTH scenario
	 * @return devicesnetwork List which contains the different device of the scenario
	 */
	public List<Device> createFTTH(){
		Gateway gateway = new Gateway("Gateway", 0, 45, Definitions.GATEWAY);
		OLT olt = new OLT("OLT", 0, 45, Definitions.OLT);
		ONT ont = new ONT("ONT", 0, 45, Definitions.ONT);
		Splitter splitter1 = new Splitter("Splitter1", 0, 45, Definitions.SPLITTER1);
		Splitter splitter2 = new Splitter("Splitter2", 0, 45, Definitions.SPLITTER2);
		situateDevice(olt, -400, 0, 0);
		situateDevice(ont, -200, 0, 0);
		situateDevice(splitter1, -0, 0, 0);
		situateDevice(splitter2, 200, 0, 0);
		situateDevice(gateway, 400, 0, 0);
		makeLinks(olt, splitter1);
		makeLinks(splitter1, splitter2);
		makeLinks(splitter2, ont);
		makeLinks(ont, gateway);	
		System.out.println("FTTH RECIEN CREADA NUMERO DEVICES: " + devicesnetwork.size());
		scenario = new Scenario(SELECT_SCENARIO, devicesnetwork);
		return devicesnetwork;
	}
	
	
	/**
	 * A more complex FTTH scenario, in this case is not necessary a method which make the connections, they are
	 * made in this method
	 * @return devicesnetwork List with the different device that form the scenario
	 */
	public List<Device> createPPP(){
		Gateway gateway1 = new Gateway("Gateway1", 0, 45, Definitions.GATEWAY);
		Gateway gateway2 = new Gateway("Gateway2", 0, 45, Definitions.GATEWAY);
		Gateway gateway3 = new Gateway("Gateway3", 0, 45, Definitions.GATEWAY);
		Gateway gateway4 = new Gateway("Gateway4", 0, 45, Definitions.GATEWAY);
		OLT olt = new OLT("OLT", 0, 45, Definitions.OLT);
		ONT ont1 = new ONT("ONT1", 0, 45, Definitions.ONT);
		ONT ont2 = new ONT("ONT2", 0, 45, Definitions.ONT);
		ONT ont3 = new ONT("ONT3", 0, 45, Definitions.ONT);
		ONT ont4 = new ONT("ONT4", 0, 45, Definitions.ONT);
		Splitter splitter1 = new Splitter("Splitter1", 0, 45, Definitions.SPLITTER1);
		Splitter splitter2a = new Splitter("Splitter2a", 0, 45, Definitions.SPLITTER2);
		Splitter splitter2b = new Splitter("Splitter2b", 0, 45, Definitions.SPLITTER2);
		Splitter splitter2c = new Splitter("Splitter2c", 0, 45, Definitions.SPLITTER2);
		Splitter splitter2d = new Splitter("Splitter2d", 0, 45, Definitions.SPLITTER2);
		situateDevice(olt, -400, 0, 0);
		situateDevice(splitter1, -200, 0, 0);
		situateDevice(splitter2a, 0, 400, 0);
		situateDevice(splitter2b, 0, 200, 0);
		situateDevice(splitter2c, 0, -200, 0);
		situateDevice(splitter2d, 0, -400, 0);
		situateDevice(ont1, 200, 400, 0);
		situateDevice(ont2, 200, 200, 0);
		situateDevice(ont3, 200, -200, 0);
		situateDevice(ont4, 200, -400, 0);
		situateDevice(gateway1, 400, 400, 0);
		situateDevice(gateway2, 400, 200, 0);
		situateDevice(gateway3, 400, -200, 0);
		situateDevice(gateway4, 400, -400, 0);		
		makeLinks(olt, splitter1);
		makeLinks(splitter1, splitter2a);
		makeLinks(splitter1, splitter2b);
		makeLinks(splitter1, splitter2c);
		makeLinks(splitter1, splitter2d);
		makeLinks(splitter2a, ont1);
		makeLinks(splitter2b, ont2);
		makeLinks(splitter2c, ont3);
		makeLinks(splitter2d, ont4);
		makeLinks(ont1, gateway1);
		makeLinks(ont2, gateway2);
		makeLinks(ont3, gateway3);
		makeLinks(ont4, gateway4);	
		scenario = new Scenario(SELECT_SCENARIO, devicesnetwork);
		return devicesnetwork;
	}
	
	
	public void start(){
		super.start();
		log.finer("-> start method");
		startModel();
	}
	
	//Broke a random device
	public void setBrokenStatus(){
    	Double randomize = Math.random();
    	int numeroAleatorio = (int) (Math.random()*devicesnetwork.size());
    	if(randomize < PROB_BROKEN){
    		devicesnetwork.get(numeroAleatorio).setStatus(1);
    	}
    }
	 public void createLegend(){
		 Gateway g = new Gateway("Gateway",0,45,Definitions.GATEWAY);
		 Splitter sp1 = new Splitter("Splitter 1",0,45,Definitions.SPLITTER1);
		 Splitter sp2 = new Splitter("Splitter 2",0,45,Definitions.SPLITTER2);
		 OLT olt = new OLT("OLT",0,45,Definitions.OLT);
		 ONT ont = new ONT("ONT",0,45,Definitions.ONT);
		 legend.setObjectLocation(g, new Double3D(-300, 300, 0));
		 legend.setObjectLocation(sp1, new Double3D(-100, 300, 0));
		 legend.setObjectLocation(sp2, new Double3D(100, 300, 0));
		 legend.setObjectLocation(olt, new Double3D(300, 300, 0));
		 legend.setObjectLocation(ont, new Double3D(-300, 0, 0));		 
	 }
	 
	//The initial configuration of the scenario
	public void startModel() {
		devicesnetwork.clear();
		elements3d = new Continuous3D(5, gridWidth, gridHeight, gridHeight); 
		links1 = new Network();
		problems = new SparseGrid2D(365, 50);
		legend = new Continuous3D(5, gridWidth, gridHeight, gridHeight);
		createLegend();
		ScenarioManager.totalproblems = 0;
			switch(selectScenario()){
			case 0:
				createFTTH();
				m = new ScenarioManager(scenario);
				System.out.println("SELECTED SCENARIO " + scenario.getName());
				break;
			case 1:
				createPPP();
				m = new ScenarioManager(scenario);
				System.out.println("SELECTED SCENARIO " + scenario.getName());
				break;
			}
		Error.createDeviceErrors();
		Agent a = new Agent();
		schedule.scheduleRepeating(Schedule.EPOCH,0,m,2);
		schedule.scheduleRepeating(Schedule.EPOCH + 1,2,a,2);
		
		
	}
	
	
	public static void main(String []args){
		doLoop(Model.class, args);
	    System.exit(0); 
	}

}
