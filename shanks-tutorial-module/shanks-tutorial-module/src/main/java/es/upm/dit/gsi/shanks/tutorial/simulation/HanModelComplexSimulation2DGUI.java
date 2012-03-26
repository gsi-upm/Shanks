package es.upm.dit.gsi.shanks.tutorial.simulation;

import java.util.Properties;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.tutorial.model.adsl.scenario.ADSLAccesNetworkScenario;

public class HanModelComplexSimulation2DGUI extends ShanksSimulation2DGUI{

    /**
     * @param sim
     */
    public HanModelComplexSimulation2DGUI(ShanksSimulation sim) {
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
			HanModelComplexSimulation tut = new HanModelComplexSimulation(System.currentTimeMillis(), ADSLAccesNetworkScenario.class, "ADSL scenario", ADSLAccesNetworkScenario.SUNNY, scenarioProperties);
			HanModelSimulation2DGUI gui = new HanModelSimulation2DGUI(tut);
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
	
}