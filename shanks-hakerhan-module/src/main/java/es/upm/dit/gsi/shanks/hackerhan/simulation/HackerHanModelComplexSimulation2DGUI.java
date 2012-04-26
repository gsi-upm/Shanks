package es.upm.dit.gsi.shanks.hackerhan.simulation;

import java.util.Properties;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.hackerhan.model.adsl.scenario.ADSLAccesNetworkScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedChartIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

public class HackerHanModelComplexSimulation2DGUI extends ShanksSimulation2DGUI{

    /**
     * @param sim
     */
    public HackerHanModelComplexSimulation2DGUI(ShanksSimulation sim) {
        super(sim);
    }

    /**
     * @return
     */
    public static String getName() {
        return "Tutorial Shanks - Home Area Nertwork - 2D";
    }


    public static void main (String[] args) {
		try {
			Properties scenarioProperties = new Properties();
	        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_2D);
//	        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_3D);
//	         scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
			HackerHanModelComplexSimulation tut = new HackerHanModelComplexSimulation(System.currentTimeMillis(), ADSLAccesNetworkScenario.class, "ADSL scenario", ADSLAccesNetworkScenario.SUNNY, scenarioProperties);
			HackerHanModelSimulation2DGUI gui = new HackerHanModelSimulation2DGUI(tut);
			gui.start();

//			ShanksSimulation sim = gui.getSimulation();
//			do
//				if (!sim.schedule.step(sim))
//					break;
//			while (sim.schedule.getSteps() < 8001);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
}