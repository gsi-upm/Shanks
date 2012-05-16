package es.upm.dit.gsi.shanks.workerroom.simulation;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.JFrame;

import sim.display.Display2D;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
import es.upm.dit.gsi.shanks.exception.DuplicatedAgentIDException;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedChartIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.networkattacks.util.Values;
import es.upm.dit.gsi.shanks.workerroom.model.scenario.WorkerRoomScenario;
import es.upm.dit.gsi.shanks.workerroom.model.scenario.portrayal.WorkerRoomScenario2DPortrayal;

public class WorkerRoom2DSimulationGUI extends ShanksSimulation2DGUI {

	public WorkerRoom2DSimulationGUI(ShanksSimulation sim) {
		super(sim);
	}

	@Override
	public void addCharts(Scenario2DPortrayal scenarioPortrayal)
			throws DuplicatedChartIDException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException {

	}

	public static String getName() {
		return "Worker Room Simulation";
	}

	public void addDisplays(Scenario2DPortrayal scenarioPortrayal) {
		Display2D failureDisplay = new Display2D(600, 100, this);
		try {
			this.addDisplay(WorkerRoomScenario2DPortrayal.FAILURE_DISPLAY_ID,
					failureDisplay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void locateFrames(Scenario2DPortrayal scenarioPortrayal) {
		HashMap<String, JFrame> frames = scenarioPortrayal.getFrameList();
		JFrame mainFrame = frames.get(Scenario2DPortrayal.MAIN_DISPLAY_ID);
		JFrame failureFrame = frames
				.get(WorkerRoomScenario2DPortrayal.FAILURE_DISPLAY_ID);
		// JFrame chartFrame =
		// frames.get(FailuresChartPainter.RESOLVED_FAILURES_PER_AGENT_CHART_ID);

		mainFrame.setLocation(100, 100);
		failureFrame.setLocation(500, 0);
		// chartFrame.setLocation(600, 200);

	}

	public static void main(String[] args) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException,
			UnsupportedNetworkElementStatusException,
			TooManyConnectionException, UnsupportedScenarioStatusException,
			DuplicatedIDException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException, DuplicatedAgentIDException,
			DuplicatedActionIDException {

		Properties scenarioProperties = new Properties();
		scenarioProperties.put(WorkerRoomScenario.STATUS_NORMAL, "5");
		scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_2D);
		// scenarioProperties.put(Scenario.SIMULATION_GUI,
		// Scenario.SIMULATION_3D);
		// scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
		Properties configProperties = new Properties();
		configProperties.put(WorkerRoomSimulation.CONFIGURATION, "3");
		WorkerRoomSimulation sim = new WorkerRoomSimulation(
				System.currentTimeMillis(), WorkerRoomScenario.class,
				Values.WORKER_ROOM_SCENARIO_ID, WorkerRoomScenario.STATUS_NORMAL, scenarioProperties);
		WorkerRoom2DSimulationGUI gui = new WorkerRoom2DSimulationGUI(sim);
		// WorkerRoom3DSimulationGUI gui = new WorkerRoom3DSimulationGUI(sim);
		gui.start();
		// sim.start();
		// do
		// if (!sim.schedule.step(sim))
		// break;
		// while (sim.schedule.getSteps() < 2001);
		// sim.finish();
	}

}
