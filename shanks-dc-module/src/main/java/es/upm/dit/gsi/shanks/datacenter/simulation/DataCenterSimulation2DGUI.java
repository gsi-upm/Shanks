package es.upm.dit.gsi.shanks.datacenter.simulation;

import java.util.Properties;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.datacenter.model.scenario.DataCenterScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedChartIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

public class DataCenterSimulation2DGUI extends ShanksSimulation2DGUI {

	public DataCenterSimulation2DGUI(ShanksSimulation sim) {
		super(sim);
	}

	/**
	 * @return
	 */
	public static String getName() {
		return "Network Security simulation - Data Center - 2D";
	}

	@Override
	public void addDisplays(Scenario2DPortrayal scenarioPortrayal) {
	}

	@Override
	public void addCharts(Scenario2DPortrayal scenarioPortrayal)
			throws DuplicatedChartIDException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void locateFrames(Scenario2DPortrayal scenarioPortrayal) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		try {
			Properties scenarioProperties = new Properties();
			scenarioProperties.put(Scenario.SIMULATION_GUI,
					Scenario.SIMULATION_2D);
			// scenarioProperties.put(Scenario.SIMULATION_GUI,
			// Scenario.SIMULATION_3D);
			// scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
			DataCenterSimulation tut = new DataCenterSimulation(
					System.currentTimeMillis(), DataCenterScenario.class,
					"DataCente scenario", DataCenterScenario.STATUS_NORMAL,
					scenarioProperties);
			DataCenterSimulation2DGUI gui = new DataCenterSimulation2DGUI(tut);
			gui.start();

			// ShanksSimulation sim = gui.getSimulation();
			// do
			// if (!sim.schedule.step(sim))
			// break;
			// while (sim.schedule.getSteps() < 8001);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}