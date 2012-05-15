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
		Device pc1 = new Computer("PC1");
		Device pc2 = new Computer("PC2");
		Device pc3 = new Computer("PC3");
		Device pc4 = new Computer("PC4");
		Device pc5 = new Computer("PC5");
		Device printer = new Printer("Printer", Printer.STATUS_OK, false);
		Device router = new LANRouter("LANRouter");
		Link ethernetLink1 = new EthernetLink("EthernetLink1", 2);
		Link ethernetLink2 = new EthernetLink("EthernetLink2", 2);
		Link ethernetLink3 = new EthernetLink("EthernetLink3", 2);
		Link ethernetLink4 = new EthernetLink("EthernetLink4", 2);
		Link ethernetLink5 = new EthernetLink("EthernetLink5", 2);
		Link ethernetLink6 = new EthernetLink("EthernetLink6", 2);

		
		ethernetLink1.connectDevices(pc1, router);
		ethernetLink2.connectDevices(pc2, router);
		ethernetLink3.connectDevices(pc3, router);
		ethernetLink4.connectDevices(pc4, router);
		ethernetLink5.connectDevices(pc5, router);
		ethernetLink6.connectDevices(printer, router);
		
		this.addNetworkElement(ethernetLink1);
		this.addNetworkElement(ethernetLink2);
		this.addNetworkElement(ethernetLink3);
		this.addNetworkElement(ethernetLink4);
		this.addNetworkElement(ethernetLink5);
		this.addNetworkElement(ethernetLink6);
		this.addNetworkElement(router);
		this.addNetworkElement(printer);
		this.addNetworkElement(pc1);	
		this.addNetworkElement(pc2);
		this.addNetworkElement(pc3);
		this.addNetworkElement(pc4);
		this.addNetworkElement(pc5);
				
	}

	@Override
	public void addPossibleEvents() {
//		this.addPossibleEventsOfNE(RouterFailure.class, this.getNetworkElement("LANRouter"));
	}

	@Override
	public void addPossibleFailures() {
		Set<NetworkElement> set1 = new HashSet<NetworkElement>();
		set1.add(this.getNetworkElement("EthernetLink1"));
		Set<NetworkElement> set2 = new HashSet<NetworkElement>();
		set2.add(this.getNetworkElement("EthernetLink2"));
		Set<NetworkElement> set3 = new HashSet<NetworkElement>();
		set3.add(this.getNetworkElement("EthernetLink3"));
		Set<NetworkElement> set4 = new HashSet<NetworkElement>();
		set4.add(this.getNetworkElement("EthernetLink4"));
		Set<NetworkElement> set5 = new HashSet<NetworkElement>();
		set5.add(this.getNetworkElement("EthernetLink5"));
		Set<NetworkElement> set6 = new HashSet<NetworkElement>();
		set6.add(this.getNetworkElement("EthernetLink6"));
        List<Set<NetworkElement>> possibleCombinations = new ArrayList<Set<NetworkElement>>();
		possibleCombinations.add(set1);
		possibleCombinations.add(set2);
		possibleCombinations.add(set3);
		possibleCombinations.add(set4);
		possibleCombinations.add(set5);
		possibleCombinations.add(set6);
		this.addPossibleFailure(WireBroken.class, possibleCombinations);
		this.addPossibleFailure(WireDamaged.class, possibleCombinations);
		
		this.addPossibleFailure(RouterFailure.class, this.getNetworkElement("LANRouter"));
		
		Set<NetworkElement> set10 = new HashSet<NetworkElement>();
		set10.add(this.getNetworkElement("PC1"));
		Set<NetworkElement> set11 = new HashSet<NetworkElement>();
		set11.add(this.getNetworkElement("PC2"));
		Set<NetworkElement> set12 = new HashSet<NetworkElement>();
		set12.add(this.getNetworkElement("PC3"));
		Set<NetworkElement> set13 = new HashSet<NetworkElement>();
		set13.add(this.getNetworkElement("PC4"));
		Set<NetworkElement> set14 = new HashSet<NetworkElement>();
		set14.add(this.getNetworkElement("PC5"));		
		List<Set<NetworkElement>> possibleCombinations1 = new ArrayList<Set<NetworkElement>>();
		possibleCombinations1.add(set10);
		possibleCombinations1.add(set11);
		possibleCombinations1.add(set12);
		possibleCombinations1.add(set13);
		possibleCombinations1.add(set14);
		this.addPossibleFailure(ComputerFailure.class, possibleCombinations1);	
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
