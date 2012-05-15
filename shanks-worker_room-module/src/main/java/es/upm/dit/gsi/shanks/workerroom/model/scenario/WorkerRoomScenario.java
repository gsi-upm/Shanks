package es.upm.dit.gsi.shanks.workerroom.model.scenario;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
import es.upm.dit.gsi.shanks.exception.DuplicatedAgentIDException;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.networkattacks.util.failures.ComputerFailure;
import es.upm.dit.gsi.shanks.networkattacks.util.failures.RouterFailure;
import es.upm.dit.gsi.shanks.networkattacks.util.failures.WireBroken;
import es.upm.dit.gsi.shanks.networkattacks.util.failures.WireDamaged;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.Computer;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.EthernetLink;
import es.upm.dit.gsi.shanks.workerroom.model.Values;
import es.upm.dit.gsi.shanks.workerroom.model.element.device.LANRouter;
import es.upm.dit.gsi.shanks.workerroom.model.element.device.Printer;
import es.upm.dit.gsi.shanks.workerroom.model.scenario.portrayal.WorkerRoomScenario2DPortrayal;
import es.upm.dit.gsi.shanks.workerroom.model.scenario.portrayal.WorkerRoomScenario3DPortrayal;
import es.upm.dit.gsi.shanks.workerroom.simulation.WorkerRoom2DSimulationGUI;
import es.upm.dit.gsi.shanks.workerroom.simulation.WorkerRoomSimulation;

public class WorkerRoomScenario extends Scenario{
	

	 public static final String CLOUDY_PROB = "CLOUDY_PROB";
	 
		public static final String STATUS_NORMAL = "Normal";
		public static final String STATUS_UNDER_ATTACK = "UnderAttack";

	public WorkerRoomScenario(String id, String initialState,
			Properties properties)
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, UnsupportedScenarioStatusException,
			DuplicatedIDException {
		super(id, initialState, properties);
	}

	@Override
	public void addNetworkElements()
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, DuplicatedIDException {
		
		// Worker_room router
		LANRouter router = new LANRouter(Values.WORKER_ROOM_ROUTER_ID);
		this.addNetworkElement(router);
		
		// Adding PCs, PC-Links and connect it with router
		ArrayList<Computer> pcs = new ArrayList<Computer>();
		ArrayList<Link> links = new ArrayList<Link>();
		for(int i=0; i<Values.NUMBER_OF_WORKERROOM_PCS; i++){
			Computer computer = new Computer(Values.WR_COMPUTER_ID+i);
			EthernetLink link = new EthernetLink(Values.WR_ETHERNET_ID+i, 2.0);
			pcs.add(computer);
			links.add(link);
			router.connectToDeviceWithLink(computer, link);
			this.addNetworkElement(computer);
			this.addNetworkElement(link);
		}
		
		// Adding printer
		Printer printer = new Printer(Values.WR_Printer_ID, Printer.STATUS_OK, false);
		links.add(new EthernetLink(Values.WR_ETHERNET_ID+links.size(), 2.0));
		router.connectToDeviceWithLink(printer, links.get(links.size()-1));
	}

	@Override
	public void addPossibleEvents() {
//		this.addPossibleEventsOfNE(RouterFailure.class, this.getNetworkElement("LANRouter"));
	}

	@Override
	public void addPossibleFailures() {
		List<Set<NetworkElement>> linksCombinations = new ArrayList<Set<NetworkElement>>();
		List<Set<NetworkElement>> pcsCombinations = new ArrayList<Set<NetworkElement>>();
		for(String key: this.getCurrentElements().keySet()){
			NetworkElement ne = this.getCurrentElements().get(key);
			if(ne instanceof Link){
				Set<NetworkElement> set = new HashSet<NetworkElement>();
				set.add(ne);
				linksCombinations.add(set);
			} else if(ne instanceof Computer){
				Set<NetworkElement> set = new HashSet<NetworkElement>();
				set.add(ne);
				pcsCombinations.add(set);
			}
		}
		this.addPossibleFailure(WireBroken.class, linksCombinations);
		this.addPossibleFailure(WireDamaged.class, linksCombinations);
		this.addPossibleFailure(RouterFailure.class, this.getNetworkElement(Values.WORKER_ROOM_ROUTER_ID));
		this.addPossibleFailure(ComputerFailure.class, pcsCombinations);	
	}

	@Override
	public Scenario2DPortrayal createScenario2DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return new WorkerRoomScenario2DPortrayal(this, 100, 100);
	}

	@Override
	public Scenario3DPortrayal createScenario3DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return new WorkerRoomScenario3DPortrayal(this, 100, 100, 100);
	}

	@Override
	public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
			String status) throws UnsupportedScenarioStatusException {
		HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();
	    return penalties;
	}
	

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(STATUS_NORMAL);
		this.addPossibleStatus(STATUS_UNDER_ATTACK);
	}
	
	public static void main(String[] args) throws SecurityException,
		    IllegalArgumentException, NoSuchMethodException,
		    InstantiationException, IllegalAccessException,
		    InvocationTargetException,
		    UnsupportedNetworkElementStatusException,
		    TooManyConnectionException, UnsupportedScenarioStatusException,
		    DuplicatedIDException, DuplicatedPortrayalIDException, ScenarioNotFoundException, DuplicatedAgentIDException, DuplicatedActionIDException {

		Properties scenarioProperties = new Properties();
		scenarioProperties.put(CLOUDY_PROB, "5");
		scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_2D);
		//scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_3D);
		//scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
		Properties configProperties = new Properties();
		configProperties.put(WorkerRoomSimulation.CONFIGURATION, "3");
		WorkerRoomSimulation sim = new WorkerRoomSimulation(
		        System.currentTimeMillis(), WorkerRoomScenario.class,
		        "WorkerRoomScenario", STATUS_NORMAL,
		        scenarioProperties, configProperties);
		WorkerRoom2DSimulationGUI gui = new WorkerRoom2DSimulationGUI(sim);
		//WorkerRoom3DSimulationGUI gui = new WorkerRoom3DSimulationGUI(sim);
		gui.start();
		//sim.start();
		//do
		//    if (!sim.schedule.step(sim))
		//        break;
		//while (sim.schedule.getSteps() < 2001);
		//sim.finish();
}

}
