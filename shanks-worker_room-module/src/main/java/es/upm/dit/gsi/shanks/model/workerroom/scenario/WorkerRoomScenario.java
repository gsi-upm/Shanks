package es.upm.dit.gsi.shanks.model.workerroom.scenario;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
import es.upm.dit.gsi.shanks.exception.DuplicatedAgentIDException;
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
import es.upm.dit.gsi.shanks.model.workerroom.WorkerRoom2DSimulationGUI;
import es.upm.dit.gsi.shanks.model.workerroom.WorkerRoom3DSimulationGUI;
import es.upm.dit.gsi.shanks.model.workerroom.WorkerRoomSimulation;
import es.upm.dit.gsi.shanks.model.workerroom.element.device.Computer;
import es.upm.dit.gsi.shanks.model.workerroom.element.device.Printer;
import es.upm.dit.gsi.shanks.model.workerroom.element.device.Router;
import es.upm.dit.gsi.shanks.model.workerroom.element.link.EthernetLink;
import es.upm.dit.gsi.shanks.model.workerroom.scenario.portrayal.WorkerRoomScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.workerroom.scenario.portrayal.WorkerRoomScenario3DPortrayal;

public class WorkerRoomScenario extends Scenario{
	
	 public static final String CLOUDY = "CLOUDY";
	 public static final String SUNNY = "SUNNY";

	 public static final String CLOUDY_PROB = "CLOUDY_PROB";

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
		Device pc1 = new Computer("PC1", Computer.STATUS_OK, false);
		Device pc2 = new Computer("PC2", Computer.STATUS_OK, false);
		Device pc3 = new Computer("PC3", Computer.STATUS_OK, false);
		Device pc4 = new Computer("PC4", Computer.STATUS_OK, false);
		Device pc5 = new Computer("PC5", Computer.STATUS_OK, false);
		Device pc6 = new Computer("PC6", Computer.STATUS_OK, false);
		Device pc7 = new Computer("PC7", Computer.STATUS_OK, false);
		Device pc8 = new Computer("PC8", Computer.STATUS_OK, false);
		Device printer = new Printer("Printer", Printer.STATUS_OK, false);
		Device router = new Router("Router", Router.STATUS_OK, true);
		Link ethernetLink = new EthernetLink("EthernetLink", EthernetLink.STATUS_OK, 10);
		
		pc1.connectToLink(ethernetLink);
		pc2.connectToLink(ethernetLink);
		pc3.connectToLink(ethernetLink);
		pc4.connectToLink(ethernetLink);
		pc5.connectToLink(ethernetLink);
		pc6.connectToLink(ethernetLink);
		pc7.connectToLink(ethernetLink);
		pc8.connectToLink(ethernetLink);
		printer.connectToLink(ethernetLink);
		router.connectToLink(ethernetLink);
		
		this.addNetworkElement(ethernetLink);
		this.addNetworkElement(router);
		this.addNetworkElement(printer);
		this.addNetworkElement(pc1);	
		this.addNetworkElement(pc2);
		this.addNetworkElement(pc3);
		this.addNetworkElement(pc4);
		this.addNetworkElement(pc5);
		this.addNetworkElement(pc6);
		this.addNetworkElement(pc7);
		this.addNetworkElement(pc8);
		
	}

	@Override
	public void addPossibleEvents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPossibleFailures() {
		// TODO Auto-generated method stub
		
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
			String arg0) throws UnsupportedScenarioStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(SUNNY);
		this.addPossibleStatus(CLOUDY);
		
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
		//scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_2D);
		scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_3D);
		//scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
		Properties configProperties = new Properties();
		configProperties.put(WorkerRoomSimulation.CONFIGURATION, "3");
		WorkerRoomSimulation sim = new WorkerRoomSimulation(
		        System.currentTimeMillis(), WorkerRoomScenario.class,
		        "MyScenario", SUNNY,
		        scenarioProperties, configProperties);
		//WorkerRoom2DSimulationGUI gui = new WorkerRoom2DSimulationGUI(sim);
		WorkerRoom3DSimulationGUI gui = new WorkerRoom3DSimulationGUI(sim);
		gui.start();
		//sim.start();
		//do
		//    if (!sim.schedule.step(sim))
		//        break;
		//while (sim.schedule.getSteps() < 2001);
		//sim.finish();
}

}
