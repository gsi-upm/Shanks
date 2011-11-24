package es.upm.dit.gsi.shanks;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
	public static List<Device> ftth = new ArrayList<Device>();
	public static SparseGrid2D elements;
	public int gridWidth = 500;
	public int gridHeight = 500;
	public int continuous1 = 500;
	public int continuous2 = 500;
	public int continuous3 = 500;
	public static Scenarios scenario;
	public static double PROB_BROKEN = 0.15;
	public static String SELECT_SCENARIO = "REAL FTTH";
	public static String SELECT_ERROR_GENERATION = "ErrorList";
	public Network links1;
	public ScenarioManager m;
	public Continuous3D elements3d;
	
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
	 * @param gridWidth
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
	 * @param gridHeight
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
	 * Set the scenario of the simulation
	 * @param scen
	 */
//	public static void setSelectScenario(String scen){
//		SELECT_SCENARIO = scen;
//	}
	
	/**
	 * Get the simulation scenario
	 * @return SELECT_SCENARIO
	 */
//	public static String getSelectScenario(){
//		return SELECT_SCENARIO;
//	}
	
	/**
	 * Set the way to simulate the errors
	 * @param error
	 */
//	public void setErrorGeneration(String error){
//		this.SELECT_ERROR_GENERATION = error;
//	}
//	
//	/**
//	 * Get the way to simulate the errors
//	 * @return SELECT_ERROR_GENERATION
//	 */
//	public String getErrorGeneration(){
//		return SELECT_ERROR_GENERATION;
//	}
	
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

	/**
	 * Create a simple FTTH scenario
	 * @return ftth List which contains the different device of the scenario
	 */
	public List<Device> createFTTH(){
		Gateway gateway = new Gateway("Gateway", 0, 45, Definitions.GATEWAY);
		OLT olt = new OLT("OLT", 0, 45, Definitions.OLT);
		ONT ont = new ONT("ONT", 0, 45, Definitions.ONT);
		Splitter splitter1 = new Splitter("Splitter1", 0, 45, Definitions.SPLITTER1);
		Splitter splitter2 = new Splitter("Splitter2", 0, 45, Definitions.SPLITTER2);
		elements3d.setObjectLocation(olt, new Double3D(-400, 0, 0));
		elements3d.setObjectLocation(splitter1, new Double3D(-200,0,0));
		elements3d.setObjectLocation(ont, new Double3D(0,0,0));
		elements3d.setObjectLocation(splitter2, new Double3D(200,0,0));
		elements3d.setObjectLocation(gateway, new Double3D(400,0,0));
		ftth.add(gateway);
		ftth.add(olt);
		ftth.add(ont);
		ftth.add(splitter1);
		ftth.add(splitter2);
		System.out.println("FTTH RECIEN CREADA NUMERO DEVICES: " + ftth.size());
		scenario = new Scenarios(SELECT_SCENARIO, ftth);
		return ftth;
	}
	
	/**
	 * Make the connections between the devices
	 *
	 */
	public void makeFTTHLinks(){
		for(Device d : ftth){
			if(d.getType() == Definitions.GATEWAY){
				elements.setObjectLocation(d, new Int2D(400,
						random.nextInt(gridHeight)));
				//elements3d.setObjectLocation(d, new Double2D(random.nextDouble(),0));
				for(Device dev : ftth){
					if(dev.getType() == Definitions.ONT && dev.getType()!= Definitions.GATEWAY){
						Edge e = new Edge(d, dev, new Links());
						links1.addEdge(e);
					}
				}
			}
			if(d.getType() == Definitions.ONT){
				elements.setObjectLocation(d, new Int2D(300,
						random.nextInt(gridHeight)));
				//elements3d.setObjectLocation(d, new Double2D(random.nextDouble(),0));
				for(Device dev : ftth){
					if((dev.getType() == Definitions.SPLITTER1 || dev.getType() == Definitions.GATEWAY) && dev.getType()!=Definitions.ONT){
						Edge e = new Edge(d, dev, new Links());
						links1.addEdge(e);
					}
				}
			}
			if(d.getType() == Definitions.SPLITTER1){
				elements.setObjectLocation(d, new Int2D(200,
						random.nextInt(gridHeight)));
				//elements3d.setObjectLocation(d, new Double2D(random.nextDouble(),0));
				for(Device dev : ftth){
					if((dev.getType() == Definitions.ONT || dev.getType() == Definitions.OLT) && dev.getType()!= Definitions.SPLITTER1){
						Edge e = new Edge(d, dev, new Links());
						links1.addEdge(e);
					}
				}
			}
			if(d.getType() == Definitions.OLT){
				elements.setObjectLocation(d, new Int2D(100,
						random.nextInt(gridHeight)));
				//elements3d.setObjectLocation(d, new Double2D(random.nextDouble(),0));
			}
		}		
	}
	/**
	 * A more complex FTTH scenario, in this case is not necessary a method which make the connections, they are
	 * made in this method
	 * @return ftth List with the different device that form the scenario
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
		ftth.add(gateway1);
		ftth.add(olt);
		ftth.add(ont1);
		ftth.add(splitter1);
		ftth.add(gateway2);
		ftth.add(ont2);
		ftth.add(splitter2a);
		ftth.add(gateway3);
		ftth.add(ont3);
		ftth.add(splitter2b);
		ftth.add(gateway4);
		ftth.add(ont4);
		ftth.add(splitter2c);
		ftth.add(splitter2d);
		elements3d.setObjectLocation(olt, new Double3D(-400, 0, 0));
		elements3d.setObjectLocation(splitter1, new Double3D(-200, 0, 0));
		elements3d.setObjectLocation(splitter2a, new Double3D(0, 400, 0));
		elements3d.setObjectLocation(splitter2b, new Double3D(0, 200, 0));
		elements3d.setObjectLocation(splitter2c, new Double3D(0, -200, 0));
		elements3d.setObjectLocation(splitter2d, new Double3D(0, -400, 0));
		elements3d.setObjectLocation(ont1, new Double3D(200, 400, 0));
		elements3d.setObjectLocation(ont2, new Double3D(200, 200, 0));
		elements3d.setObjectLocation(ont3, new Double3D(200, -200, 0));
		elements3d.setObjectLocation(ont4, new Double3D(200, -400, 0));
		elements3d.setObjectLocation(gateway1, new Double3D(400, 400, 0));
		elements3d.setObjectLocation(gateway2, new Double3D(400, 200, 0));
		elements3d.setObjectLocation(gateway3, new Double3D(400, -200, 0));
		elements3d.setObjectLocation(gateway4, new Double3D(400, -400, 0));
		elements.setObjectLocation(olt, new Int2D(100,gridHeight/2));
		elements.setObjectLocation(splitter1, new Int2D(200,gridHeight/2));
		elements.setObjectLocation(splitter2a, new Int2D(300,100));
		elements.setObjectLocation(splitter2b, new Int2D(300,200));
		elements.setObjectLocation(splitter2c, new Int2D(300,300));
		elements.setObjectLocation(splitter2d, new Int2D(300,400));
		elements.setObjectLocation(ont1, new Int2D(400,100));
		elements.setObjectLocation(ont2, new Int2D(400,200));
		elements.setObjectLocation(ont3, new Int2D(400,300));
		elements.setObjectLocation(ont4, new Int2D(400,400));
		elements.setObjectLocation(gateway1, new Int2D(500,100));
		elements.setObjectLocation(gateway2, new Int2D(500,200));
		elements.setObjectLocation(gateway3, new Int2D(500,300));
		elements.setObjectLocation(gateway4, new Int2D(500,400));
		Edge e1 = new Edge(olt, splitter1, new Links());
		Edge e2 = new Edge(splitter1, splitter2a, new Links());
		Edge e3 = new Edge(splitter1, splitter2b, new Links());
		Edge e4 = new Edge(splitter1, splitter2c, new Links());
		Edge e5 = new Edge(splitter1, splitter2d, new Links());
		Edge e6 = new Edge(splitter2a, ont1, new Links());
		Edge e7 = new Edge(splitter2b, ont2, new Links());
		Edge e8 = new Edge(splitter2c, ont3, new Links());
		Edge e9 = new Edge(splitter2d, ont4, new Links());
		Edge e10 = new Edge(ont1, gateway1, new Links());
		Edge e11 = new Edge(ont2, gateway2, new Links());
		Edge e12 = new Edge(ont3, gateway3, new Links());
		Edge e13 = new Edge(ont4, gateway4, new Links());
		links1.addEdge(e1);
		links1.addEdge(e2);
		links1.addEdge(e3);
		links1.addEdge(e4);
		links1.addEdge(e5);
		links1.addEdge(e6);
		links1.addEdge(e7);
		links1.addEdge(e8);
		links1.addEdge(e9);
		links1.addEdge(e10);
		links1.addEdge(e11);
		links1.addEdge(e12);
		links1.addEdge(e13);	
		scenario = new Scenarios(SELECT_SCENARIO, ftth);
		return ftth;
	}
	
	
	public void start(){
		super.start();
		log.finer("-> start method");
		startModel();
	}
	
	//Broke a random device
	public void setBrokenStatus(){
    	Double randomize = Math.random();
    	int numeroAleatorio = (int) (Math.random()*ftth.size());
    	if(randomize < PROB_BROKEN){
    		ftth.get(numeroAleatorio).setStatus(1);
    	}
    }
	
	//The initial configuration of the scenario
	public void startModel() {
		ftth.clear();
		elements3d = new Continuous3D(5, gridWidth, gridHeight, gridHeight); 
		links1 = new Network();
		elements = new SparseGrid2D(gridWidth, gridHeight);
		ScenarioManager.totalproblems = 0;
			switch(selectScenario()){
			case 0:
				createFTTH();
				makeFTTHLinks();
				m = new ScenarioManager(scenario);
				System.out.println("SELECTED SCENARIO " + scenario.getName());
				break;
			case 1:
				createPPP();
				m = new ScenarioManager(scenario);
				System.out.println("SELECTED SCENARIO " + scenario.getName());
				break;
			}
		DeviceErrors.createDeviceErrors();
		Agent a = new Agent();
		schedule.scheduleRepeating(Schedule.EPOCH,0,m,2);
		schedule.scheduleRepeating(Schedule.EPOCH + 1,2,a,2);
		
		
	}
	
	
	public static void main(String []args){
		doLoop(Model.class, args);
	    System.exit(0); 
	}

}
