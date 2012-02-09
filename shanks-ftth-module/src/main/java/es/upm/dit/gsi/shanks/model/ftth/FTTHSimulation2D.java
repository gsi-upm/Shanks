package es.upm.dit.gsi.shanks.model.ftth;

import java.util.HashMap;

import sim.display.Display2D;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.model.ftth.scenario.ScenarioDefinitions;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyHyperComplexScenario2DPortrayal;

public class FTTHSimulation2D extends ShanksSimulation2DGUI{

	
	/**
     * @param sim
     */
	public FTTHSimulation2D(ShanksSimulation sim) {
		super(sim);
	}

	
	/**
     * @return
     */
    public static String getName() {
        return "FTTH 2D Simulation";
    }
    
    
	@Override
	public void addDisplays(Scenario2DPortrayal scenarioPortrayal) {
HashMap<String, Display2D> displayList = scenarioPortrayal.getDisplayList();
        
        Display2D failureDisplay = new Display2D(600, 100, this);
        displayList.put(ScenarioDefinitions.FAILURE_DISPLAY_ID, failureDisplay);
		
	}

}
